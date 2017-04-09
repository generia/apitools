package de.generia.tools.model.xecore.binding;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import de.generia.tools.model.xecore.binding.ModelResourceBinding.BindingInfo;
import de.generia.tools.model.xecore.binding.stream.ModelInputStream;
import de.generia.tools.model.xecore.binding.stream.ModelOutputStream;
import de.generia.tools.model.xecore.binding.stream.ModelInputStream.ValueHandler;
import de.generia.tools.model.xecore.model.ModelNode;
import de.generia.tools.model.xecore.schema.ModelSchema;


public class ModelResourceImpl extends ResourceImpl {

	private BindingInfo mBindingInfo;
	private boolean mObjectInsideRootFolder = true;
	private boolean mNestedExternalObjects = true;


	public static class ProxyInvocationHandler implements InvocationHandler {
		private URI mProxyUri;
		private Resource mResource;
		private EClass mClass;
		private static final Method mIsProxy;
		private static final Method mGetProxyUri;
		private static final Method mEResource;
		private static Method mEClass;
//		private static Method mEquals;
//		private static Method mHashCode;

		static {
			try {
				mIsProxy = InternalEObject.class.getMethod("eIsProxy", new Class<?>[]{});
				mGetProxyUri = InternalEObject.class.getMethod("eProxyURI", new Class<?>[]{});
				mEResource = InternalEObject.class.getMethod("eResource", new Class<?>[]{});
				mEClass = InternalEObject.class.getMethod("eClass", new Class<?>[]{});
//				mEquals = Object.class.getMethod("equals", new Class<?>[]{Object.class});
//				mHashCode = Object.class.getMethod("hashCode", new Class<?>[]{});
			} catch (Exception e) {
				throw new RuntimeException("can't get method", e);
			}
		}
		
		public ProxyInvocationHandler(Resource pResource, EClass pClass, URI pProxyUri) {
			//System.out.println("creating proxy: class='" + pClass.getName() + "' uri='" + pProxyUri + "'");
			mProxyUri = pProxyUri;
			mResource = pResource;
			mClass = pClass;
		}

		@Override
		public Object invoke(Object pProxy, Method pMethod, Object[] pArgs) throws Throwable {
			if (pMethod.equals(mIsProxy)) {
				return true;
			}
			if (pMethod.equals(mGetProxyUri)) {
				return mProxyUri;
			}
			if (pMethod.equals(mEResource)) {
				return mResource;
			}
			if (pMethod.equals(mEClass)) {
				return mClass;
			}
//			if (pMethod.equals(mEquals)) {
//				return doEquals(pArgs);
//			}
//			if (pMethod.equals(mHashCode)) {
//				return doHashCode();
//			}
			return null;
		}

//		private int doHashCode() {
//			return mProxyUri.hashCode();
//		}
//
//		private boolean doEquals(Object[] pArgs) {
//			if (pArgs.length != 1) {
//				return false;
//			}
//			Object lArg = pArgs[0];
//			if (lArg == null) {
//				return false;
//			}
//			if (!(lArg instanceof InternalEObject)) {
//				return false;
//			}
//			InternalEObject lObject = (InternalEObject)lArg;
//			URI lOtherProxyURI = lObject.eProxyURI();
//			if (lOtherProxyURI == null) {
//				return false;
//			}
//			return mProxyUri.equals(lOtherProxyURI);
//		}
	}

	private class Loader {
		public class ResolvingValueHandler implements ValueHandler {
			private Resource mResource;
			private List<ResolveInfo> mResolveInfos = new ArrayList<ResolveInfo>();
			private ModelSchema mModelSchema;
			
			private class ResolveInfo {
				EObject mContext;
				EReference mReference;
				String mValue;
				
				public ResolveInfo(EObject pContext, EReference pReference, String pValue) {
					mContext = pContext;
					mReference = pReference;
					mValue = pValue;
				}

				public void resolve() {
					EObject lObject = null;
					if (isResourceLocalUri(mValue)) {
						lObject = mResource.getEObject(mValue);
					} else {
						URI lProxyUri = URI.createURI(mValue);
						if (lProxyUri.isRelative()) {
							lProxyUri = lProxyUri.resolve(mResource.getURI(), true);
						}
//						EClass lClass = mReference.getEReferenceType();
//						ProxyInvocationHandler lInvocationHandler = new ProxyInvocationHandler(mResource, lClass, lProxyUri);
//						Class<?> lInterface = lClass.getInstanceClass();
//						lObject = (EObject) Proxy.newProxyInstance(mContext.getClass().getClassLoader(), new Class[]{lInterface, InternalEObject.class}, lInvocationHandler);
						lObject = mResource.getResourceSet().getEObject(lProxyUri, true);
					}
					setValue(mContext, mReference, lObject);
				}
			}

			public ResolvingValueHandler(Resource pResource) {
				mResource = pResource;
				mModelSchema = mBindingInfo.getModelResourceBinding().getModelSchema();
			}
			
			private void setValue(EObject pContext, EStructuralFeature pFeature, Object pValue) {
				if (pFeature.isMany()) {
					@SuppressWarnings("unchecked")
					List<Object> lList = (List<Object>) pContext.eGet(pFeature);
					lList.add(pValue);
				} else {
					pContext.eSet(pFeature, pValue);
				}
			}

			private boolean isResourceLocalUri(String pValue) {
				if (pValue.indexOf("#") == -1) {
					return true;
				}
				return false;
			}

			@Override
			public void handleAttribute(EObject pContext, EAttribute pAttribute, String pValue) {
				Object lValue = mModelSchema.toValue(pAttribute.getEAttributeType(), pValue);
				setValue(pContext, pAttribute, lValue);
			}

			@Override
			public void handleReference(EObject pContext, EReference pReference, String pValue, String pNamespace) {
				ResolveInfo lResolveInfo = new ResolveInfo(pContext, pReference, pValue);
				mResolveInfos.add(lResolveInfo);
			}
			
			public void resolveReferences() {
				for (ResolveInfo lResolveInfo : mResolveInfos) {
					lResolveInfo.resolve();
				}
			}
		}

		public void load(InputStream pInputStream) {
			ResolvingValueHandler lValueHandler = new ResolvingValueHandler(ModelResourceImpl.this);
			ModelInputStream lModelInputStream = new ModelInputStream(mBindingInfo, lValueHandler);
			EObject lObject = lModelInputStream.read(pInputStream);
			getContents().add(lObject);
			lValueHandler.resolveReferences();
			doLoadExternalStores(lObject);
		}

		private void doLoadExternalStores(EObject pObject) {
			//((InternalEObject)pObject).eSetResource(this, null);

			// process children first
			for (EObject lChild : pObject.eContents()) {
				doLoadExternalStores(lChild);
			}

			// load filenames for external proxies
			Set<EStructuralFeature> lExternalStoreFeatures = mBindingInfo.getExternalStoreFeatures(pObject.eClass());
			Set<String> lExtensions = new HashSet<String>();
			for (EStructuralFeature lFeature : lExternalStoreFeatures) {
				lExtensions.addAll(mBindingInfo.getModelResourceBinding().getExtensions((EClass)lFeature.getEType()));
			}

			if (lExtensions.isEmpty()) {
				return;
			}

			// create external proxies per feature
			IFileStore lObjectFolder = getObjectFolder(pObject);
			List<String> lExternalChildren = getExternalChildren(pObject, lExtensions);
			for (EStructuralFeature lFeature : lExternalStoreFeatures) {
				List<String> lFeatureChildren = getFeatureChildren(lFeature, lExternalChildren);
				for (String lFeatureChild : lFeatureChildren) {
					EObject lProxy = createExternalProxy(lObjectFolder, lFeature, lFeatureChild);
					setFeature(pObject, lFeature, lProxy);
				}
				lExternalChildren.removeAll(lFeatureChildren);
			}
		}

		private void setFeature(EObject pObject, EStructuralFeature pFeature, EObject pProxy) {
			if (pFeature.isMany()) {
				@SuppressWarnings("unchecked")
				List<EObject> lList = (List<EObject>) pObject.eGet(pFeature);
				lList.add(pProxy);
			} else {
				pObject.eSet(pFeature, pProxy);
			}
		}
		private EObject createExternalProxy(IFileStore pObjectFolder, EStructuralFeature pFeature, String pName) {
	//		Class<?> lInterface = lClass.getInstanceClass();
	//
	//		String lInstanceClass = lInterface.getPackage().getName() + ".impl." + lInterface.getSimpleName() + "Impl";
	//
	//		ModelNode lObject;
	//		try {
	//			lObject = (ModelNode) Class.forName(lInstanceClass).newInstance();
	//		} catch (Exception e) {
	//			throw new RuntimeException("can't instantiate model-node for class '" + lInstanceClass  + "'", e);
	//		}
			URI lProxyUri = getFileStoreUri(pObjectFolder).appendSegment(pName);
			// TODO: set correct resource for proxy (instead of null)
			EClass lClass = (EClass) pFeature.getEType();
			ProxyInvocationHandler lInvocationHandler = new ProxyInvocationHandler(null, lClass, lProxyUri);
			Class<?> lInterface = lClass.getInstanceClass();
			EObject lObject = (EObject) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{lInterface, InternalEObject.class}, lInvocationHandler);
//			EClass lClass = (EClass) pFeature.getEType();
//			URI lProxyUri = getFileStoreUri(pObjectFolder).appendSegment(pName);
//			ModelNode lObject = (ModelNode) mBindingInfo.getModelResourceBinding().getModelSchema().newInstance(lClass);
//			((InternalEObject)lObject).eSetProxyURI(lProxyUri);
			return lObject;
		}

		private List<String> getFeatureChildren(EStructuralFeature pFeature, List<String> pExternalChildren) {
			Set<String> lExtensions = mBindingInfo.getModelResourceBinding().getExtensions((EClass)pFeature.getEType());
			Pattern lPattern = getExtensionsPattern(lExtensions);
			List<String> lChildren = new ArrayList<String>();
			for (String lChild : pExternalChildren) {
				if (lPattern.matcher(lChild).matches()) {
					lChildren.add(lChild);
				}
			}
			return lChildren;
		}

		private List<String> getExternalChildren(EObject pObject, Set<String> pExtensions) {
			List<String> lChildren = new ArrayList<String>();
			Pattern lPattern = getExtensionsPattern(pExtensions);
			try {
				IFileStore lObjectFolder = getObjectFolder(pObject);
				String[] lChildNames = lObjectFolder.childNames(EFS.NONE, null);
				for (String lChild : lChildNames) {
					if (lPattern.matcher(lChild).matches()) {
						lChildren.add(lChild);
					}
				}
				return lChildren;
			} catch (CoreException e) {
				throw new RuntimeException("can't get child-names for object '" + pObject + "'", e);
			}
		}

		private Pattern getExtensionsPattern(Set<String> pExtensions) {
			StringBuilder lRegex = new StringBuilder();
			String lSep = "";
			for (String lExtension : pExtensions) {
				lRegex.append(lSep);
				lRegex.append(".*\\." + lExtension + "$");
				lSep = "|";
			}
			return Pattern.compile(lRegex.toString());
		}
	}

	private class Saver {
		private Map<?, ?> mOptions;


		public void save(OutputStream pOutputStream, Map<?, ?> pOptions) {
			mOptions = pOptions;

			if (getContents().isEmpty()) {
				return;
			}
			EObject lObject = getRootObject();

			// save external stores first to setup resource-property in objects
			doSaveExternalStores(lObject);

			// save this resource
			ModelOutputStream lModelOutputStream = new ModelOutputStream(mBindingInfo);
			lModelOutputStream.write(pOutputStream, lObject);
		}


		private void doSaveExternalStores(EObject pObject) {

			// process children first
			for (EObject lChild : pObject.eContents()) {
				doSaveExternalStores(lChild);
			}

			// load filenames for external proxies
			Set<EStructuralFeature> lExternalStoreFeatures = mBindingInfo.getExternalStoreFeatures(pObject.eClass());
			for (EStructuralFeature lFeature : lExternalStoreFeatures) {
				if (lFeature.isMany()) {
					@SuppressWarnings("unchecked")
					List<EObject> lList = (List<EObject>) pObject.eGet(lFeature);
					for (EObject lChild : lList) {
						saveExternalChild(pObject, lFeature, lChild);
					}
				} else {
					EObject lChild = (EObject) pObject.eGet(lFeature);
					saveExternalChild(pObject, lFeature, lChild);
				}
			}
		}


		private void saveExternalChild(EObject pParent, EStructuralFeature pFeature, EObject pChild) {
			if (pChild.eResource() != null) {
				saveResource(pChild.eResource());
				return;
			}

			ResourceSet lResourceSet = pParent.eResource().getResourceSet();
			IFileStore lParentFolder = getObjectFolder(pParent);
			URI lParentUri = getFileStoreUri(lParentFolder);
			String lExtension = mBindingInfo.getModelResourceBinding().getDefaultExtension((EClass)pFeature.getEType());
			String lName = ((ModelNode)pChild).getName();
			if (lName == null) {
				throw new NullPointerException("name of child-object '" + pChild + "' is null");
			}
			URI lChildUri = lParentUri.appendSegment(lName).appendFileExtension(lExtension);

			Resource lChildResource = lResourceSet.createResource(lChildUri);
			lChildResource.getContents().add(pChild);
			saveResource(lChildResource);
		}


		private void saveResource(Resource pResource) {
			try {
				pResource.save(mOptions);
			} catch (IOException e) {
				throw new RuntimeException("can't save external resource '" + pResource.getURI() + "'", e);
			}
		}
	}

	public ModelResourceImpl(URI pUri, BindingInfo pBindingInfo) {
		super(pUri);
		mBindingInfo = pBindingInfo;
	}

	public void setObjectInsideRootFolder(boolean pObjectInsideRootFolder) {
		mObjectInsideRootFolder = pObjectInsideRootFolder;
	}

	public void setNestedExternalObjects(boolean pNestedExternalObjects) {
		mNestedExternalObjects = pNestedExternalObjects;
	}

	@Override
	public String getURIFragment(EObject pEObject) {
		String lFragment = super.getURIFragment(pEObject);
		if (lFragment.startsWith("/null/")) {
			lFragment = lFragment.substring("/null".length());
		}
		return lFragment;
	}

	@Override
	protected EObject getEObjectForURIFragmentRootSegment(String pSegment) {
		for (EObject lObject : getContents()) {
			if (getURIFragmentRootSegment(lObject).equals(pSegment)) {
				return lObject;
			}
		}
		return null;
	}

	@Override
	protected String getURIFragmentRootSegment(EObject pObject) {
		if (pObject instanceof ModelNode) {
			return ((ModelNode)pObject).getName();
		}
		throw new RuntimeException("expected instance of '" + ModelNode.class.getName() + "' as root object, but got '" + pObject.getClass().getName() + "'");
	}

	@Override
	public EObject getEObject(String pUriFragment) {
		ModelNode lRootObject = (ModelNode) getRootObject();
		if (pUriFragment == null || pUriFragment.equals("/")) {
			return lRootObject;
		}
		String lRootName = lRootObject.getName();
		// check for unnamed root object
		if (lRootName == null) {
			return ((InternalEObject)lRootObject).eObjectForURIFragmentSegment(pUriFragment);
		}
		if (lRootName.equals(pUriFragment)) {
			return lRootObject;
		}
		return super.getEObject(pUriFragment);
	}

	protected EObject getRootObject() {
		EList<EObject> lContents = getContents();
		if (lContents == null || lContents.isEmpty()) {
			return null;
		}
		if (getContents().size() > 1) {
			throw new IllegalStateException("can't get root object for resource '" + getURI() + "', contents size is '" + getContents().size() + "' but should be '1'");
		}
		return lContents.get(0);
	}

	@Override
	public void load(Map<?, ?> pOptions) throws IOException {
		if (pOptions == null) {
			super.load(new HashMap<Object, Object>());
		} else {
			super.load(pOptions);
		}
	}

	@Override
	public void save(Map<?, ?> pOptions) throws IOException {
		if (pOptions == null) {
			super.save(new HashMap<Object, Object>());
		} else {
			super.save(pOptions);
		}
	}

	@Override
	protected void doLoad(InputStream pInputStream, Map<?, ?> pOptions) throws IOException {
		Loader lLoader = new Loader();
		lLoader.load(pInputStream);
	}


	@Override
	protected void doSave(OutputStream pOutputStream, Map<?, ?> pOptions) throws IOException {
		Saver lSaver = new Saver();
		lSaver.save(pOutputStream, pOptions);
	}

	private IFileStore getObjectFolder(EObject pObject) {
		IFileStore lObjectFolder = getObjectRootFolder(pObject);
		if (mNestedExternalObjects ) {
			IPath lPath = new Path(getURIFragment(pObject));
			lPath = lPath.removeFirstSegments(1);
			lObjectFolder = lObjectFolder.getFileStore(lPath);
		}
		return lObjectFolder;
	}

	private IFileStore getObjectRootFolder(EObject pObject) {
		if (mObjectInsideRootFolder) {
			return getResourceFolder();
		}
		URI lUri = getURI().trimFileExtension();
		return getFileStore(lUri);
	}

	private IFileStore getResourceFolder() {
		URI lParentUri = getURI().trimSegments(1);
		return getFileStore(lParentUri);
	}

	private IFileStore getFileStore(URI pUri) {
		String lString = pUri.toString();
		// TODO: remove hardcoded path!!!
		if (lString.startsWith("platform:/resource")) {
			lString = "file:/C:/home/dev/runtime-EclipseApplication" + lString.substring("platform:/resource".length());
		}
		java.net.URI lUri = java.net.URI.create(lString);
		try {
			return EFS.getStore(lUri);
		} catch (CoreException e) {
			throw new RuntimeException("can't get file-store for uri '" + lUri + "'", e);
		}
	}

	private URI getFileStoreUri(IFileStore pFileStore) {
		java.net.URI lUri = pFileStore.toURI();
		return URI.createURI(lUri.toString());
	}
}
