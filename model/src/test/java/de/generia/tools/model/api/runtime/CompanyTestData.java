package de.generia.tools.model.api.runtime;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EEnumLiteral;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.resource.stream.ModelInputStream;

public class CompanyTestData {
	private EPackageManager packageManager;

	public CompanyTestData(EObjectFactory objectFactory) {
		ModelInputStream apiStream = new ModelInputStream();
		InputStream inputStream = CompanyTestData.class.getResourceAsStream("companymgmt.api");
		EPackage api = apiStream.read(inputStream);
		assertNotNull(api);
		
		packageManager = new EPackageManager("companymgmt:v1", api, objectFactory);

	}
	
	public EPackageManager getPackageManager() {
		return packageManager;
	}
	
	public EObject createAcmeCompany() {
		EObject acme = create("/companymgmt/model/Company");
		acme.set("name", "Acme Inc.");
		acme.set("homepage", "www.acme.com");
		Set<EObject> companyEmployees = new LinkedHashSet<>();
		acme.set("employees", companyEmployees);
		Set<EObject> companyProjects = new LinkedHashSet<>();
		acme.set("projects", companyProjects);

		EObject curly = create("/companymgmt/model/Employee");
		curly.set("name", "Curly Howard");
		curly.set("logins", "choward");
		curly.set("company", acme);
		companyEmployees.add(curly);

		EObject moe = create("/companymgmt/model/Employee");
		moe.set("name", "Moe Howard");
		moe.set("logins", "mhoward");
		moe.set("company", acme);
		companyEmployees.add(moe);

		EObject larry = create("/companymgmt/model/Manager");
		larry.set("name", "Larry Fine");
		larry.set("logins", "lfine");
		larry.set("staffCar", true);
		larry.set("company", acme);
		companyEmployees.add(larry);

		EObject larryContact = create("/companymgmt/model/Contact");
		larryContact.set("email", "larry.fine@acme.com");
		larryContact.set("phoneNumber", "555 1234567");
		larry.set("contact", larryContact);

		EObject bridge = create("/companymgmt/model/Project");
		bridge.set("name", "Big Bridge");
		bridge.set("description", "Build a big bridge.");
		bridge.set("company", acme);
		Set<EObject> projectEmployees = new LinkedHashSet<>();
		bridge.set("employees", projectEmployees);
		projectEmployees.add(curly);
		projectEmployees.add(larry);
		companyProjects.add(bridge);
		Set<EObject> curlyProjects = new LinkedHashSet<>();
		curly.set("projects", curlyProjects);
		curlyProjects.add(bridge);
		Set<EObject> larryProjects = new LinkedHashSet<>();
		larry.set("projects", larryProjects);
		larryProjects.add(bridge);

		EObject dredge = create("/companymgmt/model/Workstation");
		dredge.set("name", "Blue Dredge");
		dredge.set("assessment", createEnum("/companymgmt/model/Assessment", "excellent"));
		dredge.set("company", acme);
		dredge.set("employee", larry);
		larry.set("workstation", dredge);
		
		EObject lorry = create("/companymgmt/model/Workstation");
		lorry.set("name", "Red Lorry");
		lorry.set("assessment", createEnum("/companymgmt/model/Assessment", "poor"));
		lorry.set("company", acme);
		lorry.set("employee", curly);
		curly.set("workstation", lorry);

		List<EObject> acmeWorkstations = new ArrayList<>();
		acmeWorkstations.add(dredge);
 		acmeWorkstations.add(lorry);
 		acme.set("workstations", acmeWorkstations);
 		
		return acme;
	}
    
    @SuppressWarnings("unchecked")
	private <T> T createEnum(String type, String literal) {
		EEnum e = packageManager.lookupElement(type);
		for (EEnumLiteral l : e.getLiterals()) {
			if (l.getName().equals(literal)) {
				return (T) packageManager.getObjectFactory().createEnum(l);
			}
		}
		throw new IllegalArgumentException("can't create enum literal '" + literal + "' for enum '" + type + "'");
	}

	@SuppressWarnings("unchecked")
	private <T> T create(String name) {
		EClass c = packageManager.lookupElement(name);
		return (T) packageManager.getObjectFactory().createObject(c);
    }

}
