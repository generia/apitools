package de.generia.tools.model.api.runtime.typed;

import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.runtime.EObjectFactory;
import de.generia.tools.model.api.runtime.EPackageManager;

public class TypedEPackageManager extends EPackageManager {

	private String modelPackageRoot;

	public TypedEPackageManager(String schemaId, EPackage pkg, String modelPackageRoot) {
		this(schemaId, pkg, new TypedEObjectFactory(modelPackageRoot), modelPackageRoot);
	}

	public TypedEPackageManager(String schemaId, EPackage pkg, EObjectFactory objectFactory, String modelPackageRoot) {
		super(schemaId, pkg, objectFactory);
		this.modelPackageRoot = modelPackageRoot;
	}

	public <T> T lookupElement(Class<?> type) {

		String name = type.getName();
		if (name.startsWith(modelPackageRoot)) {
			name = getPackage().getName() + "/" + name.substring(modelPackageRoot.length() + 1);
		}
		name = name.replace('$', '/');
		name = name.replace('.', '/');
		name = "/" + name;
		return super.lookupElement(name);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T create(Class<?> typeInterface) {
		EClass type = lookupElement(typeInterface);
		return (T) getObjectFactory().createObject(type);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T createEnum(Class<T> enumType, String name) {
		EEnum type = lookupElement(enumType);
		return (T) getObjectFactory().createEnum(type, name);
	}
}
