package de.generia.tools.model.api.runtime.typed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import de.generia.tools.model.api.runtime.io.EObjectReader;
import de.generia.tools.model.api.runtime.io.EObjectWriter;
import de.generia.tools.model.api.runtime.io.SimpleIoContext;
import de.generia.tools.model.api.runtime.io.json.EObjectJsonReader;
import de.generia.tools.model.api.runtime.io.json.EObjectJsonWriter;
import de.generia.tools.model.api.runtime.typed.gen.model.Assessment;
import de.generia.tools.model.api.runtime.typed.gen.model.Company;
import de.generia.tools.model.api.runtime.typed.gen.model.Contact;
import de.generia.tools.model.api.runtime.typed.gen.model.Employee;
import de.generia.tools.model.api.runtime.typed.gen.model.Manager;
import de.generia.tools.model.api.runtime.typed.gen.model.Project;
import de.generia.tools.model.api.runtime.typed.gen.model.Workstation;

public class TypedEObjectTest {

	private EPackageManager packageManager;
	private EClass companyType;
	private EObject acme;
	
	private TypedEPackageManager typedPackageManager;
	private EClass typedCompanyType;
	private Company typedAcme;

    @Before
	public void setup() {
    	String modelPackageRoot = TypedEObjectTest.class.getPackage().getName() + ".gen";
    	TypedEObjectFactory objectFactory = new TypedEObjectFactory(modelPackageRoot);
		CompanyTestData companyTestData = new CompanyTestData(objectFactory);
    	packageManager = companyTestData.getPackageManager();
		companyType = packageManager.lookupElement("/companymgmt/model/Company");
		acme = companyTestData.createAcmeCompany();
		
    	TypedCompanyTestData typedCompanyTestData = new TypedCompanyTestData(objectFactory);
    	typedPackageManager = typedCompanyTestData.getPackageManager();
		typedCompanyType = typedPackageManager.lookupElement(Company.class);
		typedAcme = typedCompanyTestData.createAcmeCompany();
	}
	
	@Test
	public void testInstanceTypes() throws IOException {
		assertInstance(Company.class, acme);
		Company typedAcme = (Company) acme;
		assertInstance(String.class, typedAcme.getName());
		assertInstance(String.class, typedAcme.getHomepage());
		assertInstance(Set.class, typedAcme.getEmployees());
		assertInstance(Set.class, typedAcme.getProjects());
		assertInstance(List.class, typedAcme.getWorkstations());
		
		Iterator<Project> projects = typedAcme.getProjects().iterator();
		Project bigBridge = projects.next();
		assertInstance(String.class, bigBridge.getName());
		assertInstance(Company.class, bigBridge.getCompany());
		
		Iterator<Workstation> workstations = typedAcme.getWorkstations().iterator();
		Workstation blueDredge = workstations.next();
		assertInstance(String.class, blueDredge.getName());
		assertInstance(Company.class, blueDredge.getCompany());
		assertInstance(Assessment.class, blueDredge.getAssessment());

		Iterator<Employee> employees = typedAcme.getEmployees().iterator();
		Employee curly = employees.next();
		assertInstance(Employee.class, curly);
		assertInstance(String.class, curly.getName());
		assertInstance(String.class, curly.getLogins());
		assertInstance(Company.class, curly.getCompany());
		assertNull(curly.getContact());

		Employee moe = employees.next();
		assertInstance(Employee.class, moe);
		assertInstance(String.class, moe.getName());
		assertInstance(String.class, moe.getLogins());
		assertInstance(Company.class, moe.getCompany());
		assertNull(moe.getContact());
		
		Employee larry = employees.next();
		assertInstance(Manager.class, larry);
		assertInstance(String.class, larry.getName());
		assertInstance(String.class, larry.getLogins());
		assertInstance(Company.class, larry.getCompany());
		assertInstance(Contact.class, larry.getContact());
}
	
	private void assertInstance(Class<?> expectedType, Object actualValue) {
		assertTrue(expectedType.isInstance(actualValue));
	}

	@Test
	public void testContainment() throws IOException {
		testContainment(packageManager, companyType, acme);
		testContainment(typedPackageManager, typedCompanyType, (EObject)typedAcme);
	}
	
	@Test
	public void testGraph() throws IOException {
		testGraph(packageManager, companyType, acme);
		testGraph(typedPackageManager, typedCompanyType, (EObject)typedAcme);
	}
	
	@Test
	public void testMixed() throws IOException {
		testMixed(packageManager, acme, typedPackageManager, typedCompanyType);
		testMixed(typedPackageManager, (EObject)typedAcme, packageManager, companyType);
	}
	
	private void testMixed(EPackageManager packageManager1, EObject acme1, EPackageManager packageManager2, EClass companyType2) throws IOException {
		SimpleIoContext context1 = new SimpleIoContext(packageManager1, packageManager1.getObjectFactory(), false);
		SimpleIoContext context2 = new SimpleIoContext(packageManager2, packageManager2.getObjectFactory(), false);
		JsonFactory jsonFactory = new JsonFactory();

		String acmeJson = writeObject(context1, jsonFactory, acme1);
		EObject acme2 = readObject(context2, jsonFactory, companyType2, acmeJson);
		String acmeJson2 = writeObject(context2, jsonFactory, acme2);
	
		assertEquals(acmeJson, acmeJson2);
	}


	private void testContainment(EPackageManager packageManager, EClass companyType, EObject acme) throws IOException {
		SimpleIoContext context = new SimpleIoContext(packageManager);
		JsonFactory jsonFactory = new JsonFactory();

		String acmeJson = writeObject(context, jsonFactory, acme);
		EObject acme2 = readObject(context, jsonFactory, companyType, acmeJson);
		String acmeJson2 = writeObject(context, jsonFactory, acme2);
	
		assertEquals(acmeJson, acmeJson2);
	}
	
	private void testGraph(EPackageManager packageManager, EClass companyType, EObject acme) throws IOException {
		SimpleIoContext context = new SimpleIoContext(packageManager, packageManager.getObjectFactory(), false);
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
