/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api.provider;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import de.generia.tools.model.api.ApiPackage;
import de.generia.tools.model.api.EClassifier;
import de.generia.tools.model.api.EStructuralFeature;
import de.generia.tools.model.api.ETypedElement;
import de.generia.tools.model.api.util.ApiPlugin;

/**
 * This is the item provider adapter for a {@link de.generia.tools.model.api.ETypedElement} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ETypedElementItemProvider
	extends ENamedElementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ETypedElementItemProvider(AdapterFactory adapterFactory) {
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

			addOrderedPropertyDescriptor(object);
			addUniquePropertyDescriptor(object);
			addManyPropertyDescriptor(object);
			addRequiredPropertyDescriptor(object);
			addTypePropertyDescriptor(object);
			addKeyTypePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Ordered feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOrderedPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ETypedElement_ordered_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ETypedElement_ordered_feature", "_UI_ETypedElement_type"),
				 ApiPackage.Literals.ETYPED_ELEMENT__ORDERED,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Unique feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUniquePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ETypedElement_unique_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ETypedElement_unique_feature", "_UI_ETypedElement_type"),
				 ApiPackage.Literals.ETYPED_ELEMENT__UNIQUE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Many feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addManyPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ETypedElement_many_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ETypedElement_many_feature", "_UI_ETypedElement_type"),
				 ApiPackage.Literals.ETYPED_ELEMENT__MANY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Required feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRequiredPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ETypedElement_required_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ETypedElement_required_feature", "_UI_ETypedElement_type"),
				 ApiPackage.Literals.ETYPED_ELEMENT__REQUIRED,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ETypedElement_type_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ETypedElement_type_feature", "_UI_ETypedElement_type"),
				 ApiPackage.Literals.ETYPED_ELEMENT__TYPE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Key Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addKeyTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ETypedElement_keyType_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ETypedElement_keyType_feature", "_UI_ETypedElement_type"),
				 ApiPackage.Literals.ETYPED_ELEMENT__KEY_TYPE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object pObject) {
		String lText = super.getText(pObject);
		ETypedElement lTypedElement = (ETypedElement) pObject;
		EClassifier lType = lTypedElement.getType();
		String lTypeText = (lType == null ? "" : lType.getName());
		lText += " : " + getMapType(lTypedElement, lTypeText);
		return lText;
	}

	public String getCollectionType(ETypedElement pTypedElement) {
		if (pTypedElement.isMany()) {
			return pTypedElement.isUnique() ? "Set" : "List";
		}
		return "";
	}
	
	public String getMapType(ETypedElement pTypedElement, String pTypeText) {
		String lTypeText = pTypeText;
		if (pTypedElement.getKeyType() != null) {
			lTypeText = "Map<" + pTypedElement.getKeyType().getName() + ", " + lTypeText + ">";
		}
		return lTypeText;
	}

	  public Object getComposedImage(Object object, Object imageToCompose)
	  {
	    ETypedElement eTypedElement = (ETypedElement)object;
	    Collection<Object> images = new ArrayList<Object>();
	    images.add(imageToCompose);
	    String imageName = "full/obj16/EOccurrence";

	    if (eTypedElement.isRequired()) {
	          imageName += "One";
	    } else {
	          imageName += "Zero";
	    }

	    if (eTypedElement.isMany()) {
	          imageName += "ToUnbounded";
	    } else {
	          imageName += "ToOne";
	    }
	  
	    if (!imageName.equals("full/obj16/EOccurrenceZeroToOne"))
	    {
	      images.add(ApiPlugin.INSTANCE.getImage(imageName));
	    }
	  
	    final int offset =
	      !imageName.endsWith("Unspecified") && object instanceof EStructuralFeature ?
	        -2 : 
	        -3;

	    return 
	      new ComposedImage(images)
	      {
	        @Override
	        public List<ComposedImage.Point> getDrawPoints(Size size)
	        {
	          List<ComposedImage.Point> result = super.getDrawPoints(size);
	          if (result.size() > 1)
	          {
	            result.get(0).y = offset;
	          }
	          return result;
	        }
	      };
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

		switch (notification.getFeatureID(ETypedElement.class)) {
			case ApiPackage.ETYPED_ELEMENT__ORDERED:
			case ApiPackage.ETYPED_ELEMENT__UNIQUE:
			case ApiPackage.ETYPED_ELEMENT__MANY:
			case ApiPackage.ETYPED_ELEMENT__REQUIRED:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
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
	}

}
