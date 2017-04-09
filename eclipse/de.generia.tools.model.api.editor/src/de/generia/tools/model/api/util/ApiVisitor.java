package de.generia.tools.model.api.util;

import org.eclipse.emf.ecore.EObject;

import de.generia.tools.model.api.EAnnotation;
import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EDataType;
import de.generia.tools.model.api.EEnum;
import de.generia.tools.model.api.EEnumLiteral;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.ENamedElement;
import de.generia.tools.model.api.EOperation;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.EParameter;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.ETypedElement;

public interface ApiVisitor {
	public void visitEAnnotation(EAnnotation eAnnotation);
	public void visitEDataType(EDataType eDataType);
	public void visitEEnum(EEnum eEnum);
	public void visitEEnumLiteral(EEnumLiteral eEnumLiteral);
	public void visitEClass(EClass eClass);
	public void visitEAttribute(EAttribute eAttribute);
	public void visitEReference(EReference eReference);
	public void visitEOperation(EOperation eOperation);
	public void visitEParameter(EParameter eParameter);
	public void visitEPackage(EPackage ePackage);

	public class Acceptor {
		private ThreadLocal<ApiVisitor> visitor = new ThreadLocal<ApiVisitor>();

		protected ApiSwitch<Boolean> acceptorSwitch = new ApiSwitch<Boolean>() {
			@Override
			public Boolean caseEAnnotation(EAnnotation object) {
				visitor.get().visitEAnnotation(object);
				return true;
			};

			@Override
			public Boolean caseEAttribute(EAttribute object) {
				visitor.get().visitEAttribute(object);
				return true;
			}

			@Override
			public Boolean caseEClass(EClass object) {
				visitor.get().visitEClass(object);
				return true;
			}

			@Override
			public Boolean caseEClassifier(EClassifier object) {
				throw new UnsupportedOperationException();
			};

			@Override
			public Boolean caseEDataType(EDataType object) {
				visitor.get().visitEDataType(object);
				return true;
			}

			@Override
			public Boolean caseEEnum(EEnum object) {
				visitor.get().visitEEnum(object);
				return true;
			}

			@Override
			public Boolean caseEEnumLiteral(EEnumLiteral object) {
				visitor.get().visitEEnumLiteral(object);
				return true;
			}

			@Override
			public Boolean caseEModelElement(EModelElement object) {
				throw new UnsupportedOperationException();
			}

			@Override
			public Boolean caseENamedElement(ENamedElement object) {
				throw new UnsupportedOperationException();
			}

			@Override
			public Boolean caseEOperation(EOperation object) {
				visitor.get().visitEOperation(object);
				return true;
			}

			@Override
			public Boolean caseEPackage(EPackage object) {
				visitor.get().visitEPackage(object);
				return true;
			}

			@Override
			public Boolean caseEParameter(EParameter object) {
				visitor.get().visitEParameter(object);
				return true;
			}

			@Override
			public Boolean caseEReference(EReference object) {
				visitor.get().visitEReference(object);
				return true;
			}

			@Override
			public Boolean caseEStructuralFeature(EStructuralFeature object) {
				throw new UnsupportedOperationException();
			}

			@Override
			public Boolean caseETypedElement(ETypedElement object) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public Boolean defaultCase(EObject object) {
				return false;
			}
		};

		public boolean accept(ApiVisitor visitor, EObject object) {
			try {
				this.visitor.set(visitor);
				return acceptorSwitch.doSwitch(object);
			} finally {
				this.visitor.remove();
			}
		}
	}
}
