package de.generia.tools.model.api.generator.java;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
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
	private boolean renderPojos;

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

    	Logger log = LoggerFactory.getLogger(ApiGenerator.class);
    	log.info("starting model generator via main.");
		ApiGenerator lGenerator = new ApiGenerator();
		//File lRoot = new File("/Users/qxn7720/home/prj/bmw/ds2/ws-ms/api-tools-generator");
		File lRoot = new File("/Users/alex/home/prj/bmw/ds2/ws-ms/apitools/generator/java");
		URL lInputFile = new URL("file:///" + lRoot + "/tst/companymgmt.api");
		//URL lInputFile = new URL("file:///" + lRoot + "/tst/productdata.v0.9.api");
		//URL lInputFile = new URL("file:////Users/alex/home/dev/ws-api/apitools/model/src/test/resources/de/generia/tools/model/api/runtime/companymgmt.api");
		File lWorkspaceRoot = lRoot;
		File lOutputDir = new File(lWorkspaceRoot, "/tst/gen");
		//File lOutputDir = new File("/Users/alex/home/dev/ws-api/apitools/model/src/test/java");
		//File lOutputDir = new File(lRoot, "src-gen");
		lGenerator.setInputUrl(lInputFile);
		lGenerator.setRenderPojos(false);
		lGenerator.setJavaOutputDir(lOutputDir);
		lGenerator.setModelPackageRoot("de.generia.tools.model.api.runtime.typed.gen");
		lGenerator.setProject(new Project());
		lGenerator.getProject().addBuildListener(new ApiBuildListener(System.out, "apitools-java-generator"));
		lGenerator.execute();
   }

    
    public ApiGenerator() {
    	
	}
    
	@Override
    public void execute() throws BuildException {
	    try {
	    	log("This is apitools-java-generator ...'");
	    	log("- renderPojos='" + renderPojos + "'");
	    	log("- renderAnnotations='" + renderAnnotations + "'");
	    	log("- modelPackageRoot='" + mModelPackageRoot + "'");
	    	log("- javaOutputDir='" + mJavaOutputDir + "'");
	    	log("- reading   '" + mInputUrl.toExternalForm() + "' ...");
			URI lUri = URI.create(mInputUrl.toExternalForm());
			EPackage lPackage = loadPackage(lUri.toURL().openStream());			
			render(lPackage);
	    	log("done.'");
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
		JavaPackage lJavaPackage;
		if (renderPojos) {
			lJavaPackage = new PojoPackage(this, pPackage);
		} else {
			lJavaPackage = new JavaPackage(this, pPackage); 
		}
		lJavaPackage.render();
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
	
	public boolean isRenderPojos() {
		return renderPojos;
	}

	public void setRenderPojos(boolean renderPojos) {
		this.renderPojos = renderPojos;
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

	public List<?> filterList(List<? extends Object> pList, ContentFilter pFilter) {
		List<Object> lFilteredList = new ArrayList<Object>();
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
