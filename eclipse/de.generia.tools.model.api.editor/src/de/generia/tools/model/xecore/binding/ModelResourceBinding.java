package de.generia.tools.model.xecore.binding;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;

import de.generia.tools.model.xecore.binding.stream.ContentType;
import de.generia.tools.model.xecore.schema.ModelSchema;


public class ModelResourceBinding {
	//private static final Log xlog = LogFactory.getLog(ModelResourceBinding.class);

	public static class BindingInfo {
		public static class MixedContentInfo {
			private EClass mTextContentClass = null;
			private EStructuralFeature mTextFeature = null;
			private EStructuralFeature mChildrenFeature = null;
			public EClass getTextContentClass() {
				return mTextContentClass;
			}
			public void setTextContentClass(EClass pTextContentClass) {
				mTextContentClass = pTextContentClass;
			}
			public EStructuralFeature getTextFeature() {
				return mTextFeature;
			}
			public void setTextFeature(EStructuralFeature pTextFeature) {
				mTextFeature = pTextFeature;
			}
			public EStructuralFeature getChildrenFeature() {
				return mChildrenFeature;
			}
			public void setChildrenFeature(EStructuralFeature pChildrenFeature) {
				mChildrenFeature = pChildrenFeature;
			}
		}

		public static class ExternalStoreInfo {
			private Set<EStructuralFeature> mExternalStoreFeatures = new HashSet<EStructuralFeature>();

			public void addExternalStoreFeature(EStructuralFeature pExternalStoreFeature) {
				mExternalStoreFeatures.add(pExternalStoreFeature);
			}

			public Set<EStructuralFeature> getExternalStoreFeatures() {
				return mExternalStoreFeatures;
			}

			public boolean isExternalStoreFeature(EStructuralFeature pFeature) {
				return mExternalStoreFeatures.contains(pFeature);
			}
		}

		private ModelResourceBinding mModelResourceBinding;
		private Map<EClass, MixedContentInfo> mMixedContentInfoMap = new HashMap<EClass, MixedContentInfo>();
		private Map<EClass, ExternalStoreInfo> mExternalStoreFeatureMap = new HashMap<EClass, ExternalStoreInfo>();

		public BindingInfo(ModelResourceBinding pModelResourceBinding) {
			mModelResourceBinding = pModelResourceBinding;
		}

		public ModelResourceBinding getModelResourceBinding() {
			return mModelResourceBinding;
		}

		public MixedContentInfo getMixedContentInfo(EClass pClass) {
			return mMixedContentInfoMap.get(pClass);
		}

		public ExternalStoreInfo getExternalStoreInfo(EClass pClass) {
			return mExternalStoreFeatureMap.get(pClass);
		}


		public boolean isMixedContentClass(EClass pClass) {
			return mMixedContentInfoMap.get(pClass) != null;
		}

		public boolean isExternalStoreFeature(EStructuralFeature pFeature) {
			EClass lContainingClass = pFeature.getEContainingClass();
			ExternalStoreInfo lExternalStoreInfo = getExternalStoreInfo(lContainingClass);
			if (lExternalStoreInfo == null) {
				return false;
			}
			return lExternalStoreInfo.isExternalStoreFeature(pFeature);
		}

		public void addMixedContentInfo(EClass pClass, MixedContentInfo pMixedContentInfo) {
			mMixedContentInfoMap.put(pClass, pMixedContentInfo);
		}

		public void addExternalStoreInfo(EClass pClass, ExternalStoreInfo pExternalStoreInfo) {
			mExternalStoreFeatureMap.put(pClass, pExternalStoreInfo);
		}

		public Set<EStructuralFeature> getExternalStoreFeatures(EClass pClass) {
			ExternalStoreInfo lExternalStoreInfo = mExternalStoreFeatureMap.get(pClass);
			if (lExternalStoreInfo == null) {
				return Collections.emptySet();
			}
			return lExternalStoreInfo.getExternalStoreFeatures();
		}
	}

	// general maps
	private Map<String, Set<EClass>> mExtensionClassesMap = new HashMap<String, Set<EClass>>();
	private Map<String, Set<ContentType>> mExtensionContentTypesMap = new HashMap<String, Set<ContentType>>();

	private Map<EClass, Set<String>> mClassExtensionsMap = new HashMap<EClass, Set<String>>();
	private Map<EClass, Set<ContentType>> mClassContentTypesMap = new HashMap<EClass, Set<ContentType>>();

	private Map<ContentType, Set<EClass>> mContentTypeClassesMap = new HashMap<ContentType, Set<EClass>>();
	private Map<ContentType, Set<String>> mContentTypeExtensionsMap = new HashMap<ContentType, Set<String>>();

	// default maps
	private Map<String, EClass> mDefaultExtensionClassMap = new HashMap<String, EClass>();
	private Map<String, ContentType> mDefaultExtensionContentTypeMap = new HashMap<String, ContentType>();

	private Map<EClass, String> mDefaultClassExtensionMap = new HashMap<EClass, String>();
	private Map<EClass, ContentType> mDefaultClassContentTypeMap = new HashMap<EClass, ContentType>();

	private Map<ContentType, EClass> mDefaultContentTypeClassMap = new HashMap<ContentType, EClass>();
	private Map<ContentType, String> mDefaultContentTypeExtensionMap = new HashMap<ContentType, String>();

	// resource factory map
	private Map<String, Resource.Factory> mResourceFactoryMap = new HashMap<String, Resource.Factory>();

	private ModelSchema mModelSchema;
	private NamingConvention mNamingConvention;

	public ModelResourceBinding(ModelSchema pModelSchema, NamingConvention pNamingConvention) {
		mModelSchema = pModelSchema;
		mNamingConvention = pNamingConvention;
	}

	public ModelSchema getModelSchema() {
		return mModelSchema;
	}

	public void setModelSchema(ModelSchema pModelSchema) {
		mModelSchema = pModelSchema;
	}

	public NamingConvention getNamingConvention() {
		return mNamingConvention;
	}

	public void setNamingConvention(NamingConvention pNamingConvention) {
		mNamingConvention = pNamingConvention;
	}


	public void addMapping(String pExtension, EClass pClass, ContentType pContentType, Factory pResourceFactory) {
		addExtensionClassMapping(pExtension, pClass);
		addExtensionContentTypeMapping(pExtension, pContentType);
		addClassContentTypeMapping(pClass, pContentType, pResourceFactory);

		Map<String, Object> lExtensionToFactoryMap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
		if (lExtensionToFactoryMap.containsKey(pExtension)) {
			//if (log.isWarnEnabled()) log.warn("extension '" + pExtension + "' is already configured for factory '" + lExtensionToFactoryMap.get(pExtension) + "' using '" + pResourceFactory + "' instead.");
		}
		lExtensionToFactoryMap.put(pExtension, pResourceFactory);
	}


	public void addExtensionClassMapping(String pExtension, EClass pClass) {
		Set<EClass> lClassSet = mExtensionClassesMap.get(pExtension);
		if (lClassSet == null) {
			lClassSet = new HashSet<EClass>();
			mExtensionClassesMap.put(pExtension, lClassSet);
		}
		lClassSet.add(pClass);

		Set<String> lExtensionSet = mClassExtensionsMap.get(pClass);
		if (lExtensionSet == null) {
			lExtensionSet = new HashSet<String>();
			mClassExtensionsMap.put(pClass, lExtensionSet);
		}
		lExtensionSet.add(pExtension);

		// set default
		if (!mDefaultExtensionClassMap.containsKey(pExtension)) {
			setDefaultExtensionClassMapping(pExtension, pClass);
		}
	}

	public void addExtensionContentTypeMapping(String pExtension, ContentType pContentType) {
		Set<ContentType> lContentTypeSet = mExtensionContentTypesMap.get(pExtension);
		if (lContentTypeSet == null) {
			lContentTypeSet = new HashSet<ContentType>();
			mExtensionContentTypesMap.put(pExtension, lContentTypeSet);
		}
		lContentTypeSet.add(pContentType);

		Set<String> lExtensionSet = mContentTypeExtensionsMap.get(pContentType);
		if (lExtensionSet == null) {
			lExtensionSet = new HashSet<String>();
			mContentTypeExtensionsMap.put(pContentType, lExtensionSet);
		}
		lExtensionSet.add(pExtension);

		// set default
		if (!mDefaultExtensionContentTypeMap.containsKey(pExtension)) {
			setDefaultExtensionContentTypeMapping(pExtension, pContentType);
		}
	}

	public void addClassContentTypeMapping(EClass pClass, ContentType pContentType, Resource.Factory pResourceFactory) {
		Set<ContentType> lContentTypeSet = mClassContentTypesMap.get(pClass);
		if (lContentTypeSet == null) {
			lContentTypeSet = new HashSet<ContentType>();
			mClassContentTypesMap.put(pClass, lContentTypeSet);
		}
		lContentTypeSet.add(pContentType);

		Set<EClass> lClassSet = mContentTypeClassesMap.get(pContentType);
		if (lClassSet == null) {
			lClassSet = new HashSet<EClass>();
			mContentTypeClassesMap.put(pContentType, lClassSet);
		}
		lClassSet.add(pClass);

		// set default
		if (!mDefaultClassContentTypeMap.containsKey(pClass)) {
			setDefaultClassContentTypeMapping(pClass, pContentType);
		}

		String lFactoryKey = getFactoryKey(pClass, pContentType);
		mResourceFactoryMap.put(lFactoryKey, pResourceFactory);
	}

	public void setDefaultExtensionClassMapping(String pExtension, EClass pClass) {
		mDefaultExtensionClassMap.put(pExtension, pClass);
		mDefaultClassExtensionMap.put(pClass, pExtension);
	}

	public void setDefaultExtensionContentTypeMapping(String pExtension, ContentType pContentType) {
		mDefaultExtensionContentTypeMap.put(pExtension, pContentType);
		mDefaultContentTypeExtensionMap.put(pContentType, pExtension);
	}

	public void setDefaultClassContentTypeMapping(EClass pClass, ContentType pContentType) {
		mDefaultClassContentTypeMap.put(pClass, pContentType);
		mDefaultContentTypeClassMap.put(pContentType, pClass);
	}


	public Set<String> getExtensions(ContentType pContentType) {
		Set<String> lSet = mContentTypeExtensionsMap.get(pContentType);
		if (lSet == null) {
			lSet = Collections.emptySet();
		}
		return lSet;
	}

	public Set<String> getExtensions(EClass pClass) {
		Set<String> lSet = mClassExtensionsMap.get(pClass);
		if (lSet == null) {
			lSet = Collections.emptySet();
		}
		return lSet;
	}

	public Set<EClass> getClasses(ContentType pContentType) {
		Set<EClass> lSet = mContentTypeClassesMap.get(pContentType);
		if (lSet == null) {
			lSet = Collections.emptySet();
		}
		return lSet;
	}

	public Set<EClass> getClasses(String pExtension) {
		Set<EClass> lSet = mExtensionClassesMap.get(pExtension);
		if (lSet == null) {
			lSet = Collections.emptySet();
		}
		return lSet;
	}

	public Set<ContentType> getContentTypes(String pExtension) {
		Set<ContentType> lSet = mExtensionContentTypesMap.get(pExtension);
		if (lSet == null) {
			lSet = Collections.emptySet();
		}
		return lSet;
	}

	public Set<ContentType> getContentTypes(EClass pClass) {
		Set<ContentType> lSet = mClassContentTypesMap.get(pClass);
		if (lSet == null) {
			lSet = Collections.emptySet();
		}
		return lSet;
	}

	public String getDefaultExtension(EClass pClass) {
		return mDefaultClassExtensionMap.get(pClass);
	}

	public String getDefaultExtension(ContentType pContentType) {
		return mDefaultContentTypeExtensionMap.get(pContentType);
	}

	public ContentType getDefaultContentType(EClass pClass) {
		return mDefaultClassContentTypeMap.get(pClass);
	}

	public ContentType getDefaultContentType(String pExtension) {
		return mDefaultExtensionContentTypeMap.get(pExtension);
	}

	public EClass getDefaultClass(ContentType pContentType) {
		return mDefaultContentTypeClassMap.get(pContentType);
	}

	public EClass getDefaultClass(String pExtension) {
		return mDefaultExtensionClassMap.get(pExtension);
	}

	public Resource.Factory getResourceFactory(EClass pClass, ContentType pContentType) {
		String lFactoryKey = getFactoryKey(pClass, pContentType);
		return mResourceFactoryMap.get(lFactoryKey);
	}

	public Resource.Factory getResourceFactory(String pExtension) {
		EClass lClass = getDefaultClass(pExtension);
		ContentType lContentType = getDefaultContentType(pExtension);
		return getResourceFactory(lClass, lContentType);
	}

	public Resource.Factory getResourceFactory(EClass pClass) {
		ContentType lContentType = getDefaultContentType(pClass);
		return getResourceFactory(pClass, lContentType);
	}

	public Resource.Factory getResourceFactory(ContentType pContentType) {
		Set<EClass> lClasses = getClasses(pContentType);
		EClass lClass;
		if (lClasses.size() == 1) {
			lClass = lClasses.iterator().next();
		} else {
			lClass = getDefaultClass(pContentType);
		}
		return getResourceFactory(lClass, pContentType);
	}



	private String getFactoryKey(EClass pClass, ContentType pContentType) {
		return pClass.getEPackage().getNsURI() + "#" + pClass.getName() + "-" + pContentType;
	}
}
