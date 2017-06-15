package de.generia.tools.model.api.generator.java.pojo;

import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.generator.java.base.JavaOperation;
import de.generia.tools.model.api.generator.trafo.TrafoGenerator;

public class PojoOperation extends JavaOperation {
	public PojoOperation(TrafoGenerator pGenerator, PojoClass pContext, EOperation pOperation) {
		super(pGenerator, pContext, pOperation);
	}
}
