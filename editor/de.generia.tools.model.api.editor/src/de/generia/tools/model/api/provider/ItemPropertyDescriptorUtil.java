package de.generia.tools.model.api.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;

import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.api.EAttribute;
import de.generia.tools.model.api.EClass;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EDataType;
import de.generia.tools.model.api.EReference;
import de.generia.tools.model.api.ETypedElement;
import de.generia.tools.model.api.util.ApiModelUtil;

public class ItemPropertyDescriptorUtil {

	public static String getText(ItemPropertyDescriptor pItemPropertyDescriptor, Object pFeature, IItemLabelProvider pLabelProvider, Object pObject) {
		if (pFeature.equals(ApiPackage.eINSTANCE.getEReference_Opposite())) {
			EReference lReference = (EReference)pObject;
			String lText = lReference.getContainingClass().getName() + "." + lReference.getName();
			return lText + " - " + ApiModelUtil.getFullName(lReference.getContainingClass());
		}
		if (pFeature.equals(ApiPackage.eINSTANCE.getETypedElement_Type())
		 || pFeature.equals(ApiPackage.eINSTANCE.getETypedElement_KeyType())
		 || pFeature.equals(ApiPackage.eINSTANCE.getEClass_SuperType())) {
			EClassifier lClassifier = (EClassifier) pObject;
			return lClassifier.getName() + " - " + ApiModelUtil.getFullName(lClassifier);
		}
		return pLabelProvider.getText(pObject);
	}

	public static Object getImage(ItemPropertyDescriptor pItemPropertyDescriptor, Object pFeature, IItemLabelProvider pLabelProvider, Object pObject) {
		return pLabelProvider.getImage(pObject);
	}

	public static Collection<?> getComboBoxObjects(ItemPropertyDescriptor pItemPropertyDescriptor, Object pFeature, Collection<?> pComboBoxObjects, Object pObject) {
		if (pFeature.equals(ApiPackage.eINSTANCE.getETypedElement_Type())) {
			ETypedElement lTypedElement = (ETypedElement)pObject;
			if (lTypedElement instanceof EAttribute) {
				return filterObjects(pComboBoxObjects, EDataType.class);
			}
			if (lTypedElement instanceof EReference) {
				return filterObjects(pComboBoxObjects, EClass.class);
			}
		}
		return pComboBoxObjects;
	}

	private static Collection<?> filterObjects(Collection<?> pObjects, Class<?> pClass) {
		Collection<Object> lObjects = new ArrayList<Object>();
		for (Object lObject : pObjects) {
			if (pClass.isInstance(lObject)) {
				lObjects.add(lObject);
			}
		}
		return lObjects;
	}
}
