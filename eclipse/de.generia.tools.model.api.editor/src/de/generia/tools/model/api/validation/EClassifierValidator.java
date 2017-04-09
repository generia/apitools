/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api.validation;

import de.generia.tools.model.api.EPackage;

/**
 * A sample validator interface for {@link de.generia.tools.model.api.EClassifier}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface EClassifierValidator {
	boolean validate();

	boolean validateInstanceTypeName(String value);
	boolean validateDefaultValue(String value);
	boolean validatePackage(EPackage value);

	boolean validateEPackage(EPackage value);
}
