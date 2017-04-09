package de.generia.tools.model.xecore.schema;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

public class ModelSchema {
	private String mName;
	private Map<String, EPackage> mPackageMap = new HashMap<String, EPackage>();
	private Map<String, Set<EClass>> mClassesMap = new HashMap<String, Set<EClass>>();

	public ModelSchema(String pName) {
		mName = pName;
	}

	public String getName() {
		return mName;
	}

	public void register(EPackage pPackage, EFactory pFactory) {
		pPackage.setEFactoryInstance(pFactory);
		pFactory.setEPackage(pPackage);
		mPackageMap.put(pPackage.getName(), pPackage);
		mClassesMap.clear();
	}

	public Collection<EPackage> getPackages() {
		return mPackageMap.values();
	}

	public EPackage getPackage(String pName) {
		return mPackageMap.get(pName);
	}

	public Set<EClass> getClasses(String pName) {
		Set<EClass> lClasses = mClassesMap.get(pName);
		if (lClasses != null) {
			return lClasses;
		}
		lClasses = new HashSet<EClass>();
		for (EPackage lPackage : mPackageMap.values()) {
			EClassifier lClassifier = lPackage.getEClassifier(pName);
			if (lClassifier instanceof EClass) {
				lClasses.add((EClass) lClassifier);
			}
		}
		mClassesMap.put(pName, lClasses);
		return lClasses;
	}

	public EObject newInstance(EClass pClass) {
		return pClass.getEPackage().getEFactoryInstance().create(pClass);
	}

	public Object toValue(EDataType pType, String pString) {
		return pType.getEPackage().getEFactoryInstance().createFromString(pType, pString);
	}

	public String toString(EDataType pType, Object pValue) {
		return pType.getEPackage().getEFactoryInstance().convertToString(pType, pValue);
	}
}
