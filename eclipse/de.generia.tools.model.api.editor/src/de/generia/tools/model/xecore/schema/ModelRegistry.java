package de.generia.tools.model.xecore.schema;

import java.util.HashMap;
import java.util.Map;

import de.generia.tools.model.api.resource.ApiSchema;


public class ModelRegistry {
	private static ModelRegistry mInstance = null;

	private Map<String, ModelSchema> mModelSchemaMap = new HashMap<String, ModelSchema>();

	public static ModelSchema getModelSchema(String pSchema) {
		if (mInstance == null)  {
			mInstance = new ModelRegistry();

			ModelSchema lApiSchema = new ApiSchema();
			mInstance.mModelSchemaMap.put(lApiSchema.getName(), lApiSchema);
		}
		return mInstance.mModelSchemaMap.get(pSchema);
	}
}
