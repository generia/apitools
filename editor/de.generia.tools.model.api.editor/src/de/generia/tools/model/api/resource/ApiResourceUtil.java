package de.generia.tools.model.api.resource;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.xecore.binding.ModelResourceFactory;

public class ApiResourceUtil {

	
	public static final String API_XMI_FILE_EXTENSION = "api-xmi";
	public static final String API_FILE_EXTENSION = "api";
	public static final String API_MODEL_PATHMAP = "pathmap://API_MODEL/";

	public static ResourceSet createResourceSet() {
		ResourceSet lResourceSet = new ResourceSetImpl();
		initStandalone(lResourceSet);
		return lResourceSet;
	}
	

	public static void initStandalone(ResourceSet pResourceSet) {
		//pResourceSet.getPackageRegistry().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
		pResourceSet.getPackageRegistry().put(ApiPackage.eNS_PREFIX, ApiPackage.eINSTANCE);

		Map<URI,URI> lUriMap = pResourceSet.getURIConverter().getURIMap();

		/*
		URI lUmlUri = createClasspathUri("model/UML.ecore");
		lUriMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), lUmlUri.appendSegment("libraries").appendSegment(""));
		lUriMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), lUmlUri.appendSegment("metamodels").appendSegment(""));
		lUriMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP), lUmlUri.appendSegment("profiles").appendSegment(""));
		*/
		URI lApiUri = createClasspathUri("model/api.ecore");
		lUriMap.put(URI.createURI(API_MODEL_PATHMAP), lApiUri.appendSegment("model").appendSegment(""));
		
		//pResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
		pResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(API_FILE_EXTENSION, new ModelResourceFactory());
		pResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(API_XMI_FILE_EXTENSION, new XMIResourceFactoryImpl());
	}


	public static URI createClasspathUri(String pResource) {
		URL lUrl = ApiResourceUtil.class.getClassLoader().getResource(pResource);
		if (lUrl == null) {
			throw new RuntimeException("can't find resource '" + pResource + "' in classpath");
		}
		URI lUri = URI.createURI(lUrl.toExternalForm().substring(0, lUrl.toExternalForm().length() - pResource.length()));
		return lUri;
	}

	
	public static void save(ResourceSet pResourceSet, String pExtension) {
		for (Resource lResource : pResourceSet.getResources()) {
			if (pExtension == null || lResource.getURI().fileExtension().equals(pExtension)) {
				try {
					lResource.save(null);
				} catch (IOException e) {
					throw new RuntimeException("can't save resource '" + lResource.getURI() +  "'", e);
				}
			}
		}
	}
}
