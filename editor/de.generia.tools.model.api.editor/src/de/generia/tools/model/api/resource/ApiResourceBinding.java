package de.generia.tools.model.api.resource;

import org.eclipse.emf.ecore.resource.Resource;

import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.xecore.binding.LowerCaseNamingConvention;
import de.generia.tools.model.xecore.binding.ModelResourceBinding;
import de.generia.tools.model.xecore.binding.ModelResourceFactory;
import de.generia.tools.model.xecore.binding.stream.ContentType;
import de.generia.tools.model.xecore.schema.ModelSchema;


public class ApiResourceBinding extends ModelResourceBinding {
	public static final ContentType CONTENT_TYPE = ContentType.parseContentType("model/api+xml");
//	public static final ContentType XMI_CONTENT_TYPE = ContentType.parseContentType("model/api+xmi");

	public ApiResourceBinding(ModelSchema pModelSchema) {
		super(pModelSchema, new LowerCaseNamingConvention());

		BindingInfo lSiteBindingInfo = createBindingInfo();
		Resource.Factory lResourceFactory = new ModelResourceFactory(lSiteBindingInfo);
		addMapping("api", ApiPackage.eINSTANCE.getEPackage(), CONTENT_TYPE, lResourceFactory);
//
//		Resource.Factory lXmiResourceFactory = new XMIResourceFactoryImpl();
//		addMapping("api-xmi", ApiPackage.eINSTANCE.getEPackage(), XMI_CONTENT_TYPE, lXmiResourceFactory);
	}

	public BindingInfo createBindingInfo() {
		BindingInfo lBindingInfo = new BindingInfo(this);

//		MixedContentInfo lRichtextInfo = new MixedContentInfo();
//		lRichtextInfo.setTextContentClass(CommonPackage.eINSTANCE.getText());
//		lRichtextInfo.setTextFeature(CommonPackage.eINSTANCE.getText_Text());
//		lRichtextInfo.setChildrenFeature(CommonPackage.eINSTANCE.getAbstractRichtextNode_Children());
//		lBindingInfo.addMixedContentInfo(CommonPackage.eINSTANCE.getRichtext(), lRichtextInfo);

//		ExternalStoreInfo lMenuInfo = new ExternalStoreInfo();
//		lMenuInfo.addExternalStoreFeature(SitePackage.eINSTANCE.getMenu_Pages());
//		lBindingInfo.addExternalStoreInfo(SitePackage.eINSTANCE.getMenu(), lMenuInfo);

		return lBindingInfo;
	}
}
