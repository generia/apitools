package de.generia.tools.model.api.runtime.io.json;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.fasterxml.jackson.core.JsonGenerator;

import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.runtime.EObject;
import de.generia.tools.model.api.runtime.io.EObjectWriter;
import de.generia.tools.model.api.runtime.util.EObjectSet;

public class EObjectJsonWriter implements EObjectWriter {
	
	private Set<EObject> writtenObjects = new EObjectSet();
	private Stack<EStructuralFeature> featureStack = new Stack<>();
	
	private Context context;
	private JsonGenerator jg;
	
	public EObjectJsonWriter(Context context, JsonGenerator jg) {
		this.context = context;
		this.jg = jg;
	}
	
	public void write(Object value) throws IOException {
		writeElement(value);
	}
	
	private void writeElement(Object value) throws IOException {
		
		if (value instanceof EObject) {
			writeObject((EObject)value);
		} else if (value instanceof Map) {
			writeMap((Map<?,?>)value);
		} else if (value instanceof Collection) {
			writeCollection((Collection<?>)value);
		} else if (context.isEnumValue(value)) {
			writeEnum(value);
		} else {
			writeValue(value);
		}
	}

	private void writeEnum(Object value) throws IOException {
		jg.writeString(context.toEnumName(value));
	}

	private void writeValue(Object value) throws IOException {
		if (value == null) {
			jg.writeNull();
			return;
		}
		Class<?> clazz = value.getClass();
		if (value instanceof String) {
			jg.writeString((String) value);
		} else if (value instanceof Boolean || clazz.equals(boolean.class)) {
			jg.writeBoolean((boolean) value);
		} else if (value instanceof BigInteger) {
			jg.writeNumber((BigInteger)value);
		} else if (value instanceof BigDecimal) {
			jg.writeNumber((BigDecimal)value);
		} else if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
			jg.writeNumber((int)value);
		} else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
			jg.writeNumber((long)value);
		} else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
			jg.writeNumber((double)value);
		} else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
			jg.writeNumber((float)value);
		} else {
			throw new IllegalArgumentException("unsupported value-type '" + clazz + "' while writing value '" + value + "'");
		}
	}

	private void writeObject(EObject value) throws IOException {
		EStructuralFeature activeFeature = getActiveFeature();
		boolean writeRef = activeFeature != null && !context.isAggregationFeature(activeFeature);
		if (writeRef || writtenObjects.contains(value)) {
			writeObjectRef(value);
			return;
		}
		writtenObjects.add(value);
		jg.writeStartObject();
		if (needsTypeHint(activeFeature, value)) {
			jg.writeStringField(context.getTypeHintProperty(), context.getTypeHint(value.eGetType()));
		}
		Collection<EStructuralFeature> filteredFeatures = context.getFilteredFeatures(value, featureStack);
		for (EStructuralFeature feature : filteredFeatures) {
			Object featureValue = value.get(feature.getName());
			if (hasFeatureValue(featureValue)) {
				featureStack.push(feature);
				jg.writeFieldName(feature.getName());
				writeElement(featureValue);
				featureStack.pop();
			}
		}
		jg.writeEndObject();
	}

	@SuppressWarnings("unchecked")
	private boolean hasFeatureValue(Object featureValue) {
		if (featureValue == null) {
			return false;
		}
		if (!(featureValue instanceof Collection)) {
			return true;
		}
		return !((Collection<Object>) featureValue).isEmpty();
	}

	private boolean needsTypeHint(EStructuralFeature activeFeature, EObject object) {
		if (activeFeature == null) {
			return false;
		}
		return !activeFeature.getType().equals(object.eGetType()) && context.needsTypeHint(activeFeature);
	}

	private void writeObjectRef(EObject value) throws IOException {
		String ref = context.getObjectId(value);
		jg.writeString(ref);
	}

	private EStructuralFeature getActiveFeature() {
		if (featureStack.isEmpty()) {
			return null;
		}
		return featureStack.peek();
	}

	private void writeCollection(Collection<?> value) throws IOException {
		jg.writeStartArray();
		for (Object item : value) {
			writeElement(item);
		}
		jg.writeEndArray();
	}

	private void writeMap(Map<?,?> value) throws IOException {
		jg.writeStartObject();
		for (Map.Entry<?, ?> entry : value.entrySet()) {
			String key = getFieldName(entry.getKey());
			jg.writeFieldName(key);
			writeElement(entry.getValue());
		}
		jg.writeEndObject();
	}

	private String getFieldName(Object value) {
		if (context.isEnumValue(value)) {
			return context.toEnumName(value);
		}
		return value.toString();
	}
}
