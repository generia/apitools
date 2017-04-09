package de.generia.tools.model.uml.profilebuilder;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.resource.UMLResource;

public class UmlProfileBuilder extends Uml2Util {

	protected static Profile createProfile(String name) {
		Profile profile = UMLFactory.eINSTANCE.createProfile();
		profile.setName(name);

		out("Profile '" + profile.getQualifiedName() + "' created.");

		return profile;
	}

	protected static Type importType(org.eclipse.uml2.uml.Package pPackage, String pName) {

		Type lType = (Type) pPackage.getImportedMember(pName);
		if (lType == null) {
			Model umlLibrary = (Model) load(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI));
			lType = umlLibrary.getOwnedType(pName);
			if (lType == null) {
				Model umlModel = (Model) load(URI.createURI(UMLResource.UML_METAMODEL_URI));
				lType = umlModel.getOwnedType(pName);
				if (lType == null) {
					throw new RuntimeException("can't import primitive type '" + pName + "', only primitive types of library '" + UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI + "' are supported.");
				}
			}
			pPackage.createElementImport(lType);
			out("Type '" + lType.getQualifiedName() + "' imported.");
		}

		return lType;
	}

	protected static Stereotype createStereotype(Profile profile, String name, boolean isAbstract) {
		Stereotype stereotype = profile.getOwnedStereotype(name);
		if (stereotype == null) {
			stereotype = profile.createOwnedStereotype(name, isAbstract);
			out("Stereotype '" + stereotype.getQualifiedName() + "' created.");
		}
		return stereotype;
	}

	protected static org.eclipse.uml2.uml.Class referenceMetaclass(Model pUmlMetaModel, Profile pProfile, String name) {
		org.eclipse.uml2.uml.Class metaclass = (org.eclipse.uml2.uml.Class) pUmlMetaModel.getOwnedType(name);
		if (metaclass == null) {
			throw new IllegalArgumentException("can't find metaclass '" + name + "'");
		}
		for (ElementImport lImport : pProfile.getMetaclassReferences()) {
			String lQualifiedName = lImport.getImportedElement().getQualifiedName();
			if (lQualifiedName.equals(metaclass.getQualifiedName())) {
				return metaclass;
			}
		}
		pProfile.createMetaclassReference(metaclass);
		out("Metaclass '" + metaclass.getQualifiedName() + "' referenced.");

		return metaclass;
	}

	protected static Extension createExtension(org.eclipse.uml2.uml.Class metaclass, Stereotype stereotype, boolean required) {
		String lExtensionName = metaclass.getName() + "_" + stereotype.getName();
		
		for (Type lType : stereotype.getProfile().getOwnedTypes()) {
			if (lType instanceof Extension && lType.getName().equals(lExtensionName)) {
				return (Extension) lType;
			}
		}
		Extension extension = stereotype.createExtension(metaclass, required);
		out((required ? "Required extension '" : "Extension '") + extension.getQualifiedName() + "' created.");
		return extension;
	}

	protected static void defineProfile(Profile profile) {
		profile.define();

		out("Profile '" + profile.getQualifiedName() + "' defined.");
	}

	protected static void applyProfile(org.eclipse.uml2.uml.Package package_, Profile profile) {
		package_.applyProfile(profile);

		out("Profile '" + profile.getQualifiedName() + "' applied to package '" + package_.getQualifiedName() + "'.");
	}

	protected static void applyStereotype(NamedElement namedElement, Stereotype stereotype) {
		namedElement.applyStereotype(stereotype);

		out("Stereotype '" + stereotype.getQualifiedName() + "' applied to element '" + namedElement.getQualifiedName()
				+ "'.");
	}

	protected static Object getStereotypePropertyValue(NamedElement namedElement, Stereotype stereotype,
			Property property) {
		Object value = namedElement.getValue(stereotype, property.getName());

		out("Value of stereotype property '" + property.getQualifiedName() + "' on element '"
				+ namedElement.getQualifiedName() + "' is " + String.valueOf(value) + ".");

		return value;
	}

	protected static void setStereotypePropertyValue(NamedElement namedElement, Stereotype stereotype,
			Property property, Object value) {
		namedElement.setValue(stereotype, property.getName(), value);

		out("Value of stereotype property '" + property.getQualifiedName() + "' on element '"
				+ namedElement.getQualifiedName() + "' set to " + String.valueOf(value) + ".");
	}
}