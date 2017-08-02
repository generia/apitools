package de.generia.tools.model.api.generator.table;

import java.io.File;
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

import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import test.eclipse.standalone.StandaloneRegistryProvider;
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
import de.generia.tools.model.api.resource.ApiResourceBinding;
import de.generia.tools.model.xecore.schema.ModelRegistry;
import de.generia.tools.model.xecore.schema.ModelSchema;

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

	public static class ApiTreeBuilder implements ObjectBuilder<Class<?>> {
		private static final String TYPE = "type";
		private static final String LOCATION = "location";
		private static final String PATH = "path";
		private static final String NAME = "name";

		private ObjectDriver<Class<?>> objectDriver;
		private TreeDriver treeDriver;
		private Object contextNode;
		private Object currentNode;
		private Namespace namespace;
		private Map<String, Set<ProxyHolder>> proxyHolderMap = new HashMap<String, Set<ProxyHolder>>();

		public ApiTreeBuilder(ObjectDriver<Class<?>> objectDriver, TreeDriver treeDriver) {
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

		private Object createChild(Class<?> type, String location, Object context) {
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
		public Object createObject(Class<?> type, Map<String, Object> metaInfo) {
			String typeName = (String) metaInfo.get(TYPE);
			String path = (String) metaInfo.get(PATH);
			String location = (String) metaInfo.get(LOCATION);

			Class<?> typeClass = objectDriver.getTypeClass(typeName);
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
		public void setProperty(Property<Class<?>> property, Object value) {
			Class<?> currentType = objectDriver.getObjectType(currentNode);
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
					Class<?> typeClass = objectDriver.getPropertyType(objectDriver.getObjectType(rowObject), property.getName());
					// NOTE: proxy handling is needed to cope with abstract classes for property types
					Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { typeClass, ObjectRef.class }, new ProxyInvocationHandler(name));
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
		public Object getProperty(Property<Class<?>> property) {
			Class<?> currentType = objectDriver.getObjectType(currentNode);
			if (!objectDriver.hasProperty(currentType, property.getName())) {
				return null;
			}
			Object value = objectDriver.getValue(currentNode, property.getName());
			if (value != null) {
				Class<?> propertyType = objectDriver.getPropertyType(property.getDefinedIn(), property.getName());
				if (objectDriver.isObjectType(propertyType)) {
					return "#" + getPath(value);
				}
			}
			return value;
		}

		@Override
		public Class<?> getPropertyType(Property<Class<?>> property) {
			if (property.getDefinedIn() == null) {
				return String.class;
			}
			return objectDriver.getPropertyType(property.getDefinedIn(), property.getName());
		}

		@Override
		public Class<?> getObjectType() {
			return objectDriver.getObjectType(currentNode);
		}

		@Override
		public boolean hasProperty(Property<Class<?>> property) {
			Class<?> objectType = getObjectType();
			if (!objectDriver.isAssignableFrom(property.getDefinedIn(), objectType)) {
				return false;
			}
			return objectDriver.hasProperty(objectType, property.getName());
		}
	}

	public static class ApiModelDriver extends SimpleModelDriver<Class<?>> {
		private TreeDriver treeDriver;
		private ApiTreeBuilder treeBuilder;

		public ApiModelDriver(Class<?> databaseType, Map<String, Class<?>> rowTypeMap, ObjectDriver<Class<?>> objectDriver, TreeDriver treeDriver, ApiTreeBuilder treeBuilder) {
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
		private ObjectBuilder<Class<?>> objectBuilder;

		public ApiConverter(ApiModelDriver modelDriver, ObjectBuilder<Class<?>> objectBuilder) {
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

		Map<String, Class<?>> rowTypeMap = new HashMap<String, Class<?>>();
		final ApiObjectDriver objectDriver = new ApiObjectDriver();
		//TreeDriver treeDriver = new ApiTreeDriver();
		TreeDriver treeDriver = new TreeDefinitionTreeDriver<Class<?>>(createTreeDefinition(), objectDriver);
		ApiTreeBuilder treeBuilder = new ApiTreeBuilder(objectDriver, treeDriver);
		ApiModelDriver modelDriver = new ApiModelDriver(EPackage.class, rowTypeMap, objectDriver, treeDriver, treeBuilder);

		final SimpleDatabaseMarshaller.NamespaceContext context = new SimpleDatabaseMarshaller.NamespaceContext(modelDriver);

		Binding<Class<?>> binding = createBinding();
		Converter.Resolver<Class<?>> converterResolver = createConverterResolver(new ApiConverter(modelDriver, treeBuilder));
		context.registerRowMarshaller("Elements", new BindingRowMarshaller<Class<?>>(binding, converterResolver, treeBuilder));
		DatabaseDriver driver = new XlsDatabaseDriver();
		DatabaseMarshaller marshaller = new SimpleDatabaseMarshaller();

		String uri1 = "/Users/alex/home/dev/ws-dev/dom2table/tst/pdml/pdml.api";
		String uri2 = "/Users/alex/home/dev/ws-dev/dom2table/tst/api/pdml-gen.api.xls";
		String uri3 = "/Users/alex/home/dev/ws-dev/dom2table/tst/api/pdml-gen.api.xls.api";

		String function = null;
		String inputFile = null;
		String outputFile = null;
		if (args.length == 3) {
			function = args[0];
			inputFile = args[1];
			outputFile = args[2];
		}
		
		if (function == null || function.equals("api2xls")) {
			uri1 = inputFile != null ? inputFile : uri1;
			uri2 = outputFile != null ? outputFile : uri2;
			String url2 = new File(uri2).toURI().toURL().toString();
			EPackage apiModel = readApiModel(uri1);
			DatabaseWriter writer = driver.createWriter(url2);
			marshaller.marshal(context, writer, apiModel);
			writer.close();
		}
		if (function == null || function.equals("xls2api")) {
			uri2 = inputFile != null ? inputFile : uri2;
			uri3 = outputFile != null ? outputFile : uri3;
			String url2 = new File(uri2).toURI().toURL().toString();
			DatabaseReader reader = driver.createReader(url2);
			EPackage apiModel2 = (EPackage) marshaller.unmarshal(context, reader);
			apiModel2 = (EPackage) treeBuilder.contextNode;
			writeApiModel(uri3, apiModel2);
		}
	}

	private static TreeDefinition<Class<?>> createTreeDefinition() {
		TreeDefinition<Class<?>> treeDefinition = new TreeDefinition<Class<?>>();
		treeDefinition.addParentProperty(EPackage.class, "superPackage");
		treeDefinition.addChildProperty(EPackage.class, "classifiers");
		treeDefinition.addChildProperty(EPackage.class, "subPackages");
		treeDefinition.addParentProperty(EClassifier.class, "package");
		treeDefinition.addParentProperty(EClassifier.class, "containingClass");
		treeDefinition.addParentProperty(EStructuralFeature.class, "containingClass");
		treeDefinition.addParentProperty(EOperation.class, "containingClass");
		treeDefinition.addChildProperty(EOperation.class, "parameters");
		treeDefinition.addParentProperty(EParameter.class, "operation");
		treeDefinition.addChildProperty(EModelElement.class, "annotations");
		treeDefinition.addChildProperty(EClass.class, "structuralFeatures");
		treeDefinition.addChildProperty(EClass.class, "operations");
		treeDefinition.addChildProperty(EClass.class, "nestedClassifiers");
		treeDefinition.addChildProperty(EEnum.class, "literals");
		treeDefinition.addParentProperty(EEnumLiteral.class, "enum");
		return treeDefinition;
	}

	private static Binding<Class<?>> createBinding() {
		Binding<Class<?>> binding = new Binding<Class<?>>();
		binding.register(null, ApiTreeBuilder.LOCATION, "Location", String.class);
		binding.register(null, ApiTreeBuilder.PATH, "Path", String.class);
		binding.register(null, ApiTreeBuilder.TYPE, "Type", String.class);
		// binding.register(EAnnotation.class, "", "Name", String.class);
		binding.register(EAnnotation.class, "source", "Annotation.source", String.class);
		binding.register(EAnnotation.class, "details", "Annotation.details", String.class);
		binding.register(ENamedElement.class, "name", "NamedElement.name", String.class);
		// binding.register(EClassifier.class, "package", "Classifier.package",
		// String.class);
		binding.register(EClassifier.class, "instanceTypeName", "Classifier.instanceTypeName", String.class);
		binding.register(EClassifier.class, "defaultValue", "Classifier.defaultValue", String.class);
		binding.register(EClass.class, "abstract", "Class.abstract", boolean.class);
		binding.register(EClass.class, "interface", "Class.interface", boolean.class);
		binding.register(EClass.class, "superType", "Class.superType", String.class);
		binding.register(EEnumLiteral.class, "literal", "EnumLiteral.literal", String.class);
		binding.register(EEnumLiteral.class, "value", "EnumLiteral.value", String.class);
		binding.register(ETypedElement.class, "type", "TypedElement.type", String.class);
		binding.register(ETypedElement.class, "keyType", "TypedElement.keyType", String.class);
		binding.register(ETypedElement.class, "many", "TypedElement.many", boolean.class);
		binding.register(ETypedElement.class, "ordered", "TypedElement.ordered", boolean.class);
		binding.register(ETypedElement.class, "required", "TypedElement.required", boolean.class);
		binding.register(ETypedElement.class, "unique", "TypedElement.unique", boolean.class);
		binding.register(EOperation.class, "exceptions", "Operation.exceptions", String.class);
		binding.register(EStructuralFeature.class, "defaultValueLiteral", "StructuralFeature.defaultValueLiteral", String.class);
		binding.register(EStructuralFeature.class, "transient", "StructuralFeature.transient", boolean.class);
		binding.register(EAttribute.class, "id", "Attribute.id", boolean.class);
		binding.register(EReference.class, "opposite", "Reference.opposite", String.class);
		binding.register(EReference.class, "containment", "Reference.containment", boolean.class);
		return binding;
	}

	private static Resolver<Class<?>> createConverterResolver(final ApiConverter converter) {
		Converter.Resolver<Class<?>> converterResolver = new Converter.Resolver<Class<?>>() {
			@Override
			public Converter lookup(Class<?> objType, Class<?> colType, String name) {
				return converter;
			}
		};
		return converterResolver;
	}

	private static EPackage readApiModel(String file) throws Exception {

		// RegistryFactory.getRegistry().
		RegistryFactory.setDefaultRegistryProvider(new StandaloneRegistryProvider());
		ModelSchema lModelSchema = ModelRegistry.getModelSchema("api");
		ApiResourceBinding lSiteResourceBinding = new ApiResourceBinding(lModelSchema);
		// add missing content-type to registry (see ModelResourceBinding.addMapping())
		Resource.Factory.Registry.INSTANCE.getContentTypeToFactoryMap().put(ApiResourceBinding.CONTENT_TYPE.toString(), Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().get("api"));
		// BindingInfo lBindingInfo = lSiteResourceBinding.createBindingInfo();
		URI lUri = URI.createFileURI(file);

		// ResourceSetImpl lResourceSet = new
		// ModelResourceSetImpl(lBindingInfo);
		ResourceSetImpl lResourceSet = new ResourceSetImpl();
		Resource lResource = lResourceSet.createResource(lUri, ApiResourceBinding.CONTENT_TYPE.toString());

		lResource.load(null);
		Object lObject = lResource.getContents().get(0);
		System.out.println("object: " + lObject);
		return (EPackage) lObject;
	}

	private static void writeApiModel(String file, EPackage apiModel) throws Exception {

		// RegistryFactory.getRegistry().
		//RegistryFactory.setDefaultRegistryProvider(new StandaloneRegistryProvider());
		ModelSchema lModelSchema = ModelRegistry.getModelSchema("api");
		ApiResourceBinding lSiteResourceBinding = new ApiResourceBinding(lModelSchema);
		Resource.Factory.Registry.INSTANCE.getContentTypeToFactoryMap().put(ApiResourceBinding.CONTENT_TYPE.toString(), Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().get("api"));
		// BindingInfo lBindingInfo = lSiteResourceBinding.createBindingInfo();
		URI lUri = URI.createFileURI(file);

		// ResourceSetImpl lResourceSet = new
		// ModelResourceSetImpl(lBindingInfo);
		ResourceSetImpl lResourceSet = new ResourceSetImpl();
		Resource lResource = lResourceSet.createResource(lUri, ApiResourceBinding.CONTENT_TYPE.toString());

		//lResource.getContents().add(apiModel);
		lResource.save(null);
	}

}
