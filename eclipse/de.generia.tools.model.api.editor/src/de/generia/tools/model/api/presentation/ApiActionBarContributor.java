/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.generia.tools.model.api.presentation;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.ControlAction;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.action.CreateSiblingAction;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.emf.edit.ui.action.LoadResourceAction;
import org.eclipse.emf.edit.ui.action.ValidateAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.resource.UMLResource;

import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.trafo.uml.Api2UmlTrafo;
import de.generia.tools.model.api.util.ApiPlugin;
import de.generia.tools.model.xecore.binding.ModelResourceFactory;

/**
 * This is the action bar contributor for the Api model editor.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
@SuppressWarnings("restriction")
public class ApiActionBarContributor
	extends EditingDomainActionBarContributor
	implements ISelectionChangedListener {
	/**
	 * This keeps track of the active editor.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IEditorPart activeEditorPart;

	/**
	 * This keeps track of the current selection provider.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ISelectionProvider selectionProvider;

	/**
	 * This action opens the Properties view.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected IAction showPropertiesViewAction =
		new Action(ApiPlugin.INSTANCE.getString("_UI_ShowPropertiesView_menu_item"), ApiPlugin.getImageDescriptor("properties.gif")) {
			@Override
			public void run() {
				try {
					getPage().showView("org.eclipse.ui.views.PropertySheet");
				}
				catch (PartInitException exception) {
					ApiPlugin.INSTANCE.log(exception);
				}
			}
		};

	/**
	 * This action refreshes the viewer of the current editor if the editor
	 * implements {@link org.eclipse.emf.common.ui.viewer.IViewerProvider}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected IAction refreshViewerAction =
		new Action(ApiPlugin.INSTANCE.getString("_UI_RefreshViewer_menu_item"), ApiPlugin.getImageDescriptor("refresh_tree.gif")) {
			@Override
			public boolean isEnabled() {
				return activeEditorPart instanceof IViewerProvider;
			}

			@Override
			public void run() {
				if (activeEditorPart instanceof IViewerProvider) {
					Viewer viewer = ((IViewerProvider)activeEditorPart).getViewer();
					if (viewer != null) {
						viewer.refresh();
					}
				}
			}
		};
		
	protected IAction openAsXmlAction = 
		new Action("Open As XML ...", ApiPlugin.getImageDescriptor("open_as.gif")) {
			@Override
			public boolean isEnabled() {
				return activeEditorPart instanceof IViewerProvider;
			}
	
			@Override
			public void run() {
				if (!(activeEditorPart instanceof IViewerProvider)) {
					return;
				}
				File lInputFile = getInputFile();
				IFile lWorkspaceFile = getWorkspaceFile(lInputFile);
				try {
					IFile lFile = ((IFolder)lWorkspaceFile.getParent()).getFile("dummy-test.xml");
					IEditorDescriptor lEditorDescriptor = IDE.getDefaultEditor(lFile);
					WorkbenchPage lPage = (WorkbenchPage) activeEditorPart.getSite().getPage();
	        		lPage.openEditorFromDescriptor(new FileEditorInput(lWorkspaceFile), lEditorDescriptor, true, null);
				} catch (PartInitException e) {
					throw new RuntimeException("can't open editor", e);
				}
			}
		};
		
	protected IAction saveAsXmlAction = 
		new Action("Save As XML ...", ApiPlugin.getImageDescriptor("save_as.gif")) {
			@Override
			public boolean isEnabled() {
				return activeEditorPart instanceof IViewerProvider;
			}
	
			@Override
			public void run() {
				if (!(activeEditorPart instanceof IViewerProvider)) {
					return;
				}
				Resource.Factory lResourceFactory = new ModelResourceFactory();
				saveApiAs("Save As XML", "api", lResourceFactory);
			}
		};
	
	protected IAction saveAsXmiAction = 
		new Action("Save As XMI ...", ApiPlugin.getImageDescriptor("save_as.gif")) {
			@Override
			public boolean isEnabled() {
				return activeEditorPart instanceof IViewerProvider;
			}
	
			@Override
			public void run() {
				if (!(activeEditorPart instanceof IViewerProvider)) {
					return;
				}
				Resource.Factory lResourceFactory = new XMIResourceFactoryImpl();
				saveApiAs("Save As XMI", "api-xmi", lResourceFactory);
			}
		};

	protected IAction saveAsUmlAction = 
		new Action("Save As UML ...", ApiPlugin.getImageDescriptor("save_as.gif")) {
			@Override
			public boolean isEnabled() {
				return activeEditorPart instanceof IViewerProvider;
			}
	
			@Override
			public void run() {
				if (!(activeEditorPart instanceof IViewerProvider)) {
					return;
				}
				EPackage lPackage = getModelPackage();
				if (lPackage == null) {
					return;
				}
				Resource.Factory lResourceFactory = UMLResource.Factory.INSTANCE;
				Resource resource = lResourceFactory.createResource(null);

				// NOTE: the model needs to be part of a resource otherwise stereotype applications won't be saved
				Model lModel = UMLFactory.eINSTANCE.createModel();
				resource.getContents().add(lModel);
				
				Profile lProfile = findProfile(lPackage);
				Api2UmlTrafo lTrafo = new Api2UmlTrafo(lModel, lProfile);
				lTrafo.transform(lPackage, lModel);
				
				saveAs("Save As UML", "uml", resource, lResourceFactory);
			}

			private Profile findProfile(EPackage lPackage) {
				ResourceSet resourceSet = lPackage.eResource().getResourceSet();
				for (Resource resource : resourceSet.getResources()) {
					if (resource.getContents().isEmpty()) {
						continue;
					}
					EObject eObject = resource.getContents().get(0);
					if (eObject instanceof Profile) {
						return (Profile) eObject;
					}
				}
				return null;
			}
		};

	private void saveApiAs(String pTitle, String pExtension, Resource.Factory pResourceFactory) {
		EPackage lPackage = getModelPackage();
		if (lPackage == null) {
			return;
		}
		Resource lOldResource = lPackage.eResource();
		try {
			saveAs(pTitle, pExtension, lPackage, pResourceFactory);
		} finally {
			lOldResource.getContents().add(lPackage);			
		}
	}
	
	private void saveAs(String pTitle, String pExtension, Object pContent, Resource.Factory pResourceFactory) {
		File lInputFile = getInputFile();

		String lFilterPath = lInputFile.getParent();
		String lExtension = "*";
		String lFileName = lInputFile.getName() + "." + pExtension;

		Shell lShell = activeEditorPart.getSite().getShell();
		FileDialog lFileDialog = new FileDialog(lShell, SWT.SAVE);
		lFileDialog.setText(pTitle);
		lFileDialog.setFilterExtensions(new String[] { "*." + lExtension });
		lFileDialog.setFileName(lFileName);
		lFileDialog.setOverwrite(true);
		lFileDialog.setFilterPath(lFilterPath);

		String lOutputFile = lFileDialog.open();
		if (lOutputFile == null || lOutputFile.isEmpty()) {
			return;
		}
		try {
			URI lUri = URI.createFileURI(lOutputFile);
			Resource lResource = pResourceFactory.createResource(lUri);
			if (pContent instanceof Resource) {				
				lResource.getContents().addAll(((Resource)pContent).getContents());
			} else {
				lResource.getContents().add((EObject)pContent);
			}
			lResource.save(null);
			
			IFile lWorkspaceFile = getWorkspaceFile(new File(lOutputFile));
			refreshFolder(lWorkspaceFile.getParent());
		} catch (Exception e) {
			RuntimeException lRuntimeException = new RuntimeException("can't write file '" + lOutputFile + "'", e);
			PrintStream lOut = ConsoleUtil.getConsoleOut(activeEditorPart.getSite().getPage());
			lRuntimeException.printStackTrace(lOut);
			throw lRuntimeException;
		}
	}	
	
	private EPackage getModelPackage() {
		IStructuredSelection lSelection = (IStructuredSelection) selectionProvider.getSelection();
		Object lElement = lSelection.getFirstElement();
		if (lElement == null) {
			return null;
		}
		Resource lResource;
		if (lElement instanceof EModelElement) {
			lResource = ((EModelElement) lElement).eResource();
		} else if (lElement instanceof Resource) {
			lResource = (Resource)lElement;
		} else {
			return null;
		}

		EList<EObject> lContents = lResource.getContents();
		if (lContents.isEmpty()) {
			return null;
		}
		EObject lObject = lContents.get(0);
		if (lObject instanceof EPackage) {
			return (EPackage) lObject;
		}
		return null;
	}

	protected IAction generateApiAction = new Action("Generate Api", ApiPlugin.getImageDescriptor("generate.gif")) {
		@Override
		public boolean isEnabled() {
			return activeEditorPart instanceof IViewerProvider;
		}

		@Override
		public void run() {
			if (!(activeEditorPart instanceof IViewerProvider)) {
				return;
			}
			PrintStream lOut = ConsoleUtil.getConsoleOut(activeEditorPart.getSite().getPage());
			String lPrefix = "api-generator";
			lOut.println();
			lOut.println(lPrefix + ": This is Api-Generator ....");
			try {
//				File lInputFile = getInputFile();
//				IFolder lOutputFolder = getOutputFolder(lInputFile);
//				File lOutputDir = lOutputFolder.getLocation().toFile();

			// TODO:	ApiGenerator.generateApi(lInputFile, lOutputDir, lPrefix, lOut);
//				lOut.println(lPrefix + ": refreshing ...");
//				refreshFolder(lOutputFolder);
				lOut.println(lPrefix + ": done.");
			} catch (Exception e) {
				lOut.println(lPrefix + ": caught exception '" + e.getMessage() + "'");
				e.printStackTrace(lOut);
			}
		}
	};

	private File getInputFile() {
		IPathEditorInput lEditorInput = (IPathEditorInput) activeEditorPart.getEditorInput();
		IPath lPath = lEditorInput.getPath();
		File lInputFile = lPath.toFile();
		return lInputFile;
	}
	
	private void refreshFolder(IContainer pContainer) {
		IProgressMonitor lProgressMonitor = (IProgressMonitor) activeEditorPart.getSite().getService(IProgressMonitor.class);
		try {
			pContainer.refreshLocal(IResource.DEPTH_INFINITE, lProgressMonitor);
		} catch (CoreException e) {
			throw new RuntimeException("error while refreshing folder '" + pContainer + "'", e); 
		}
	}

	@SuppressWarnings("unused")
	private IFolder getOutputFolder(File pInputFile) {
		IWorkspaceRoot lRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile[] lFiles = lRoot.findFilesForLocationURI(pInputFile.toURI());
		if (lFiles.length == 0) {
			return null;
		}
		IFile lFile = lFiles[0];
		IProject lProject = lFile.getProject();
		IFolder lFolder = lProject.getFolder("/mds/generated");
		return lFolder;
	}

	private IFile getWorkspaceFile(File pFile) {
		IWorkspaceRoot lRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile[] lFiles = lRoot.findFilesForLocationURI(pFile.toURI());
		if (lFiles.length == 0) {
			return null;
		}
		IFile lFile = lFiles[0];
		return lFile;
	}

	/**
	 * This will contain one {@link org.eclipse.emf.edit.ui.action.CreateChildAction} corresponding to each descriptor
	 * generated for the current selection by the item provider.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<IAction> createChildActions;

	/**
	 * This is the menu manager into which menu contribution items should be added for CreateChild actions.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IMenuManager createChildMenuManager;

	/**
	 * This will contain one {@link org.eclipse.emf.edit.ui.action.CreateSiblingAction} corresponding to each descriptor
	 * generated for the current selection by the item provider.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<IAction> createSiblingActions;

	/**
	 * This is the menu manager into which menu contribution items should be added for CreateSibling actions.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IMenuManager createSiblingMenuManager;

	/**
	 * This creates an instance of the contributor.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApiActionBarContributor() {
		super(ADDITIONS_LAST_STYLE);
		loadResourceAction = new LoadResourceAction();
		validateAction = new ValidateAction();
		controlAction = new ControlAction();
	}

	/**
	 * This adds Separators for editor additions to the tool bar.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(new Separator("api-settings"));
		toolBarManager.add(new Separator("api-additions"));
		// TODO: enable actions
//		toolBarManager.insertAfter("api-additions", generateApiAction);
//		toolBarManager.insertAfter("api-additions", openAsXmlAction);
		toolBarManager.insertAfter("api-additions", showPropertiesViewAction);
		toolBarManager.insertAfter("api-additions", refreshViewerAction);
		toolBarManager.insertAfter("api-additions", saveAsUmlAction);
	}

	/**
	 * This adds to the menu bar a menu and some separators for editor additions,
	 * as well as the sub-menus for object creation items.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void contributeToMenu(IMenuManager menuManager) {
		super.contributeToMenu(menuManager);

		IMenuManager submenuManager = new MenuManager(ApiPlugin.INSTANCE.getString("_UI_ApiEditor_menu"), "de.generia.tools.model.apiMenuID");
		menuManager.insertAfter("additions", submenuManager);
		submenuManager.add(new Separator("settings"));
		submenuManager.add(new Separator("actions"));
		submenuManager.add(new Separator("additions"));
		submenuManager.add(new Separator("additions-end"));

		// Prepare for CreateChild item addition or removal.
		//
		createChildMenuManager = new MenuManager(ApiPlugin.INSTANCE.getString("_UI_CreateChild_menu_item"));
		submenuManager.insertBefore("additions", createChildMenuManager);

		// Prepare for CreateSibling item addition or removal.
		//
		createSiblingMenuManager = new MenuManager(ApiPlugin.INSTANCE.getString("_UI_CreateSibling_menu_item"));
		submenuManager.insertBefore("additions", createSiblingMenuManager);

		// Force an update because Eclipse hides empty menus now.
		//
		submenuManager.addMenuListener
			(new IMenuListener() {
				 public void menuAboutToShow(IMenuManager menuManager) {
					 menuManager.updateAll(true);
				 }
			 });

		addGlobalActions(submenuManager);
	}

	/**
	 * When the active editor changes, this remembers the change and registers with it as a selection provider.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);
		activeEditorPart = part;

		// Switch to the new selection provider.
		//
		if (selectionProvider != null) {
			selectionProvider.removeSelectionChangedListener(this);
		}
		if (part == null) {
			selectionProvider = null;
		}
		else {
			selectionProvider = part.getSite().getSelectionProvider();
			selectionProvider.addSelectionChangedListener(this);

			// Fake a selection changed event to update the menus.
			//
			if (selectionProvider.getSelection() != null) {
				selectionChanged(new SelectionChangedEvent(selectionProvider, selectionProvider.getSelection()));
			}
		}
		
		saveAsUmlAction.setEnabled(saveAsUmlAction.isEnabled());
		refreshViewerAction.setEnabled(refreshViewerAction.isEnabled());		
		showPropertiesViewAction.setEnabled(showPropertiesViewAction.isEnabled());
	}

	/**
	 * This implements {@link org.eclipse.jface.viewers.ISelectionChangedListener},
	 * handling {@link org.eclipse.jface.viewers.SelectionChangedEvent}s by querying for the children and siblings
	 * that can be added to the selected object and updating the menus accordingly.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		// Remove any menu items for old selection.
		//
		if (createChildMenuManager != null) {
			depopulateManager(createChildMenuManager, createChildActions);
		}
		if (createSiblingMenuManager != null) {
			depopulateManager(createSiblingMenuManager, createSiblingActions);
		}

		// Query the new selection for appropriate new child/sibling descriptors
		//
		Collection<?> newChildDescriptors = null;
		Collection<?> newSiblingDescriptors = null;

		ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection && ((IStructuredSelection)selection).size() == 1) {
			Object object = ((IStructuredSelection)selection).getFirstElement();

			EditingDomain domain = ((IEditingDomainProvider)activeEditorPart).getEditingDomain();

			newChildDescriptors = domain.getNewChildDescriptors(object, null);
			newSiblingDescriptors = domain.getNewChildDescriptors(null, object);
		}

		// Generate actions for selection; populate and redraw the menus.
		//
		createChildActions = generateCreateChildActions(newChildDescriptors, selection);
		createSiblingActions = generateCreateSiblingActions(newSiblingDescriptors, selection);

		if (createChildMenuManager != null) {
			populateManager(createChildMenuManager, createChildActions, null);
			createChildMenuManager.update(true);
		}
		if (createSiblingMenuManager != null) {
			populateManager(createSiblingMenuManager, createSiblingActions, null);
			createSiblingMenuManager.update(true);
		}
	}

	/**
	 * This generates a {@link org.eclipse.emf.edit.ui.action.CreateChildAction} for each object in <code>descriptors</code>,
	 * and returns the collection of these actions.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<IAction> generateCreateChildActions(Collection<?> descriptors, ISelection selection) {
		Collection<IAction> actions = new ArrayList<IAction>();
		if (descriptors != null) {
			for (Object descriptor : descriptors) {
				actions.add(new CreateChildAction(activeEditorPart, selection, descriptor));
			}
		}
		return actions;
	}

	/**
	 * This generates a {@link org.eclipse.emf.edit.ui.action.CreateSiblingAction} for each object in <code>descriptors</code>,
	 * and returns the collection of these actions.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<IAction> generateCreateSiblingActions(Collection<?> descriptors, ISelection selection) {
		Collection<IAction> actions = new ArrayList<IAction>();
		if (descriptors != null) {
			for (Object descriptor : descriptors) {
				actions.add(new CreateSiblingAction(activeEditorPart, selection, descriptor));
			}
		}
		return actions;
	}

	/**
	 * This populates the specified <code>manager</code> with {@link org.eclipse.jface.action.ActionContributionItem}s
	 * based on the {@link org.eclipse.jface.action.IAction}s contained in the <code>actions</code> collection,
	 * by inserting them before the specified contribution item <code>contributionID</code>.
	 * If <code>contributionID</code> is <code>null</code>, they are simply added.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void populateManager(IContributionManager manager, Collection<? extends IAction> actions, String contributionID) {
		if (actions != null) {
			for (IAction action : actions) {
				if (contributionID != null) {
					manager.insertBefore(contributionID, action);
				}
				else {
					manager.add(action);
				}
			}
		}
	}
		
	/**
	 * This removes from the specified <code>manager</code> all {@link org.eclipse.jface.action.ActionContributionItem}s
	 * based on the {@link org.eclipse.jface.action.IAction}s contained in the <code>actions</code> collection.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void depopulateManager(IContributionManager manager, Collection<? extends IAction> actions) {
		if (actions != null) {
			IContributionItem[] items = manager.getItems();
			for (int i = 0; i < items.length; i++) {
				// Look into SubContributionItems
				//
				IContributionItem contributionItem = items[i];
				while (contributionItem instanceof SubContributionItem) {
					contributionItem = ((SubContributionItem)contributionItem).getInnerItem();
				}

				// Delete the ActionContributionItems with matching action.
				//
				if (contributionItem instanceof ActionContributionItem) {
					IAction action = ((ActionContributionItem)contributionItem).getAction();
					if (actions.contains(action)) {
						manager.remove(contributionItem);
					}
				}
			}
		}
	}

	/**
	 * This populates the pop-up menu before it appears.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void menuAboutToShow(IMenuManager menuManager) {
		super.menuAboutToShow(menuManager);
		MenuManager submenuManager = null;

		submenuManager = new MenuManager(ApiPlugin.INSTANCE.getString("_UI_CreateChild_menu_item"));
		populateManager(submenuManager, createChildActions, null);
		menuManager.insertBefore("edit", submenuManager);

		submenuManager = new MenuManager(ApiPlugin.INSTANCE.getString("_UI_CreateSibling_menu_item"));
		populateManager(submenuManager, createSiblingActions, null);
		menuManager.insertBefore("edit", submenuManager);
	}

	/**
	 * This inserts global actions before the "additions-end" separator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	protected void addGlobalActions(IMenuManager menuManager) {
		menuManager.insertAfter("additions-end", new Separator("ui-actions"));
		menuManager.insertAfter("ui-actions", showPropertiesViewAction);

		saveAsUmlAction.setEnabled(saveAsUmlAction.isEnabled());
		refreshViewerAction.setEnabled(refreshViewerAction.isEnabled());		
		showPropertiesViewAction.setEnabled(showPropertiesViewAction.isEnabled());
		menuManager.insertAfter("ui-actions", refreshViewerAction);
// TODO: enable actions
//		menuManager.insertAfter("ui-actions", saveAsXmiAction);
//		menuManager.insertAfter("ui-actions", saveAsXmlAction);
		menuManager.insertAfter("ui-actions", saveAsUmlAction);
//		menuManager.insertAfter("ui-actions", openAsXmlAction);
//		menuManager.insertAfter("ui-actions", generateApiAction);

		super.addGlobalActions(menuManager);
	}

	/**
	 * This ensures that a delete action will clean up all references to deleted objects.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean removeAllReferencesOnDelete() {
		return true;
	}

}