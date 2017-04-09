package de.generia.tools.model.api.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.generia.tools.model.api.ApiFactory;
import de.generia.tools.model.api.EAnnotation;
import de.generia.tools.model.api.EModelElement;

public class AnnotationUtil {
	public static boolean hasAnnotation(EModelElement pModelElement, String pName) {
		return getAnnotation(pModelElement, pName, false) != null;
	}
	
	public static EAnnotation getAnnotation(EModelElement pModelElement, String pName) {
		return getAnnotation(pModelElement, pName, true);
	}
	
	public static EAnnotation getAnnotation(EModelElement pModelElement, String pName, boolean pChecked) {
		for (EAnnotation lAnnotation : pModelElement.getAnnotations()) {
			if (lAnnotation.getSource().equals(pName)) {
				return lAnnotation;
			}
		}
		if (pChecked) {
			throw new RuntimeException("missing annotation '" + pName + "' for model-element '" + pModelElement + "'");
		}
		return null;
	}

	public static Collection<EAnnotation> mergeAnnotations(List<EAnnotation> pAnnotations1, List<EAnnotation> pAnnotations2) {
		Map<String, EAnnotation> lMergedMap = toMergedMap(pAnnotations1);
		
		for (EAnnotation lAnnotation : pAnnotations2) {
			EAnnotation lMapped = lMergedMap.get(lAnnotation.getSource());
			lMergedMap.put(lAnnotation.getSource(), mergeAnnotation(lMapped, lAnnotation));
		}
		return lMergedMap.values();
	}
	
	private static Map<String, EAnnotation> toMergedMap(List<EAnnotation> pAnnotations) {
		Map<String, EAnnotation> lMergedMap = new TreeMap<String, EAnnotation>();
		for (EAnnotation lAnnotation : pAnnotations) {
			EAnnotation lMapped = lMergedMap.get(lAnnotation.getSource());
			lMergedMap.put(lAnnotation.getSource(), mergeAnnotation(lMapped, lAnnotation));
		}
		return lMergedMap; 
	}

	public static EAnnotation mergeAnnotation(EAnnotation pAnnotation1, EAnnotation pAnnotation2) {
		if (pAnnotation1 == null) {
			return copyAnnotiation(pAnnotation2);
		}
		if (!pAnnotation1.getSource().equals(pAnnotation2.getSource())) {
			throw new RuntimeException("can't merge annotation '" + pAnnotation1 + "' with '" + pAnnotation2 + "', annotiation sources differ");
		}
		EAnnotation lResult = copyAnnotiation(pAnnotation1);
		if (pAnnotation2 != null) {
			lResult.getElements().addAll(pAnnotation2.getElements());
		}
		return lResult;
	}

	private static EAnnotation copyAnnotiation(EAnnotation pAnnotation1) {
		EAnnotation lAnnotation = ApiFactory.eINSTANCE.createEAnnotation();
		lAnnotation.setName(pAnnotation1.getName());
		lAnnotation.setSource(pAnnotation1.getSource());
		lAnnotation.getElements().addAll(pAnnotation1.getElements());
		for (EAnnotation lSubAnnotation : pAnnotation1.getAnnotations()) {
			lAnnotation.getAnnotations().add(copyAnnotiation(lSubAnnotation));
		}
		return lAnnotation;
	}

	public static EAnnotation createAnnotation(String pName) {
		EAnnotation lAnnotation = ApiFactory.eINSTANCE.createEAnnotation();
		lAnnotation.setName(pName);
		lAnnotation.setSource(pName);
		return lAnnotation;
	}
}
