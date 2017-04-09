package de.generia.tools.model.api.trafo.ecore.importer;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.converter.ModelConverter;
import org.eclipse.emf.importer.ui.contribution.base.ModelImporterDetailPage;
import org.eclipse.emf.importer.ui.contribution.base.ModelImporterPackagePage;
import org.eclipse.emf.importer.ui.contribution.base.ModelImporterWizard;

/**
 * @since 2.2.0
 */
public class ApiImporterWizard extends ModelImporterWizard {
	private static final String TITLE = "Api Importer Wizard";

	@Override
	protected ModelConverter createModelConverter() {
		return new ApiImporter();
	}
	
	@Override
	public IFile getOriginalGenModelFile() {
		IFile genModelFile = super.getOriginalGenModelFile();
		return genModelFile;
	}
	@Override
	public boolean performFinish() {
		return super.performFinish();
	}
	
	@Override
	public void addPages() {
		ModelImporterDetailPage detailsPage = new ModelImporterDetailPage(getModelImporter(), "HTMLExporterBaseLocationPage");
		detailsPage.setTitle(TITLE);
		addPage(detailsPage);

		ModelImporterPackagePage packagePage = new ModelImporterPackagePage(getModelImporter(), "HTMLExporterGenModelDetailPage");
		packagePage.setTitle(TITLE);
		// packagePage.setShowReferencedGenModels(true);
		addPage(packagePage);
	}
}
