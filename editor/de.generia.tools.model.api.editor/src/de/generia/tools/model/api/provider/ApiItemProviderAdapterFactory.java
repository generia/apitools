/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

import de.generia.tools.model.api.util.ApiAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ApiItemProviderAdapterFactory extends ApiAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApiItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.generia.tools.model.api.EAnnotation} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EAnnotationItemProvider eAnnotationItemProvider;

	/**
	 * This creates an adapter for a {@link de.generia.tools.model.api.EAnnotation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEAnnotationAdapter() {
		if (eAnnotationItemProvider == null) {
			eAnnotationItemProvider = new EAnnotationItemProvider(this);
		}

		return eAnnotationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.generia.tools.model.api.EAnnotationElement} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EAnnotationElementItemProvider eAnnotationElementItemProvider;

	/**
	 * This creates an adapter for a {@link de.generia.tools.model.api.EAnnotationElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEAnnotationElementAdapter() {
		if (eAnnotationElementItemProvider == null) {
			eAnnotationElementItemProvider = new EAnnotationElementItemProvider(this);
		}

		return eAnnotationElementItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.generia.tools.model.api.EDataType} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EDataTypeItemProvider eDataTypeItemProvider;

	/**
	 * This creates an adapter for a {@link de.generia.tools.model.api.EDataType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEDataTypeAdapter() {
		if (eDataTypeItemProvider == null) {
			eDataTypeItemProvider = new EDataTypeItemProvider(this);
		}

		return eDataTypeItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.generia.tools.model.api.EEnum} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EEnumItemProvider eEnumItemProvider;

	/**
	 * This creates an adapter for a {@link de.generia.tools.model.api.EEnum}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEEnumAdapter() {
		if (eEnumItemProvider == null) {
			eEnumItemProvider = new EEnumItemProvider(this);
		}

		return eEnumItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.generia.tools.model.api.EEnumLiteral} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EEnumLiteralItemProvider eEnumLiteralItemProvider;

	/**
	 * This creates an adapter for a {@link de.generia.tools.model.api.EEnumLiteral}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEEnumLiteralAdapter() {
		if (eEnumLiteralItemProvider == null) {
			eEnumLiteralItemProvider = new EEnumLiteralItemProvider(this);
		}

		return eEnumLiteralItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.generia.tools.model.api.EClass} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClassItemProvider eClassItemProvider;

	/**
	 * This creates an adapter for a {@link de.generia.tools.model.api.EClass}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEClassAdapter() {
		if (eClassItemProvider == null) {
			eClassItemProvider = new EClassItemProvider(this);
		}

		return eClassItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.generia.tools.model.api.EAttribute} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EAttributeItemProvider eAttributeItemProvider;

	/**
	 * This creates an adapter for a {@link de.generia.tools.model.api.EAttribute}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEAttributeAdapter() {
		if (eAttributeItemProvider == null) {
			eAttributeItemProvider = new EAttributeItemProvider(this);
		}

		return eAttributeItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.generia.tools.model.api.EReference} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EReferenceItemProvider eReferenceItemProvider;

	/**
	 * This creates an adapter for a {@link de.generia.tools.model.api.EReference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEReferenceAdapter() {
		if (eReferenceItemProvider == null) {
			eReferenceItemProvider = new EReferenceItemProvider(this);
		}

		return eReferenceItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.generia.tools.model.api.EOperation} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EOperationItemProvider eOperationItemProvider;

	/**
	 * This creates an adapter for a {@link de.generia.tools.model.api.EOperation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEOperationAdapter() {
		if (eOperationItemProvider == null) {
			eOperationItemProvider = new EOperationItemProvider(this);
		}

		return eOperationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.generia.tools.model.api.EParameter} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EParameterItemProvider eParameterItemProvider;

	/**
	 * This creates an adapter for a {@link de.generia.tools.model.api.EParameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEParameterAdapter() {
		if (eParameterItemProvider == null) {
			eParameterItemProvider = new EParameterItemProvider(this);
		}

		return eParameterItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link de.generia.tools.model.api.EPackage} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EPackageItemProvider ePackageItemProvider;

	/**
	 * This creates an adapter for a {@link de.generia.tools.model.api.EPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEPackageAdapter() {
		if (ePackageItemProvider == null) {
			ePackageItemProvider = new EPackageItemProvider(this);
		}

		return ePackageItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void dispose() {
		if (eAnnotationItemProvider != null) eAnnotationItemProvider.dispose();
		if (eAnnotationElementItemProvider != null) eAnnotationElementItemProvider.dispose();
		if (eDataTypeItemProvider != null) eDataTypeItemProvider.dispose();
		if (eEnumItemProvider != null) eEnumItemProvider.dispose();
		if (eEnumLiteralItemProvider != null) eEnumLiteralItemProvider.dispose();
		if (eClassItemProvider != null) eClassItemProvider.dispose();
		if (eAttributeItemProvider != null) eAttributeItemProvider.dispose();
		if (eReferenceItemProvider != null) eReferenceItemProvider.dispose();
		if (eOperationItemProvider != null) eOperationItemProvider.dispose();
		if (eParameterItemProvider != null) eParameterItemProvider.dispose();
		if (ePackageItemProvider != null) ePackageItemProvider.dispose();
	}

}
