/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api.validation;

import de.generia.tools.model.api.EEnumLiteral;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link de.generia.tools.model.api.EEnum}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface EEnumValidator {
	boolean validate();

	boolean validateLiterals(EList<EEnumLiteral> value);

	boolean validateELiterals(EList<EEnumLiteral> value);
}
