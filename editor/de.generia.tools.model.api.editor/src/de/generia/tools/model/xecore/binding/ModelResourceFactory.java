package de.generia.tools.model.xecore.binding;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

import de.generia.tools.model.api.resource.ApiResourceBinding;
import de.generia.tools.model.api.resource.ApiSchema;
import de.generia.tools.model.xecore.binding.ModelResourceBinding.BindingInfo;


public class ModelResourceFactory extends ResourceFactoryImpl {

	protected BindingInfo mBindingInfo;

	public ModelResourceFactory() {
		mBindingInfo = new ApiResourceBinding(new ApiSchema()).createBindingInfo();
	}

	public ModelResourceFactory(BindingInfo pMappingInfo) {
		mBindingInfo = pMappingInfo;
	}

	@Override
	public Resource createResource(URI pUri) {
		return new ModelResourceImpl(pUri, mBindingInfo);
	}
}
