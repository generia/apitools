package de.generia.tools.model.api.generator.table;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

import de.generia.tools.dom2table.io.DatabaseDriver;
import de.generia.tools.dom2table.io.DatabaseReader;
import de.generia.tools.dom2table.io.DatabaseWriter;
import de.generia.tools.dom2table.io.csv.CsvDatabaseDriver;
import de.generia.tools.dom2table.io.xls.XlsDatabaseDriver;
import de.generia.tools.dom2table.marshaller.DatabaseMarshaller;
import de.generia.tools.dom2table.marshaller.binding.Binding;
import de.generia.tools.dom2table.marshaller.binding.Binding.Converter;
import de.generia.tools.dom2table.marshaller.binding.Binding.Converter.Resolver;
import de.generia.tools.dom2table.marshaller.binding.BindingRowMarshaller;
import de.generia.tools.dom2table.marshaller.simple.SimpleDatabaseMarshaller;
import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EDataType;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.generator.table.model.EObjectObjectDriver;
import de.generia.tools.model.api.resource.stream.ModelInputStream;
import de.generia.tools.model.api.runtime.EObject;
import de.generia.tools.model.api.runtime.EObjectFactory;
import de.generia.tools.model.api.runtime.EPackageManager;
import de.generia.tools.model.api.runtime.io.SimpleIoContext;
import de.generia.tools.model.api.runtime.io.json.EObjectJsonReader;
import de.generia.tools.model.api.runtime.io.json.EObjectJsonWriter;

public class TableGenerator extends SimpleDatabaseMarshaller {
	private static final Logger LOG = LoggerFactory.getLogger(TableGenerator.class);

	public static void main(String[] args) throws Exception {
		String function = null;
		String schemaFile = null;
		String dataType = null;
		String inputFile = null;
		String outputFile = null;
		if (args.length >= 5) {
			function = args[0];
			schemaFile = args[1];
			dataType = args[2];
			inputFile = args[3];
			outputFile = args[4];
		}

		String tableName = "Objects";
		if (args.length >= 6) {
			tableName = args[5];
		}
		
		EPackageManager packageManager = readSchema(schemaFile);
		EClass type = packageManager.lookupElement(dataType);
		TableGenerator tableGenerator = new TableGenerator();
		if (function.equals("json2xls")) {
			EObject object = readObject(packageManager, type, inputFile);
			DatabaseDriver driver = createDatabaseDriver(outputFile);
			String tableUrl = new File(outputFile).toURI().toURL().toString();
			tableGenerator.writeTable(packageManager, object, driver, tableUrl, tableName);
		} else if (function.equals("xls2json")) {
			String tableUrl = new File(inputFile).toURI().toURL().toString();
			DatabaseDriver driver = createDatabaseDriver(inputFile);
			EObject object = tableGenerator.readTable(packageManager, type, driver, tableUrl, tableName);
			writeObject(packageManager, object, outputFile);
		} else {
			System.out.println("usage: tableGenerator <function> <schemaFile> <dataType> <inputFile> <outputFile> [<tableName>]");
			System.out.println("- <function> := \"json2xls\" | \"xls2json\"");
			System.out.println("- <schemaFile> := file providing the api-schema definition");
			System.out.println("- <dataType> := type of the root object");
			System.out.println("- <inputFile> := file to be read as input");
			System.out.println("- <outputFile> := file to be written as output");
			System.out.println("- <tableName> := name of the output table, default: 'Objects'");
		}
	}
	
	private static DatabaseDriver createDatabaseDriver(String file) {
		if (file.endsWith(".csv")) {
			return new CsvDatabaseDriver();
		}
		return new XlsDatabaseDriver();
	}

	public EObject readTable(EPackageManager packageManager, EClass type, DatabaseDriver driver, String tableUrl, String tableName) {
		DatabaseMarshaller.Context context = createContext(packageManager, type, tableName);
		DatabaseReader reader = driver.createReader(tableUrl);
		DatabaseMarshaller marshaller = new SimpleDatabaseMarshaller();
		EObject object = (EObject) marshaller.unmarshal(context, reader);
		return object;
	}

	public void writeTable(EPackageManager packageManager, EObject object, DatabaseDriver databaseDriver, String tableUrl, String tableName) {
		DatabaseMarshaller.Context context = createContext(packageManager, object.eGetType(), tableName);
		DatabaseMarshaller marshaller = new SimpleDatabaseMarshaller();		
		DatabaseWriter writer = databaseDriver.createWriter(tableUrl);
		marshaller.marshal(context, writer, object);
		writer.close();
	}

	private DatabaseMarshaller.Context createContext(EPackageManager packageManager, EClass type, String tableName) {
		Map<String, EClassifier> rowTypeMap = new HashMap<String, EClassifier>();

		EObjectFactory objectFactory = packageManager.getObjectFactory();
		EObjectObjectDriver objectDriver = new EObjectObjectDriver(packageManager);
		TableTreeDriver treeDriver = new TableTreeDriver(objectFactory);
		TableTreeBuilder treeBuilder = new TableTreeBuilder(objectDriver, treeDriver);
		TableModelDriver modelDriver = new TableModelDriver(type, rowTypeMap, objectDriver, treeDriver, treeBuilder);

		SimpleDatabaseMarshaller.NamespaceContext context = new SimpleDatabaseMarshaller.NamespaceContext(modelDriver);

		Binding<EClassifier> binding = createBinding(packageManager);
		Converter.Resolver<EClassifier> converterResolver = createConverterResolver(new TableConverter(objectFactory));
		context.registerRowMarshaller(tableName, new BindingRowMarshaller<EClassifier>(binding, converterResolver, treeBuilder));
		return context;
	}

	private static Binding<EClassifier> createBinding(EPackageManager packageManager) {
		Binding<EClassifier> binding = new Binding<EClassifier>();
		binding.register(null, TableTreeBuilder.LOCATION, "Location", String.class);
		binding.register(null, TableTreeBuilder.ID, "Entity-Id", String.class);
		binding.register(null, TableTreeBuilder.TYPE, "Type", String.class);
		binding.register(null, TableTreeBuilder.PARENT_TYPE, "Parent-Type", String.class);
		binding.register(null, TableTreeBuilder.CHILD_PROPERTY, "Child-Property", String.class);
		
		List<EClass> schemaTypes = new ArrayList<>();
		collectSchemaTypes(packageManager.getPackage(), schemaTypes);
		
		for (EClass schemaType : schemaTypes) {
			for (EStructuralFeature feature : schemaType.getStructuralFeatures()) {
				String propertyName = feature.getName();
				String columnName = schemaType.getName() + "." + feature.getName();
				Class<?> columnType;
				if (feature instanceof EAttribute) {
					columnType = getColumnType((EAttribute)feature);
					registerBinding(binding, schemaType, propertyName, columnName, columnType);
				} else {
					EReference reference = (EReference) feature;
					if (!reference.isContainment()) {
						columnType = reference.isMany() ? List.class : String.class;
						registerBinding(binding, schemaType, propertyName, columnName, columnType);
					}
				}
			}
		}
		return binding;
	}

	private static void registerBinding(Binding<EClassifier> binding, EClass schemaType, String propertyName, String columnName, Class<?> columnType) {
		LOG.info("registering binding: schema-type='{}' property-name='{}' column-name='{}' column-typed='{}' ...", new Object[]{schemaType.getName(), propertyName, columnName, columnType.getName()});
		binding.register(schemaType, propertyName, columnName, columnType);
	}

	private static Class<?> getColumnType(EAttribute feature) {
		EClassifier classifier = feature.getType();
		if (!(classifier instanceof EDataType)) {
			throw new IllegalArgumentException("type of attribute '" + feature.getName() + "' is not a datatype");
		}
		if (feature.isMany()) {
			return List.class;
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
		LOG.info("adding schema-type '{}' ...", type.getName());
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
			return true;
		}
		return false;
	}

	private static Resolver<EClassifier> createConverterResolver(final TableConverter converter) {
		Converter.Resolver<EClassifier> converterResolver = new Converter.Resolver<EClassifier>() {
			@Override
			public Converter lookup(EClassifier objType, Class<?> colType, String name) {
				return converter;
			}
		};
		return converterResolver;
	}

	private static EPackageManager readSchema(String file) throws Exception {
		ModelInputStream schemaStream = new ModelInputStream();
		InputStream inputStream = new FileInputStream(file);
		EPackage schema = schemaStream.read(inputStream);
		EPackageManager packageManager = new EPackageManager(null, schema);
		return packageManager;
	}

	private static EObject readObject(EPackageManager packageManager, EClass type, String file) throws IOException, JsonParseException {
		JsonFactory jsonFactory = new JsonFactory();
		SimpleIoContext context = new SimpleIoContext(packageManager);
		JsonParser jp = jsonFactory.createParser(new File(file));
		EObjectJsonReader reader = new EObjectJsonReader(context, jp);
		return (EObject) reader.read(type);
	}
	
	private static void writeObject(EPackageManager packageManager, EObject object, String file) throws IOException {
		JsonFactory jsonFactory = new JsonFactory();
		Writer fileWriter = new FileWriter(file);
		JsonGenerator jg = jsonFactory.createGenerator(fileWriter);
		jg.setPrettyPrinter(new DefaultPrettyPrinter());
		SimpleIoContext context = new SimpleIoContext(packageManager);
		EObjectJsonWriter writer = new EObjectJsonWriter(context, jg);
		writer.write(object);
		jg.flush();
		fileWriter.flush();
		fileWriter.close();
	}

	private static class TableConverter implements Converter {

		private EObjectFactory objectFactory;

		public TableConverter(EObjectFactory objectFactory) {
			this.objectFactory = objectFactory;
		}

		@Override
		public Object toRowValue(Object objValue) {
			if (objectFactory.isEnumValue(objValue)) {
				return objectFactory.toEnumName(objValue);
			}
			return objValue;
		}

		@Override
		public Object toObjValue(Object rowValue) {
			return rowValue;
		}
	}
}
