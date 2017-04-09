package de.generia.tools.model.xecore.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import de.generia.tools.model.xecore.binding.ModelResourceImpl;

public class ModelNodeImpl extends EObjectImpl implements ModelNode {

	@Override
	public String getName() {
		EStructuralFeature lNameFeature = eClass().getEStructuralFeature("name");
		if (lNameFeature == null) {
			return null;
		}
		return (String) eGet(lNameFeature);
	}

	@Override
	public void setName(String pName) {
		EStructuralFeature lNameFeature = eClass().getEStructuralFeature("name");
		if (lNameFeature == null) {
			return;
		}
		eSet(lNameFeature, pName);
	}

	private boolean isModelResource() {
		return (eResource() instanceof ModelResourceImpl);
	}

	@Override
	public EObject eResolveProxy(InternalEObject pProxy) {
		EObject lProxy = super.eResolveProxy(pProxy);
		return lProxy;
	}

	@Override
	public String eURIFragmentSegment(EStructuralFeature pStructuralFeature, EObject pObject) {
		if (!isModelResource()) {
			return super.eURIFragmentSegment(pStructuralFeature, pObject);
		}
		if (pObject instanceof ModelNode) {
			return ((ModelNode)pObject).getName();
		}
		return null;
	}

	@Override
	public EObject eObjectForURIFragmentSegment(String pSegment) {
		if (!isModelResource()) {
			return super.eObjectForURIFragmentSegment(pSegment);
		}
		for (EObject lObject : eContents()) {
			String lFragmentSegment = eURIFragmentSegment(null, lObject);
			if (lFragmentSegment != null && lFragmentSegment.equals(pSegment)) {
				return lObject;
			}
		}
		return null;
	}
}
