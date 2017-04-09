package de.generia.tools.model.api.resource.stream;


import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.resource.NamingConvention;


public abstract class AbstractModelStream {

	protected EPackage mRoot = null;
	protected EModelElement mContext = null;

	protected NamingConvention mNamingConventionHandler = null;


	public AbstractModelStream(NamingConvention namingConvention) {
		this.mNamingConventionHandler = namingConvention;
	}
}
