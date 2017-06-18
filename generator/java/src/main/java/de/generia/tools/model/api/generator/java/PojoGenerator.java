package de.generia.tools.model.api.generator.java;

import java.io.File;
import java.net.URL;

import org.apache.tools.ant.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.generator.java.pojo.PojoPackage;

public class PojoGenerator extends ApiGenerator {
    //private final static Log log = LogFactory.getLog(ModelGenerator.class);
		
    public static void main(String[] args) throws Exception {

    	Logger log = LoggerFactory.getLogger(PojoGenerator.class);
    	log.info("starting model generator via main.");
		PojoGenerator lGenerator = new PojoGenerator();
		//File lRoot = new File("/Users/qxn7720/home/prj/bmw/ds2/ws-ms/api-tools-generator");
		File lRoot = new File("/Users/alex/home/dev/ws-api/apitools/generator/java");
		URL lInputFile = new URL("file:///" + lRoot + "/tst/companymgmt.v2.api");
		//URL lInputFile = new URL("file:///" + lRoot + "/tst/productdata.v0.9.api");
		
		File lWorkspaceRoot = lRoot;
		File lOutputDir = new File(lWorkspaceRoot, "/tst/gen");
		//File lOutputDir = new File(lRoot, "src-gen");
		lGenerator.setInputUrl(lInputFile);

		lGenerator.setJavaOutputDir(lOutputDir);
		lGenerator.setModelPackageRoot("de.generia.companymgmt.v2");
		lGenerator.setRenderAnnotations(true);
		lGenerator.setProject(new Project());
		lGenerator.getProject().addBuildListener(new ApiBuildListener(System.out, "Pojo-Generator"));
		lGenerator.execute();
   }

    
    public PojoGenerator() {
    	super();
	}


	public void render(EPackage pPackage) throws Exception {
		PojoPackage lPojoPackage = new PojoPackage(this, pPackage);
		lPojoPackage.render();
	}
}
