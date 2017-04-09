package de.generia.tools.model.api.util;

import de.generia.tools.model.api.EModelElement;

public class ApiModelUtil {
	public static String getFullName(EModelElement pModelElement) {
		if (pModelElement == null) {
			return null;
		}
		EModelElement lContainer = (EModelElement) pModelElement.eContainer();
		
		String lFullName = getFullName(lContainer);
		if (lFullName == null) {
			return pModelElement.getName();
		}
		return lFullName + "." + pModelElement.getName();
	}
}
