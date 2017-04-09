package de.generia.tools.model.uml.profilebuilder;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;

public class UmlModelBuilder extends Uml2Util {

	protected static Model createModel(String name) {
		Model model = UMLFactory.eINSTANCE.createModel();
		model.setName(name);

		out("Model '" + model.getQualifiedName() + "' created.");

		return model;
	}

	protected static org.eclipse.uml2.uml.Package createPackage(org.eclipse.uml2.uml.Package nestingPackage, String name) {
		org.eclipse.uml2.uml.Package package_ = nestingPackage.getNestedPackage(name);
		if (package_ == null) {
			package_ = nestingPackage.createNestedPackage(name);
			out("Package '" + package_.getQualifiedName() + "' created.");
		}
		return package_;
	}

	protected static PrimitiveType createPrimitiveType(org.eclipse.uml2.uml.Package package_, String name) {
		Type lType = package_.getOwnedType(name);
		if (lType == null) {
			lType = package_.createOwnedPrimitiveType(name);
			out("Primitive type '" + lType.getQualifiedName() + "' created.");
		}
		if (lType instanceof PrimitiveType) {
			return (PrimitiveType) lType;
		}
		throw new IllegalStateException("existing type '" + lType.getQualifiedName() + "' is expected to be an primitive type");
	}

	protected static Enumeration createEnumeration(org.eclipse.uml2.uml.Package package_, String name) {
		Type lType = package_.getOwnedType(name);
		if (lType == null) {
			lType = package_.createOwnedEnumeration(name);
			out("Enumeration '" + lType.getQualifiedName() + "' created.");
		}
		if (lType instanceof Enumeration) {
			return (Enumeration) lType;
		}
		throw new IllegalStateException("existing type '" + lType.getQualifiedName() + "' is expected to be an enumeration");
	}

	protected static EnumerationLiteral createEnumerationLiteral(Enumeration enumeration, String name) {
		EnumerationLiteral enumerationLiteral = enumeration.getOwnedLiteral(name);
		if (enumerationLiteral == null) {
			enumerationLiteral = enumeration.createOwnedLiteral(name);
			out("Enumeration literal '" + enumerationLiteral.getQualifiedName() + "' created.");
		}
		return enumerationLiteral;
	}

	protected static org.eclipse.uml2.uml.Class createClass(org.eclipse.uml2.uml.Package package_, String name, boolean isAbstract) {
		Type lType = package_.getOwnedType(name);
		if (lType == null) {
			lType = package_.createOwnedClass(name, isAbstract);
			out("Class '" + lType.getQualifiedName() + "' created.");
		}
		if (lType instanceof org.eclipse.uml2.uml.Class) {
			return (org.eclipse.uml2.uml.Class) lType;
		}
		throw new IllegalStateException("existing type '" + lType.getQualifiedName() + "' is expected to be a class");
	}

	protected static Generalization createGeneralization(Classifier specificClassifier, Classifier generalClassifier) {
		Generalization generalization = specificClassifier.getGeneralization(generalClassifier, true);

		out("Generalization " + specificClassifier.getQualifiedName() + " ->> " + generalClassifier.getQualifiedName()
				+ " created.");

		return generalization;
	}

	protected static Property createAttribute(org.eclipse.uml2.uml.Class class_, String name, Type type, int lowerBound, int upperBound) {
		Property attribute = class_.getOwnedAttribute(name, type); 
		if (attribute == null) {
			attribute = class_.createOwnedAttribute(name, type, lowerBound, upperBound);
			if (type.getName().equals("Integer")) {
				attribute.setIntegerDefaultValue(-1);
			} else {
				attribute.setNullDefaultValue();
			}
			
			StringBuffer sb = new StringBuffer();
	
			sb.append("Attribute '");
	
			sb.append(attribute.getQualifiedName());
	
			sb.append("' : ");
	
			sb.append(type.getQualifiedName());
	
			sb.append(" [");
			sb.append(lowerBound);
			sb.append("..");
			sb.append(LiteralUnlimitedNatural.UNLIMITED == upperBound ? "*" : String.valueOf(upperBound));
			sb.append("]");
	
			sb.append(" created.");

			out(sb.toString());
		}
		return attribute;
	}

	protected static Association createAssociation(Type type1, boolean end1IsNavigable,
			AggregationKind end1Aggregation, String end1Name, int end1LowerBound, int end1UpperBound, Type type2,
			boolean end2IsNavigable, AggregationKind end2Aggregation, String end2Name, int end2LowerBound,
			int end2UpperBound) {

		Association association = type1.createAssociation(end1IsNavigable, end1Aggregation, end1Name, end1LowerBound,
				end1UpperBound, type2, end2IsNavigable, end2Aggregation, end2Name, end2LowerBound, end2UpperBound);

		StringBuffer sb = new StringBuffer();

		sb.append("Association ");

		if (null == end1Name || 0 == end1Name.length()) {
			sb.append('{');
			sb.append(type1.getQualifiedName());
			sb.append('}');
		} else {
			sb.append("'");
			sb.append(type1.getQualifiedName());
			sb.append(NamedElement.SEPARATOR);
			sb.append(end1Name);
			sb.append("'");
		}

		sb.append(" [");
		sb.append(end1LowerBound);
		sb.append("..");
		sb.append(LiteralUnlimitedNatural.UNLIMITED == end1UpperBound ? "*" : String.valueOf(end1UpperBound));
		sb.append("] ");

		sb.append(end2IsNavigable ? '<' : '-');
		sb.append('-');
		sb.append(end1IsNavigable ? '>' : '-');
		sb.append(' ');

		if (null == end2Name || 0 == end2Name.length()) {
			sb.append('{');
			sb.append(type2.getQualifiedName());
			sb.append('}');
		} else {
			sb.append("'");
			sb.append(type2.getQualifiedName());
			sb.append(NamedElement.SEPARATOR);
			sb.append(end2Name);
			sb.append("'");
		}

		sb.append(" [");
		sb.append(end2LowerBound);
		sb.append("..");
		sb.append(LiteralUnlimitedNatural.UNLIMITED == end2UpperBound ? "*" : String.valueOf(end2UpperBound));
		sb.append("]");

		sb.append(" created.");

		out(sb.toString());

		return association;
	}
}