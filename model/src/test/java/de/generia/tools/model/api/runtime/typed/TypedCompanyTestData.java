package de.generia.tools.model.api.runtime.typed;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.resource.stream.ModelInputStream;
import de.generia.tools.model.api.runtime.CompanyTestData;
import de.generia.tools.model.api.runtime.EObjectFactory;
import de.generia.tools.model.api.runtime.typed.gen.model.Assessment;
import de.generia.tools.model.api.runtime.typed.gen.model.Company;
import de.generia.tools.model.api.runtime.typed.gen.model.Contact;
import de.generia.tools.model.api.runtime.typed.gen.model.Employee;
import de.generia.tools.model.api.runtime.typed.gen.model.Manager;
import de.generia.tools.model.api.runtime.typed.gen.model.Project;
import de.generia.tools.model.api.runtime.typed.gen.model.Workstation;

public class TypedCompanyTestData {
	private TypedEPackageManager packageManager;

	public TypedCompanyTestData(EObjectFactory objectFactory) {
		ModelInputStream apiStream = new ModelInputStream();
		InputStream inputStream = CompanyTestData.class.getResourceAsStream("companymgmt.api");
		EPackage api = apiStream.read(inputStream);
		assertNotNull(api);
		
		String modelPackageRoot = TypedCompanyTestData.class.getPackage().getName() + ".gen";
		packageManager = new TypedEPackageManager("companymgmt:v1", api, objectFactory, modelPackageRoot);
	}
	
	public TypedEPackageManager getPackageManager() {
		return packageManager;
	}
	
	public Company createAcmeCompany() {
		Company acme = create(Company.class);
		acme.setName("Acme Inc.");
		acme.setHomepage("www.acme.com");
		Set<Employee> companyEmployees = new LinkedHashSet<>();
		acme.setEmployees(companyEmployees);
		Set<Project> companyProjects = new LinkedHashSet<>();
		acme.setProjects(companyProjects);

		Employee curly = create(Employee.class);
		curly.setName("Curly Howard");
		curly.setLogins("choward");
		curly.setCompany(acme);
		companyEmployees.add(curly);

		Employee moe = create(Employee.class);
		moe.setName("Moe Howard");
		moe.setLogins("mhoward");
		moe.setCompany(acme);
		companyEmployees.add(moe);

		Manager larry = create(Manager.class);
		larry.setName("Larry Fine");
		larry.setLogins("lfine");
		larry.setStaffCar(true);
		larry.setCompany(acme);
		companyEmployees.add(larry);

		Contact larryContact = create(Contact.class);
		larryContact.setEmail("larry.fine@acme.com");
		larryContact.setPhoneNumber("555 1234567");
		larry.setContact(larryContact);

		Project bridge = create(Project.class);
		bridge.setName("Big Bridge");
		bridge.setDescription("Build a big bridge.");
		bridge.setCompany(acme);
		Set<Employee> projectEmployees = new LinkedHashSet<>();
		bridge.setEmployees(projectEmployees);
		projectEmployees.add(curly);
		projectEmployees.add(larry);
		companyProjects.add(bridge);
		Set<Project> curlyProjects = new LinkedHashSet<>();
		curly.setProjects(curlyProjects);
		curlyProjects.add(bridge);
		Set<Project> larryProjects = new LinkedHashSet<>();
		larry.setProjects(larryProjects);
		larryProjects.add(bridge);

		Workstation dredge = create(Workstation.class);
		dredge.setName("Blue Dredge");
		dredge.setAssessment(createEnum(Assessment.class, "excellent"));
		dredge.setCompany(acme);
		dredge.setEmployee(larry);
		larry.setWorkstation(dredge);
		
		Workstation lorry = create(Workstation.class);
		lorry.setName("Red Lorry");
		lorry.setAssessment(createEnum(Assessment.class, "poor"));
		lorry.setCompany(acme);
		lorry.setEmployee(curly);
		curly.setWorkstation(lorry);

		List<Workstation> acmeWorkstations = new ArrayList<>();
		acmeWorkstations.add(dredge);
 		acmeWorkstations.add(lorry);
 		acme.setWorkstations(acmeWorkstations);
 		
		return acme;
	}
    
    @SuppressWarnings("unchecked")
	private <T> T createEnum(Class<?> type, String literal) {
    	return (T) packageManager.createEnum(type, literal);
	}

	@SuppressWarnings("unchecked")
	private <T> T create(Class<?> type) {
		return (T) packageManager.create(type);
    }

}
