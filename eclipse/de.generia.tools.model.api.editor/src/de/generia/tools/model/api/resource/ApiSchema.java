package de.generia.tools.model.api.resource;

import de.generia.tools.model.api.ApiFactory;
import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.xecore.schema.ModelSchema;

public class ApiSchema extends ModelSchema {

	private static final String APISCHEMA = "api";

	public ApiSchema() {
		super(APISCHEMA);
		register(ApiPackage.eINSTANCE, ApiFactory.eINSTANCE);
	}
}
