package de.generia.tools.model.uml.profilebuilder.exporter;

import java.util.Map;

import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.exporter.ModelExporter;

import de.generia.tools.model.uml.profilebuilder.EcoreProfileBuilder;

public class UmlProfileExporter extends ModelExporter {
	private ModelExporter.ExportData exportData;
	private Map<EPackage, GenPackage> ePackageToGenPackage;

	private GenPackage currentGenPackage;
	private URI currentArtifactURI;

	@Override
	public String getID() {
		return UmlProfileExporter.class.getPackage().getName();
	}

	@Override
	protected String getDefaultArtifactLocation(EPackage ePackage) {
		return getDefaultArtifactFileName(ePackage) + ".profile.uml";
	}

	@Override
	protected String doCheckEPackageArtifactLocation(String location, String packageName) {
		if (!location.endsWith(".profile.uml")) {
			return "The artifact file name must end in '.profile.uml'";
		}
		return super.doCheckEPackageArtifactLocation(location, packageName);
	}

	@Override
	protected Diagnostic doExport(Monitor pMonitor, ModelExporter.ExportData pExportData) throws Exception {
		EList<GenPackage> lGenPackages = getGenModel().getGenPackages();
		if (lGenPackages.isEmpty()) {
			return Diagnostic.CANCEL_INSTANCE;
		}
		GenPackage lGenPackage = lGenPackages.get(0);
		EPackage lEcorePackage = lGenPackage.getEcorePackage();
		URI lOutputUri = pExportData.genPackageToArtifactURI.get(lGenPackage);
		
		EcoreProfileBuilder lProfileBuilder = new EcoreProfileBuilder(null);
		lProfileBuilder.buildProfile(lEcorePackage, lOutputUri);
		return Diagnostic.OK_INSTANCE;
	}

	public GenPackage getCurrentGenPackage() {
		return currentGenPackage;
	}

	public URI getPackageArtifacttURI(EPackage ePackage) {
		GenPackage eClassifierGenPackage = ePackageToGenPackage.get(ePackage);
		if (eClassifierGenPackage != null) {
			URI artifactURI = exportData.genPackageToArtifactURI.get(eClassifierGenPackage);
			if (artifactURI == null) {
				artifactURI = exportData.referencedGenPackagesToArtifactURI.get(eClassifierGenPackage);
			}

			if (artifactURI != null) {
				return artifactURI.deresolve(currentArtifactURI);
			}
		}
		return null;
	}
}