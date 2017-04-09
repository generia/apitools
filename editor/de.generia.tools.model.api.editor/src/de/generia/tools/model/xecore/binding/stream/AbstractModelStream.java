package de.generia.tools.model.xecore.binding.stream;


import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import de.generia.tools.model.xecore.binding.NamingConvention;
import de.generia.tools.model.xecore.binding.ModelResourceBinding.BindingInfo;
import de.generia.tools.model.xecore.schema.ModelSchema;


public abstract class AbstractModelStream {

	protected static final String MIXED_CONTENT_ANNOTATION = "mixedContent";
	protected static final String TEXT_NODE_CLASS = "textNodeClass";
	protected static final String TEXT_FEATURE = "textFeature";
	protected static final String CHILDREN_FEATURE = "childrenFeature";

	protected EObject mRoot = null;
	protected EObject mContext = null;

	protected BindingInfo mBindingInfo = null;
	protected ModelSchema mModelSchema;

	protected NamingConvention mNamingConventionHandler = null;
	protected EClass mTextContentClass = null;
	protected EStructuralFeature mTextFeature = null;
	protected EStructuralFeature mChildrenFeature = null;


	public AbstractModelStream(BindingInfo pBindingInfo) {
		mBindingInfo = pBindingInfo;
		mModelSchema = pBindingInfo.getModelResourceBinding().getModelSchema();
		mNamingConventionHandler = pBindingInfo.getModelResourceBinding().getNamingConvention();
	}

	protected void checkAndEnterMixedContentMode(EReference pReference) {
		EClassifier lType = pReference.getEType();
		if (!(lType instanceof EClass)) {
			return;
		}

		EAnnotation lAnnotation = lType.getEAnnotation(MIXED_CONTENT_ANNOTATION);
		if (lAnnotation == null) {
			return;
		}
		EClass lTextContentClass = null;
		String lTextNode = lAnnotation.getDetails().get(TEXT_NODE_CLASS);
		EClassifier lClassifier = lType.getEPackage().getEClassifier(lTextNode);
		if (lClassifier instanceof EClass) {
			lTextContentClass = (EClass)lClassifier;
		} else {
			throw new RuntimeException("text content class has wrong type, expected '" + EClass.class.getName() + "' but got '" + lClassifier.getClass().getName() + "'");
		}
		mTextContentClass = lTextContentClass;

		String lTextFeatureName = lAnnotation.getDetails().get(TEXT_FEATURE);
		mTextFeature = mTextContentClass.getEStructuralFeature(lTextFeatureName);
		if (mTextFeature == null) {
			throw new RuntimeException("can't get text feature '" + TEXT_FEATURE + "' from text content class '" + mTextContentClass + "'");
		}

		String lChildrenFeatureName = lAnnotation.getDetails().get(CHILDREN_FEATURE);
		mChildrenFeature = mTextContentClass.getEStructuralFeature(lChildrenFeatureName);
		if (mChildrenFeature == null) {
			throw new RuntimeException("can't get children feature '" + CHILDREN_FEATURE + "' from text content class '" + mTextContentClass + "'");
		}
	}

	protected void checkAndLeaveMixedContentMode(EClass pClass) {
		EAnnotation lAnnotation = pClass.getEAnnotation(MIXED_CONTENT_ANNOTATION);
		if (lAnnotation != null) {
			mTextContentClass = null;
			mTextFeature = null;
			mChildrenFeature = null;
		}
	}

	protected boolean isMixedContentMode() {
		return mTextContentClass != null;
	}
}
