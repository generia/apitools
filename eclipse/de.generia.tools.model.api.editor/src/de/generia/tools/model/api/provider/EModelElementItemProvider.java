/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import de.generia.tools.model.api.ApiFactory;
import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.util.ApiPlugin;

/**
 * This is the item provider adapter for a {@link de.generia.tools.model.api.EModelElement} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class EModelElementItemProvider
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EModelElementItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addDocumentationPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Documentation feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDocumentationPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_EModelElement_documentation_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_EModelElement_documentation_feature", "_UI_EModelElement_type"),
				 ApiPackage.Literals.EMODEL_ELEMENT__DOCUMENTATION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(ApiPackage.Literals.EMODEL_ELEMENT__ANNOTATIONS);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((EModelElement)object).getDocumentation();
		return label == null || label.length() == 0 ?
			getString("_UI_EModelElement_type") :
			getString("_UI_EModelElement_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(EModelElement.class)) {
			case ApiPackage.EMODEL_ELEMENT__DOCUMENTATION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case ApiPackage.EMODEL_ELEMENT__ANNOTATIONS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(ApiPackage.Literals.EMODEL_ELEMENT__ANNOTATIONS,
				 ApiFactory.eINSTANCE.createEAnnotation()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ApiPlugin.INSTANCE;
	}

	@Override
	protected ItemPropertyDescriptor createItemPropertyDescriptor(AdapterFactory pAdapterFactory,
			ResourceLocator pResourceLocator, String pDisplayName, String pDescription, EStructuralFeature pFeature,
			boolean pIsSettable, boolean pMultiLine, boolean pSortChoices, Object pStaticImage, String pCategory,
			String[] pFilterFlags) {
		return new LabelItemPropertyDescriptor(pAdapterFactory, pResourceLocator, pDisplayName, pDescription, pFeature,
				pIsSettable, pMultiLine, pSortChoices, pStaticImage, pCategory, pFilterFlags);
	}

	private static class LabelItemPropertyDescriptor extends ItemPropertyDescriptor {
		public LabelItemPropertyDescriptor(AdapterFactory adapterFactory,
			      ResourceLocator resourceLocator,
			      String displayName,
			      String description,
			      EStructuralFeature feature, 
			      boolean isSettable,
			      boolean multiLine,
			      boolean sortChoices,
			      Object staticImage,
			      String category,
			      String [] filterFlags) {
			super(adapterFactory, resourceLocator, displayName, description, feature, isSettable, multiLine, sortChoices, staticImage, category, filterFlags);
		}

		@Override
		public IItemLabelProvider getLabelProvider(Object pObject) {
//			return super.getLabelProvider(pObject);
//			System.out.println("getLabelProvider: opposite='" + ((EReference)pObject).getOpposite() + "' object='" + pObject + "', label-provider='" + lLabelProvider + "'");
			return new IItemLabelProvider() {
				@Override
				public Object getImage(Object pObject) {
					if (pObject == null) {
						return LabelItemPropertyDescriptor.super.getLabelProvider(pObject).getImage(pObject);
					}
					return ItemPropertyDescriptorUtil.getImage(LabelItemPropertyDescriptor.this, LabelItemPropertyDescriptor.this.getFeature(pObject), LabelItemPropertyDescriptor.super.getLabelProvider(pObject), pObject);
				}

				@Override
				public String getText(Object pObject) {
					if (pObject == null) {
						return LabelItemPropertyDescriptor.super.getLabelProvider(pObject).getText(pObject);
					}
					return ItemPropertyDescriptorUtil.getText(LabelItemPropertyDescriptor.this, LabelItemPropertyDescriptor.this.getFeature(pObject), LabelItemPropertyDescriptor.super.getLabelProvider(pObject), pObject);
				}
			};
		}
		
		@Override
		protected Collection<?> getComboBoxObjects(Object pObject) {
			return ItemPropertyDescriptorUtil.getComboBoxObjects(LabelItemPropertyDescriptor.this, LabelItemPropertyDescriptor.this.getFeature(pObject), super.getComboBoxObjects(pObject), pObject);
		}
	}
}
