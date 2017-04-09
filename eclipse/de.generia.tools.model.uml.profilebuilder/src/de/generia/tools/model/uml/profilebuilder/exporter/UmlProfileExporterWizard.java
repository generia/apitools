package de.generia.tools.model.uml.profilebuilder.exporter;

import org.eclipse.emf.converter.ModelConverter;
import org.eclipse.emf.exporter.ui.contribution.base.ModelExporterDirectoryURIPage;
import org.eclipse.emf.exporter.ui.contribution.base.ModelExporterOptionsPage;
import org.eclipse.emf.exporter.ui.contribution.base.ModelExporterPackagePage;
import org.eclipse.emf.exporter.ui.contribution.base.ModelExporterWizard;

/**
 * @since 2.2.0
 */
public class UmlProfileExporterWizard extends ModelExporterWizard {
	private static final String TITLE = "UML Profile Exporter Wizard";

	@Override
	protected ModelConverter createModelConverter() {
		return new UmlProfileExporter();
	}

	@Override
	public void addPages() {
		ModelExporterDirectoryURIPage directoryURIPage = new ModelExporterDirectoryURIPage(getModelExporter(), "HTMLExporterBaseLocationPage");
		directoryURIPage.setTitle(TITLE);
		addPage(directoryURIPage);

		ModelExporterPackagePage packagePage = new ModelExporterPackagePage(getModelExporter(), "HTMLExporterGenModelDetailPage");
		packagePage.setTitle(TITLE);
		// packagePage.setShowReferencedGenModels(true);
		addPage(packagePage);

		ModelExporterOptionsPage optionsPage = new ModelExporterOptionsPage(getModelExporter(), "HTMLExporterOptionsPage");
		optionsPage.setTitle(TITLE);
		addPage(optionsPage);
	}
}
