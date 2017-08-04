package de.generia.tools.model.api.generator.table;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;

import de.generia.tools.dom2table.io.DatabaseDriver;
import de.generia.tools.dom2table.io.DatabaseReader;
import de.generia.tools.dom2table.io.DatabaseWriter;
import de.generia.tools.dom2table.io.xls.XlsDatabaseDriver;
import de.generia.tools.dom2table.marshaller.DatabaseMarshaller;
import de.generia.tools.dom2table.marshaller.binding.Binding;
import de.generia.tools.dom2table.marshaller.binding.Binding.Converter;
import de.generia.tools.dom2table.marshaller.binding.Binding.Converter.Resolver;
import de.generia.tools.dom2table.marshaller.binding.BindingRowMarshaller;
import de.generia.tools.dom2table.marshaller.binding.ObjectBuilder;
import de.generia.tools.dom2table.marshaller.simple.SimpleDatabaseMarshaller;
import de.generia.tools.dom2table.marshaller.simple.SimpleModelDriver;
import de.generia.tools.dom2table.model.ObjectDriver;
import de.generia.tools.dom2table.model.Property;
import de.generia.tools.dom2table.model.ecore.EcoreObjectDriver;
import de.generia.tools.dom2table.model.tree.TreeDefinition;
import de.generia.tools.dom2table.model.tree.TreeDefinitionTreeDriver;
import de.generia.tools.dom2table.model.tree.TreeDriver;
import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.api.EAnnotation;
import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EDataType;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EEnumLiteral;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.ENamedElement;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.EParameter;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.ETypedElement;
import de.generia.tools.model.api.generator.table.model.EObjectObjectDriver;
import de.generia.tools.model.api.generator.table.model.EObjectTreeDriver;
import de.generia.tools.model.api.resource.stream.ModelInputStream;
import de.generia.tools.model.api.runtime.EObject;
import de.generia.tools.model.api.runtime.EPackageManager;
import de.generia.tools.model.api.runtime.io.SimpleIoContext;
import de.generia.tools.model.api.runtime.io.json.EObjectJsonReader;

public class TableGenerator extends SimpleDatabaseMarshaller {

	private static class ApiObjectDriver extends EcoreObjectDriver {

		public ApiObjectDriver() {
			register(ApiPackage.eINSTANCE);
		}
		
		@Override
		public Class<?> getTypeClass(String typeName) {
			String fullName = ApiPackage.class.getPackage().getName() + ".E" + typeName;
			return super.getTypeClass(fullName);
		}

		@Override
		public String getTypeName(Class<?> typeClass) {
			String fullName = super.getTypeName(typeClass);
			int i = fullName.lastIndexOf(".E");
			return fullName.substring(i + ".E".length());
		}
	};

	public static class ApiTreeBuilder implements ObjectBuilder<EClassifier> {
		private static final String TYPE = "type";
		private static final String LOCATION = "location";
		private static final String PATH = "path";
		private static final String NAME = "name";

		private ObjectDriver<EClassifier> objectDriver;
		private TreeDriver treeDriver;
		private Object contextNode;
		private Object currentNode;
		private Namespace namespace;
		private Map<String, Set<ProxyHolder>> proxyHolderMap = new HashMap<String, Set<ProxyHolder>>();

		public ApiTreeBuilder(ObjectDriver<EClassifier> objectDriver, TreeDriver treeDriver) {
			this.objectDriver = objectDriver;
			this.treeDriver = treeDriver;
			this.namespace = new Namespace();
		}

		private void resolve(ProxyHolder ph, Namespace namespace) {
			Object value = objectDriver.getValue(ph.object, ph.property);
			if (value instanceof Collection) {
				Collection values = (Collection) value;
				Collection resolved = new ArrayList();
				for (Object o : values) {
					resolved.add(resolve(o, namespace));
				}
				values.clear();
				values.addAll(resolved);
			} else {
				objectDriver.setValue(ph.object, ph.property, resolve(value, namespace));
			}
		}

		private Object resolve(Object o, Namespace namespace) {
			if (!(o instanceof ObjectRef)) {
				return o;
			}
			String reference = ((ObjectRef) o).getReference();
			Object resolved = namespace.lookup(reference);
			if (resolved == null) {
				System.err.println("can't resolve '" + reference + "'");
			}
			return resolved;
		}
		
		private Object createChild(EClassifier type, String location, Object context) {
			Object parent = getObjectByLocation(context, location);
			if (parent == null) {
				return context;
			}
			Object child = objectDriver.create(type);
			treeDriver.addChild(parent, child);
			return child;
		}

		private Object getObjectByLocation(Object context, String location) {
			if (location == null) {
				return null;
			}
			String[] steps = location.split("\\.");
			Object o = context;
			for (String step : steps) {
				int index = Integer.parseInt(step);
				List<Object> children = treeDriver.getChildren(o);
				if (index < children.size()) {
					Object child = children.get(index);
					o = child;
				} else {
					return o;
				}
			}
			return o;
		}

		private String getLocation(Object object) {
			String location = null;
			Object o = object;
			while (o != null) {
				int index = treeDriver.getChildrenIndex(o);
				if (index != -1) {
					String format = String.format("%02d", index);
					location = location == null ? format : format + "." + location;
				}
				o = treeDriver.getParent(o);
			}
			return location;
		}

		private String getPath(Object object) {
			String path = null;
			Object o = object;
			while (o != null) {
				String name = (String) objectDriver.getValue(o, NAME);
				if (name != null) {
					path = path == null ? name : name + "." + path;
				}
				o = treeDriver.getParent(o);
			}
			return path;
		}

		@Override
		public Object createObject(EClassifier type, Map<String, Object> metaInfo) {
			String typeName = (String) metaInfo.get(TYPE);
			String path = (String) metaInfo.get(PATH);
			String location = (String) metaInfo.get(LOCATION);

			EClassifier typeClass = objectDriver.getTypeClass(typeName);
			currentNode = createChild(typeClass, location, contextNode);
			joinNamespace(path, currentNode);
			return currentNode;
		}

		private void joinNamespace(String name, Object object) {
			if (name == null) {
				return;
			}
			namespace.add(name, currentNode);
			Set<ProxyHolder> proxyHolders = proxyHolderMap.remove(name);
			if (proxyHolders != null) {
				for (ProxyHolder ph : proxyHolders) {
					resolve(ph, namespace);
				}
			}
		}

		@Override
		public void initMetaInfo(Object rowObject, Map<String, Object> metaInfo) {
			currentNode = rowObject;
			String typeName = objectDriver.getTypeName(objectDriver.getObjectType(rowObject));
			metaInfo.put(TYPE, typeName);
			metaInfo.put(PATH, getPath(rowObject));
			metaInfo.put(LOCATION, getLocation(rowObject));
		}

		@Override
		public void setProperty(Property<EClassifier> property, Object value) {
			EClassifier currentType = objectDriver.getObjectType(currentNode);
			if (!objectDriver.hasProperty(currentType, property.getName())) {
				return;
			}
			Object object = value;
			if (value instanceof String && value.toString().startsWith("#")) {
				String name = value.toString().substring(1);
				object = namespace.lookup(name);
				if (object == null) {
					System.out.println("forward reference found '" + value + "'");
					Object rowObject = currentNode;
					EClassifier typeClass = objectDriver.getPropertyType(objectDriver.getObjectType(rowObject), property.getName());
					// NOTE: proxy handling is needed to cope with abstract classes for property types
					Object proxy = null; // TODO: Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { typeClass, ObjectRef.class }, new ProxyInvocationHandler(name));
					Set<ProxyHolder> proxyHolders = proxyHolderMap.get(name);
					if (proxyHolders == null) {
						proxyHolders = new HashSet<ProxyHolder>();
						proxyHolderMap.put(name, proxyHolders);
					}
					// Object proxy = objectDriver.create(typeClass);
					proxyHolders.add(new ProxyHolder(rowObject, property.getName()));
					// proxyMap.put(proxy, columnValue.toString());
					object = proxy;
				}
			}
			objectDriver.setValue(currentNode, property.getName(), object);
		}

		@Override
		public Object getProperty(Property<EClassifier> property) {
			EClassifier currentType = objectDriver.getObjectType(currentNode);
			if (!objectDriver.hasProperty(currentType, property.getName())) {
				return null;
			}
			Object value = objectDriver.getValue(currentNode, property.getName());
			if (value != null) {
				EClassifier propertyType = objectDriver.getPropertyType(property.getDefinedIn(), property.getName());
				if (objectDriver.isObjectType(propertyType)) {
					return "#" + getPath(value);
				}
			}
			return value;
		}

		@Override
		public EClassifier getPropertyType(Property<EClassifier> property) {
			if (property.getDefinedIn() == null) {
				return null; // TODO: String.class;
			}
			return objectDriver.getPropertyType(property.getDefinedIn(), property.getName());
		}

		@Override
		public EClassifier getObjectType() {
			return objectDriver.getObjectType(currentNode);
		}

		@Override
		public boolean hasProperty(Property<EClassifier> property) {
			EClassifier objectType = getObjectType();
			if (!objectDriver.isAssignableFrom(property.getDefinedIn(), objectType)) {
				return false;
			}
			return objectDriver.hasProperty(objectType, property.getName());
		}
	}

	public static class ApiModelDriver extends SimpleModelDriver<EClassifier> {
		private TreeDriver treeDriver;
		private ApiTreeBuilder treeBuilder;

		public ApiModelDriver(EClassifier databaseType, Map<String, EClassifier> rowTypeMap, ObjectDriver<EClassifier> objectDriver, TreeDriver treeDriver, ApiTreeBuilder treeBuilder) {
			super(databaseType, rowTypeMap, objectDriver);
			this.treeDriver = treeDriver;
			this.treeBuilder = treeBuilder;
		}

		@Override
		protected Object createTableObject(Object databaseObject, String tableProperty) {
			treeBuilder.contextNode = databaseObject;
			return databaseObject;
		}

		public Iterable<Object> getRowObjects(Object databaseObject, String tableProperty) {
			List<Object> objects = new ArrayList<Object>();
			collectObjects(databaseObject, objects);
			return objects;
		}

		private void collectObjects(Object object, List<Object> objects) {
			objects.add(object);
			for (Object child : treeDriver.getChildren(object)) {
				collectObjects(child, objects);
			}
		};
	};

	public static class ApiConverter implements Converter {
		private ApiModelDriver modelDriver;
		private ObjectBuilder<EClassifier> objectBuilder;

		public ApiConverter(ApiModelDriver modelDriver, ObjectBuilder<EClassifier> objectBuilder) {
			this.modelDriver = modelDriver;
			this.objectBuilder = objectBuilder;
		}

		@Override
		public Object toRowValue(Object objValue) {
			return objValue;
		}

		@Override
		public Object toObjValue(Object rowValue) {
			Object object = rowValue;
			return object;
		}
	}

	public static class Namespace {
		private Map<String, Object> namespace = new TreeMap<String, Object>();

		public Map<String, Object> getNamespace() {
			return namespace;
		}

		public void add(String name, Object object) {
			namespace.put(name, object);
		}

		public Object lookup(String name) {
			if (name == null) {
				return null;
			}
			return namespace.get(name);
		}
	}

	private static interface ObjectRef {
		public String getReference();
	}

	private static class ProxyInvocationHandler implements InvocationHandler {
		private String reference;

		public ProxyInvocationHandler(String reference) {
			this.reference = reference;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			String name = method.getName();
			if (name.equals("equals")) {
				return proxy.equals(args[0]);
			} else if (name.equals("hashCode")) {
				return proxy.hashCode();
			} else if (name.equals("eIsProxy")) {
				return false;
			} else if (name.equals("getReference")) {
				return reference;
			}
			return null;
		}
	}

	private static class ProxyHolder {

		private Object object;
		private String property;

		public ProxyHolder(Object object, String property) {
			this.object = object;
			this.property = property;
		}

		@Override
		public int hashCode() {
			return object.hashCode() * property.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof ProxyHolder)) {
				return false;
			}
			ProxyHolder p = (ProxyHolder) obj;
			if (object.equals(p.object)) {
				return property.equals(p.property);
			}
			return false;
		}
	}

	public static void main(String[] args) throws Exception {

		Map<String, EClassifier> rowTypeMap = new HashMap<String, EClassifier>();

		String schemaId = "companymgmt.v1";
		String schemaFile = "/Users/qxn7720/home/prj/bmw/ds2/ws-api/apitools/model/src/test/resources/de/generia/tools/model/api/runtime/companymgmt.api";
		String dataFile = "/Users/qxn7720/home/prj/bmw/ds2/ws-api/apitools/model/src/test/resources/de/generia/tools/model/api/runtime/Acme-Inc..v1.json";
		String dataType = "Company";
		String tableFile = "/Users/qxn7720/home/tmp/table-generator/Acme Inc..v1.xlsx";

		String function = null;
		String inputFile = null;
		String outputFile = null;
		if (args.length == 3) {
			function = args[0];
			inputFile = args[1];
			outputFile = args[2];
		}

		EPackageManager packageManager = readApiSchema(schemaId, schemaFile);
		EClass type = packageManager.lookupElement(dataType);

		final ObjectDriver<EClassifier> objectDriver = new EObjectObjectDriver(packageManager);
		//TreeDriver treeDriver = new ApiTreeDriver();
		TreeDriver treeDriver = new EObjectTreeDriver(packageManager.getObjectFactory());
		ApiTreeBuilder treeBuilder = new ApiTreeBuilder(objectDriver, treeDriver);
		ApiModelDriver modelDriver = new ApiModelDriver(type, rowTypeMap, objectDriver, treeDriver, treeBuilder);

		final SimpleDatabaseMarshaller.NamespaceContext context = new SimpleDatabaseMarshaller.NamespaceContext(modelDriver);

		Binding<EClassifier> binding = createBinding(packageManager, type);
		Converter.Resolver<EClassifier> converterResolver = createConverterResolver(new ApiConverter(modelDriver, treeBuilder));
		context.registerRowMarshaller("Elements", new BindingRowMarshaller<EClassifier>(binding, converterResolver, treeBuilder));
		DatabaseDriver driver = new XlsDatabaseDriver();
		DatabaseMarshaller marshaller = new SimpleDatabaseMarshaller();
		
		if (function == null || function.equals("json2xls")) {

			EObject object = readObject(packageManager, type, dataFile);
			String tableUrl = new File(tableFile).toURI().toURL().toString();
			DatabaseWriter writer = driver.createWriter(tableUrl);
			marshaller.marshal(context, writer, object);
			writer.close();
		}
	}

	private static Binding<EClassifier> createBinding(EPackageManager packageManager, EClass type) {
		Binding<EClassifier> binding = new Binding<EClassifier>();
		binding.register(null, ApiTreeBuilder.LOCATION, "Location", String.class);
		binding.register(null, ApiTreeBuilder.PATH, "Path", String.class);
		binding.register(null, ApiTreeBuilder.TYPE, "Type", String.class);
		
		List<EClass> schemaTypes = new ArrayList<>();
		collectSchemaTypes(packageManager.getPackage(), schemaTypes);
		
		for (EClass schemaType : schemaTypes) {
			for (EStructuralFeature feature : schemaType.getStructuralFeatures()) {
				if (feature instanceof EAttribute) {
					String propertyName = feature.getName();
					String columnName = schemaType.getName() + "." + feature.getName();
					Class<?> columnType = getColumnType((EAttribute)feature);
					binding.register(schemaType, propertyName, columnName, columnType);
				}
			}
		}
		return binding;
	}

	private static Class<?> getColumnType(EAttribute feature) {
		EClassifier classifier = feature.getType();
		if (!(classifier instanceof EDataType)) {
			throw new IllegalArgumentException("type of attribute '" + feature.getName() + "' is not a datatype");
		}
		EDataType dataType = (EDataType) classifier;
		
		String instanceTypeName = dataType.getInstanceTypeName();
		if (instanceTypeName == null) {
			instanceTypeName = dataType.getName();
		}
		if ("int".equals(instanceTypeName)) {
			return int.class;
		} else if ("string".equals(instanceTypeName) || "String".equals(instanceTypeName) || dataType instanceof EEnum) {
			return String.class;
		} else if ("double".equals(instanceTypeName)) {
			return double.class;
		} else if ("boolean".equals(instanceTypeName)) {
			return boolean.class;
		} else {
			try {
				return Class.forName(instanceTypeName);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException("can't determine column-type for attribute '" + feature.getName() + "'", e);
			}
		}
	}

	private static void collectSchemaTypes(EPackage pkg, List<EClass> schemaTypes) {
		for (EClassifier classifier : pkg.getClassifiers()) {
			if (isObjectType(classifier)) {
				collectSchemaTypes((EClass) classifier, schemaTypes);
			}
		}
		for (EPackage subPackage : pkg.getSubPackages()) {
			collectSchemaTypes(subPackage, schemaTypes);
		}
	}

	private static void collectSchemaTypes(EClass type, List<EClass> schemaTypes) {
		schemaTypes.add(type);
		for (EClassifier nestedClassifier : type.getNestedClassifiers()) {
			if (isObjectType(nestedClassifier)) {
				collectSchemaTypes((EClass) nestedClassifier, schemaTypes);
			}
		}
	}

	private static boolean isObjectType(EClassifier classifier) {
		if (classifier instanceof EClass) {
			EClass cls = (EClass) classifier;
			if (cls.isInterface()) {
				return false;
			}
			for (EStructuralFeature feature : cls.getStructuralFeatures()) {
				if (feature instanceof EAttribute) {
					return true;
				}
			}
		}
		return false;
	}

	private static Resolver<EClassifier> createConverterResolver(final ApiConverter converter) {
		Converter.Resolver<EClassifier> converterResolver = new Converter.Resolver<EClassifier>() {
			@Override
			public Converter lookup(EClassifier objType, Class<?> colType, String name) {
				return converter;
			}
		};
		return converterResolver;
	}

	private static EPackageManager readApiSchema(String schemaId, String file) throws Exception {
		ModelInputStream apiStream = new ModelInputStream();
		InputStream inputStream = new FileInputStream(file);
		EPackage api = apiStream.read(inputStream);
		EPackageManager packageManager = new EPackageManager(schemaId, api);
		return packageManager;
	}

	private static EObject readObject(EPackageManager packageManager, EClass type, String file) throws IOException, JsonParseException {
		JsonFactory jsonFactory = new JsonFactory();
		SimpleIoContext context = new SimpleIoContext(packageManager);
		JsonParser jp = jsonFactory.createParser(new File(file));
		EObjectJsonReader reader = new EObjectJsonReader(context, jp);
		return (EObject) reader.read(type);
	}
}
