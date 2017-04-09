package de.generia.tools.model.api.runtime.io;

import java.io.IOException;

import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.runtime.EObjectFactory;

public interface EObjectReader {
	
	Object read(EModelElement type) throws IOException;
	
	public interface Context {
		String getTypeHintProperty();
		EClassifier getType(String typeHint);
		String getTypeHint(EClass clazz);
		EAttribute getIdAttribute(EClass type);
		EObjectFactory getObjectFactory();
	}
}
