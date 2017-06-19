package de.generia.tools.model.api.runtime.io.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EDataType;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.ENamedElement;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.runtime.EObject;
import de.generia.tools.model.api.runtime.EObjectProxy;
import de.generia.tools.model.api.runtime.io.EObjectReader;

public class EObjectJsonReader implements EObjectReader {

	private Context context;
	private Stack<Link> linkStack = new Stack<>();
	private List<Link> links = new ArrayList<>();
	private Map<String, Object> idObjectMap = new HashMap<>();
	private JsonParser jp;
	private JsonToken currentToken;

	public EObjectJsonReader(Context context, JsonParser jp) {
		this.context = context;
		this.jp = jp;
	}
	
	public Object read(EModelElement type) throws IOException {
		Object object = readElement(jp, type);

		for (Link link : links) {
			resolveLink(link);
		}
		return object;
	}
	
	private void resolveLink(Link link) {
		if (link.feature.isMany()) {
			resolveLinkCollection(link);
		} else {
			Object source = link.object.get(link.feature.getName());
			if (source instanceof EObjectProxy) {
				EObjectProxy proxy = (EObjectProxy) source;
				Object resolved = idObjectMap.get(proxy.eGetId());
				if (resolved != null) {
					link.object.set(link.feature.getName(), resolved);
				}
			}
		}
	}

	private void resolveLinkCollection(Link link) {
		@SuppressWarnings("unchecked")
		Collection<Object> sources = (Collection<Object>) link.object.get(link.feature.getName());
		if (sources == null) {
			throw new IllegalStateException("inconsistent model graph, no links for feature '" + link.feature + "' available");
		}
		Collection<Object> targets = createCollection(link.feature);
		for (Object source : sources) {
			Object target = source;
			if (source instanceof EObjectProxy) {
				EObjectProxy proxy = (EObjectProxy) source;
				Object resolved = idObjectMap.get(proxy.eGetId());
				if (resolved != null) {
					target = resolved;
				}
			}
			targets.add(target);
		}
		link.object.set(link.feature.getName(), targets);
	}

	private Collection<Object> createCollection(EStructuralFeature feature) {
		return context.getObjectFactory().createCollection(feature);
	}

	private Object readElement(JsonParser jp, EModelElement type) throws IOException {

		Object value;
		if (type instanceof EClass) {
			value = readClass(jp, (EClass)type);
		} else if (type instanceof EEnum) {
			value = readEnum(jp, (EEnum)type);
		} else if (type instanceof EEnum) {
			value = readEnum(jp, (EEnum)type);
		} else if (type instanceof EDataType) {
			value = readDataType(jp, (EDataType)type);
		} else if (type instanceof EStructuralFeature) {
			value = readStructuralFeature(jp, (EStructuralFeature)type);
		} else {
			throw new IllegalArgumentException("can't process type '" + type + "'");
		}
		return value;		
	}

	private Object readEnum(JsonParser jp, EEnum type) throws IOException {
		String text = jp.nextTextValue();
		if (text == null) {
			return null;
		}
		return context.getObjectFactory().createEnumValue(type, text);
	}

	private Object readDataType(JsonParser jp, EDataType type) throws IOException {
		JsonToken token = nextToken();
		Object value;
		switch (token) {
		case VALUE_FALSE:
			value = false;
			break;

		case VALUE_TRUE:
			value = true;
			break;

		case VALUE_NULL:
			value = null;
			break;

		case VALUE_STRING:
			value = jp.getText();
			break;

		case VALUE_NUMBER_INT:
		case VALUE_NUMBER_FLOAT:
			value = readNumber(jp);
			if (value == null) {
				throw new IllegalStateException("can't parse number '" + jp.getValueAsString() + "' for data-type '" + typeName(type) + "'");
			}
			break;

		default:
			throw new IllegalStateException("unsupported data-type token '" + token + "' while parsing '" + jp.getValueAsString() + "' for data-type '" + typeName(type) + "'");
		}
		return value;
	}

	private Object readNumber(JsonParser jp) throws IOException {
		Object value;
		switch (jp.getNumberType()) {
		case INT:
			value = jp.getIntValue();
			break;
		case LONG:
			value = jp.getLongValue();
			break;
		case DOUBLE:
			value = jp.getDoubleValue();
			break;
		case FLOAT:
			value = jp.getFloatValue();
			break;
		case BIG_DECIMAL:
			value = jp.getDecimalValue();
			break;
		case BIG_INTEGER:
			value = jp.getBigIntegerValue();
			break;
		default:
			value = null;
		}
		return value;
	}

	private Object readClass(JsonParser jp, EClass type) throws IOException {
		JsonToken token = nextToken();
		if (token == JsonToken.VALUE_NULL) {
			return null;
		}
		if (token == JsonToken.VALUE_STRING) {
			String ref = jp.getValueAsString();
			Object object = idObjectMap.get(ref);
			if (object != null) {
				return object;
			}
			Link activeLink = getActiveLink();
			if (activeLink != null) {
				Link link = new Link();
				link.object = activeLink.object;
				link.feature = activeLink.feature;
				links.add(link);
			}
			return context.getObjectFactory().createObjectProxy(type, ref);
		}
		if (token != JsonToken.START_OBJECT) {
			throw new IllegalArgumentException("illegal token '" + token + "', expected '" + JsonToken.START_OBJECT + "' while parsing type '" + typeName(type) + "'");
		}
		EObject object = context.getObjectFactory().createObject(type);
		Link link = new Link();
		link.object = object;
		linkStack.push(link);
		EAttribute idAttribute = context.getIdAttribute(type);
		boolean isEmpty = true;
		while (nextToken() != JsonToken.END_OBJECT) {
			   String property = jp.getCurrentName();
			   if (property.equals(context.getTypeHintProperty())) {
				   String typeHint = jp.nextTextValue();
				   object = createPolymorphObject(typeHint);
				   link.object = object;
				   continue;
			   }
			   EStructuralFeature feature = object.eGetFeature(property);
			   if (feature == null) {
					throw new IllegalArgumentException("property '" + property + "' not found for type '" + typeName(type) + "'");
			   }
			   Object propertyValue = readStructuralFeature(jp, feature);
			   if (feature.equals(idAttribute) && propertyValue instanceof String) {
				   idObjectMap.put((String) propertyValue, object);
			   }
			   object.set(property, propertyValue);
			   isEmpty = false;
		}
		linkStack.pop();
		if (isEmpty) {
			object = null;
		}
		return object;
	}

	private EObject createPolymorphObject(String typeHint) {
		EClassifier type = context.getType(typeHint);
		if (type == null) {
			throw new IllegalArgumentException("can't create polymorph object: type-hint '" + typeHint +"' could not be resolved");
		}
		if (!(type instanceof EClass)) {
			throw new IllegalArgumentException("can't create polymorph object using type-hint '" + typeHint +"', expected 'ECLass' type but got '" + type.getClass().getSimpleName() + "'");
		}
		return context.getObjectFactory().createObject((EClass) type);
	}

	private Object readStructuralFeature(JsonParser jp, EStructuralFeature type) throws IOException {
		Object value;
		Link link = getActiveLink();
		link.feature = type;
		if (type.isMany()) {
			value = readArray(jp, type);
		} else {
			value = readElement(jp, type.getType());
		}
		return value;
	}

	private Object readArray(JsonParser jp, EStructuralFeature type) throws IOException {
		JsonToken token = nextToken();
		if (token == JsonToken.VALUE_NULL) {
			return null;
		}
		if (token != JsonToken.START_ARRAY) {
			throw new IllegalArgumentException("illegal token '" + token + "', expected '" + JsonToken.START_ARRAY + "' while parsing type '" + typeName(type) + "'");
		}
		Collection<Object> collection = createCollection(type);  
		token = nextToken();
		while (token != JsonToken.END_ARRAY) {
			currentToken = token;
			Object itemValue = readElement(jp, type.getType());
			collection.add(itemValue);
			token = nextToken();
		}
		return collection;
	}

	private JsonToken nextToken() throws IOException {
		JsonToken token;
		if (currentToken != null) {
			token = currentToken;
			currentToken = null;
		} else {
			token = jp.nextToken();
		}
		return token;
	}
	
	private Link getActiveLink() {
		if (linkStack.isEmpty()) {
			return null;
		}
		return linkStack.peek();
	}

	private String typeName(ENamedElement type) {		
		return type.getClass().getSimpleName() + ":" + type.getName();
	}
	
	private static class Link {
		private EObject object;
		private EStructuralFeature feature;
		@Override
		public int hashCode() {
			return object.hashCode() *13 + feature.hashCode();
		}
		
		@Override
		public boolean equals(Object object) {
			if (object == null) {
				return false;
			}
			if (object == this) {
				return true;
			}
			if (!(object instanceof Link)) {
				return false;
			}
			Link link = (Link) object;
			return object.equals(link.object) && feature.equals(link.feature);
		}
	}
}
