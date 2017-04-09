package de.generia.tools.model.api.trafo.ecore.importer;

import java.util.Map;

import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.importer.ModelImporter;

public class ApiImporter extends ModelImporter {
	private ModelImporter.EPackageImportInfo exportData;
	private Map<EPackage, GenPackage> ePackageToGenPackage;

	private GenPackage currentGenPackage;
	private URI currentArtifactURI;

	@Override
	public String getID() {
		return ApiImporter.class.getPackage().getName();
	}
}