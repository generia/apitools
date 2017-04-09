package de.generia.tools.model.uml.profilebuilder;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;

public abstract class Uml2Util {

	public static boolean DEBUG = Activator.getDefault().isDebugging();

	protected static void out(String output) {
		if (DEBUG) {
			System.out.println(output);
		}
	}

	protected static void err(String error) {
		System.err.println(error);
	}

	protected static void registerResourceFactories() {
		if (Activator.isStandalone()) {
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());
		}
	}

	protected static void registerPathmaps(URI uri) {
		if (Activator.isStandalone()) {
			URIConverter.URI_MAP.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), uri.appendSegment("libraries")
					.appendSegment(""));
	
			URIConverter.URI_MAP.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), uri.appendSegment("metamodels")
					.appendSegment(""));
	
			URIConverter.URI_MAP.put(URI.createURI(UMLResource.PROFILES_PATHMAP), uri.appendSegment("profiles")
					.appendSegment(""));
		}
	}

	/* needed for standalone mode */
	protected static void registerPackages(ResourceSet resourceSet) {
		if (Activator.isStandalone()) {
			Registry packageRegistry = resourceSet.getPackageRegistry();
			packageRegistry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
			packageRegistry.put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
		}
	}

	protected static ResourceSet createResourceSet() {
		return new ResourceSetImpl();
	}
	
	protected static void save(org.eclipse.uml2.uml.Package pPackage, URI uri) {
		Resource resource = createResourceSet().createResource(uri);
		EList<EObject> contents = resource.getContents();

		contents.add(pPackage);

		for (Iterator<EObject> allContents = UMLUtil.getAllContents(pPackage, true, false); allContents.hasNext();) {

			EObject eObject = (EObject) allContents.next();
			if (eObject instanceof Element) {
				contents.addAll(((Element) eObject).getStereotypeApplications());
			}
		}

		try {
			resource.save(null);
			out("Done.");
		} catch (IOException e) {
			err(e.getMessage());
			throw new RuntimeException("caught exception while saving resource '" + uri + "'", e);
		}
	}

	protected static org.eclipse.uml2.uml.Package load(URI uri) {
		org.eclipse.uml2.uml.Package package_ = null;

		try {
			Resource resource = createResourceSet().getResource(uri, true);
			package_ = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(resource.getContents(),
					UMLPackage.Literals.PACKAGE);
		} catch (WrappedException e) {
			err(e.getMessage());
			throw new RuntimeException("caught exception while loading resource '" + uri + "'", e);
		}
		return package_;
	}

}