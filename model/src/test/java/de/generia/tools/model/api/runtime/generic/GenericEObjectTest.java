package de.generia.tools.model.api.runtime.generic;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.runtime.CompanyTestData;
import de.generia.tools.model.api.runtime.EObject;
import de.generia.tools.model.api.runtime.EPackageManager;
import de.generia.tools.model.api.runtime.generic.GenericEObjectFactory;
import de.generia.tools.model.api.runtime.io.EObjectReader;
import de.generia.tools.model.api.runtime.io.EObjectWriter;
import de.generia.tools.model.api.runtime.io.SimpleIoContext;
import de.generia.tools.model.api.runtime.io.json.EObjectJsonReader;
import de.generia.tools.model.api.runtime.io.json.EObjectJsonWriter;

public class GenericEObjectTest {

	private EPackageManager packageManager;
	private EClass companyType;
	private EObject acme;

    @Before
	public void setup() {
    	CompanyTestData companyTestData = new CompanyTestData(new GenericEObjectFactory());
    	packageManager = companyTestData.getPackageManager();
		companyType = packageManager.lookupElement("/companymgmt/model/Company");
		acme = companyTestData.createAcmeCompany();
	}
	
	@Test
	public void test() throws IOException {
		SimpleIoContext context = new SimpleIoContext(packageManager);
		JsonFactory jsonFactory = new JsonFactory();

		String acmeJson = writeObject(context, jsonFactory, acme);
		EObject acme2 = readObject(context, jsonFactory, companyType, acmeJson);
		String acmeJson2 = writeObject(context, jsonFactory, acme2);
	
		assertEquals(acmeJson, acmeJson2);
	}
	
	@Test
	public void testGraph() throws IOException {
		SimpleIoContext context = new SimpleIoContext(packageManager, new GenericEObjectFactory(), false);
		JsonFactory jsonFactory = new JsonFactory();

		String acmeJson = writeObject(context, jsonFactory, acme);
		EObject acme2 = readObject(context, jsonFactory, companyType, acmeJson);
		String acmeJson2 = writeObject(context, jsonFactory, acme2);
	
		assertEquals(acmeJson, acmeJson2);
	}

	private EObject readObject(EObjectReader.Context context, JsonFactory jsonFactory, EClass type, String json) throws IOException, JsonParseException {
		JsonParser jp = jsonFactory.createParser(json);
		EObjectJsonReader reader = new EObjectJsonReader(context, jp);
		return (EObject) reader.read(type);
	}

	private String writeObject(EObjectWriter.Context context, JsonFactory jsonFactory, EObject object) throws IOException {
		StringWriter stringWriter = new StringWriter();
		
		JsonGenerator jg = jsonFactory.createGenerator(stringWriter);
		jg.setPrettyPrinter(new DefaultPrettyPrinter());
		EObjectJsonWriter writer = new EObjectJsonWriter(context, jg);
		writer.write(object);
		jg.flush();

		return stringWriter.toString();
	}
}
