package de.generia.tools.model.api.generator.java;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.generator.java.base.JavaPackage;
import de.generia.tools.model.api.generator.java.pojo.PojoPackage;
import de.generia.tools.model.api.generator.trafo.TrafoComponent.ContentFilter;
import de.generia.tools.model.api.generator.trafo.TrafoGenerator;
import de.generia.tools.model.api.resource.stream.ModelInputStream;

public class ApiGenerator extends Task implements TrafoGenerator {
    //private final static Log log = LogFactory.getLog(ModelGenerator.class);
	private URL mInputUrl;
	private File mJavaOutputDir;
	private File mHbmOutputDir;
	private String mLibDatatypePackageRoot;
	private String mDatatypePackageRoot;
	private String mModelPackageRoot;
	private String mConfigPackageRoot;
	private String mEntityBaseClass;
	private String mDaoBaseInterface;
	private String mOppositeSupportCollection;
	private boolean renderAnnotations;

	public static class ApiBuildListener implements BuildListener {
		private PrintStream mPrintStream;
		private String mPrefix;
		
		public ApiBuildListener(PrintStream pPrintStream, String pPrefix) {
			mPrintStream = pPrintStream;
			mPrefix = pPrefix;
		}
		
		public void messageLogged(BuildEvent pBuildEvent) {
			mPrintStream.println(mPrefix + ": " + pBuildEvent.getMessage());
			Throwable lException = pBuildEvent.getException();
			if (lException != null) {
				mPrintStream.println(mPrefix + ": caught exception: " + lException.getMessage());
				lException.printStackTrace(mPrintStream);
			}
		}

		public void taskStarted(BuildEvent pBuildEvent) {
			messageLogged(pBuildEvent);
		}

		public void taskFinished(BuildEvent pBuildEvent) {
			messageLogged(pBuildEvent);
		}

		public void targetStarted(BuildEvent pBuildEvent) {
			messageLogged(pBuildEvent);
		}

		public void targetFinished(BuildEvent pBuildEvent) {
			messageLogged(pBuildEvent);
		}

		public void buildStarted(BuildEvent pBuildEvent) {
			messageLogged(pBuildEvent);
		}

		public void buildFinished(BuildEvent pBuildEvent) {
			messageLogged(pBuildEvent);
		}
	}
	
    public static void main(String[] args) throws Exception {
    	//System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Log4JLogger");
    	//Properties lProps = new Properties();
    	//File lFile = new File("c:/iap/ws/prototyp/cfg/de/mercur/iap/spdb/mylog.properties");
    	//lProps.load(ModelGenerator.class.getResourceAsStream("/de/mercur/iap/spdb/mylog.properties"));
    	//System.setProperty("log4j.configuration", "/de/mercur/iap/spdb/mylog.properties");
    	//System.setProperty("log4j.configuration", "de/mercur/iap/spdb/devlog.properties");
    	//System.setProperties(lProps);

    	Logger log = LoggerFactory.getLogger(ApiGenerator.class);
    	log.info("starting model generator via main.");
		ApiGenerator lGenerator = new ApiGenerator();
    	//File lRoot = new File("C:/home/dev/ws/modelweb");
		//File lRoot = new File("C:/home/prj/hvb/mds/ws/api-tools");
		File lRoot = new File("/Users/qxn7720/home/prj/bmw/ds2/ws-ms/api-tools-generator");
		//File lRoot = new File("C:/home/prj/mds/ws/default-saved/workspace");
    	//File lRoot = new File("C:/home/prj/mds/ws/default-saved/workspace/mds/source/plugins/api-tools");
		//URL lInputFile = new URL("file:///" + lRoot + "/mds/source/java/de/ugis/m0/bondipv/bondipv.api");
		URL lInputFile = new URL("file:///" + lRoot + "/tst/ext-api/mini-pd-v1.api");
		//URL lInputFile = new URL("file:///" + lRoot + "/tst/ext-api/bondipv.api.uml.api");
		//URL lInputFile = new URL("file:///" + lRoot + "/mds/source/java/de/ugis/m0/sandbox/companymgmt/companymgmt.api");
		//URL lInputFile = new URL("file:///" + lRoot + "/mds/source/java/de/ugis/m0/sandbox/modeltest/modeltest.api");
		//URL lInputFile = new URL("file:///" + lRoot + "/mds/source/java/de/ugis/m0/common/usermgmt/usermgmt.api");
		
		//URL lInputFile = new URL("file:///" + lRoot + "/tst/companymgmt.api");
		//File lWorkspaceRoot = new File("C:/home/prj/mds/ws/default-saved/workspace");
		File lWorkspaceRoot = lRoot;
		File lOutputDir = new File(lWorkspaceRoot, "/tst/ext-api/gen");
		//File lOutputDir = new File(lRoot, "src-gen");
		lGenerator.setInputUrl(lInputFile);

		lGenerator.setJavaOutputDir(lOutputDir);
//		String lSystemComponent = "bondipv";
//		lGenerator.setConfigPackageRoot("de.generia.tools." + lSystemComponent + ".backend");
//		lGenerator.setDatatypePackageRoot("de.generia.tools." + lSystemComponent + ".appcore");
//		lGenerator.setModelPackageRoot("de.generia.tools." + lSystemComponent + ".appcore");
//		lGenerator.setLibDatatypePackageRoot("de.generia.tools.appcore.basetypes");
		lGenerator.setEntityBaseClass("de.ugis.m0.common.model.persistence.jpa.AbstractEntity");
		lGenerator.setDaoBaseInterface("de.ugis.m0.common.backend.persistence.PersistenceDao");
		lGenerator.setOppositeSupportCollection("de.ugis.m0.common.model.impl.collections.OppositeSupportSet");
		lGenerator.setProject(new Project());
		lGenerator.getProject().addBuildListener(new ApiBuildListener(System.out, "Api-Generator"));
		lGenerator.execute();
   }
    
    public static void generateApi(File pInputFile, File pOutputDir, final String pPrefix, final PrintStream pPrintStream) {
		ApiGenerator lGenerator = new ApiGenerator();
		URL lInputUrl;
		try {
			lInputUrl = pInputFile.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new RuntimeException("can't create url from file '" + pInputFile + "'", e);
		}
		lGenerator.setEntityBaseClass("de.ugis.m0.common.model.persistence.jpa.AbstractEntity");
		lGenerator.setDaoBaseInterface("de.ugis.m0.common.backend.persistence.PersistenceDao");
		lGenerator.setOppositeSupportCollection("de.ugis.m0.common.model.impl.collections.OppositeSupportSet");
		lGenerator.setInputUrl(lInputUrl);
		lGenerator.setJavaOutputDir(pOutputDir);
		lGenerator.setProject(new Project());
		lGenerator.getProject().addBuildListener(new ApiBuildListener(pPrintStream, pPrefix));
		lGenerator.execute();
    }
    
    public ApiGenerator() {
    	
	}
    
	@Override
    public void execute() throws BuildException {
	    try {
	    	log("reading    '" + mInputUrl.toExternalForm() + "' ...");
			URI lUri = URI.create(mInputUrl.toExternalForm());
			EPackage lPackage = loadPackage(lUri.toURL().openStream());
	    
//			if (true) {
//				ModelResourceFactory lModelResourceFactory = new ModelResourceFactory();
//				Resource lResource = lModelResourceFactory.createResource(lUri);
//				lResource.getContents().add(lPackage);
//				lResource.save(System.out, null);
//			}
			
			render(lPackage);
		} catch (Throwable e) {
			throw new BuildException(e);
		}
    }

	private EPackage loadPackage(InputStream inputStream) {
		ModelInputStream apiStream = new ModelInputStream();
		EPackage pkg = apiStream.read(inputStream);
		return pkg;
	}

	public void render(EPackage pPackage) throws Exception {
		JavaPackage lJavaPackage = new JavaPackage(this, pPackage);
		lJavaPackage.render();

		PojoPackage lPojoPackage = new PojoPackage(this, pPackage);
		lPojoPackage.render();
	}
    
	public File getJavaOutputDir() {
		return mJavaOutputDir;
	}

	public void setJavaOutputDir(File pJavaOutputDir) {
		mJavaOutputDir = pJavaOutputDir;
	}

	public boolean isRenderAnnotations() {
		return renderAnnotations;
	}

	public void setRenderAnnotations(boolean renderAnnotations) {
		this.renderAnnotations = renderAnnotations;
	}
	
	public File getHbmOutputDir() {
		return mHbmOutputDir;
	}

	public void setHbmOutputDir(File pHbmOutputDir) {
		this.mHbmOutputDir = pHbmOutputDir;
	}

	public String getDatatypePackageRoot() {
		return mDatatypePackageRoot;
	}

	public void setDatatypePackageRoot(String pDatatypePackageRoot) {
		mDatatypePackageRoot = pDatatypePackageRoot;
	}

	public String getModelPackageRoot() {
		return mModelPackageRoot;
	}

	public void setModelPackageRoot(String pModelPackageRoot) {
		mModelPackageRoot = pModelPackageRoot;
	}

	public String getConfigPackageRoot() {
		return mConfigPackageRoot;
	}

	public void setConfigPackageRoot(String pConfigPackageRoot) {
		mConfigPackageRoot = pConfigPackageRoot;
	}

	@SuppressWarnings("unchecked")
	public List filterList(List<? extends Object> pList, ContentFilter pFilter) {
		List lFilteredList = new ArrayList();
		for (Object lObject : pList) {
			if (pFilter == null || pFilter.accept(lObject)) {
				lFilteredList.add(lObject);
			}
		}
		return lFilteredList;
	}

	public URL getInputUrl() {
		return mInputUrl;
	}

	public void setInputUrl(URL pInputUrl) {
		mInputUrl = pInputUrl;
	}

	public String getLibDatatypePackageRoot() {
		return mLibDatatypePackageRoot;
	}

	public void setLibDatatypePackageRoot(String pLibDatatypePackageRoot) {
		mLibDatatypePackageRoot = pLibDatatypePackageRoot;
	}

	public String getEntityBaseClass() {
		return mEntityBaseClass;
	}

	public void setEntityBaseClass(String pEntityBaseClass) {
		mEntityBaseClass = pEntityBaseClass;
	}

	public String getDaoBaseInterface() {
		return mDaoBaseInterface;
	}

	public void setDaoBaseInterface(String pDaoBaseInterface) {
		mDaoBaseInterface = pDaoBaseInterface;
	}

	public String getOppositeSupportCollection() {
		return mOppositeSupportCollection;
	}

	public void setOppositeSupportCollection(String pOppositeSupportCollection) {
		mOppositeSupportCollection = pOppositeSupportCollection;
	}
}
