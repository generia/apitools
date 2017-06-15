package de.generia.tools.model.api.generator.java.base;

import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EParameter;
import de.generia.tools.model.api.generator.trafo.TrafoGenerator;

public class JavaOperation extends AbstractJavaComponent {
		protected EOperation mOperation;
	

	@Override
	public EModelElement getModelNode() {
		return mOperation;
	}

	public JavaOperation(TrafoGenerator pGenerator, JavaClass pContext, EOperation pOperation) {
		super(pGenerator, pContext);
		mOperation = pOperation;
		setIndent(true);
	}
	
	public String getName() {
		return mOperation.getName();
	}
	
	public String getType() {
		return getFeatureType(mOperation, "void");
	}
	
	public String getSignature() {
		String lSignature = "";
		String lSep = "";
		for (EParameter lParameter : mOperation.getParameters()) {
			String lType = getFeatureType(lParameter, null);
			String lName = lParameter.getName();
			lSignature += lSep + lType + " " + lName;
			lSep = ", ";
		}
		return lSignature;
	}
}
