package de.generia.tools.model.api.generator;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;

import de.generia.tools.model.api.generator.java.ApiGenerator;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE, threadSafe = true)
public class ApiJavaGeneratorMojo extends AbstractMojo {

    @Parameter(required=true)
    private File apiInputFile;

    @Parameter(required=true)
    private File javaOutputDir;

    @Parameter
    private boolean renderAnnotations = false;

    @Parameter
    private boolean renderPojos = false;

    @Parameter
    private String modelPackageRoot;

	public void execute() throws MojoExecutionException, MojoFailureException {
		
		URL inputUrl;
		try {
			inputUrl = apiInputFile.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new MojoExecutionException("can't create input-url for file '" + apiInputFile + "'");
		}

		ApiGenerator generator = new ApiGenerator();			
		generator.setInputUrl(inputUrl);
		generator.setJavaOutputDir(javaOutputDir);
		generator.setRenderPojos(renderPojos);
		generator.setRenderAnnotations(renderAnnotations);
		generator.setModelPackageRoot(modelPackageRoot);
		generator.setProject(new Project());
		generator.getProject().addBuildListener(new LogBuildListener(getLog(), "apitools-java-generator"));
		generator.execute();

	}
	
	private static class LogBuildListener implements BuildListener {
		private Log log;
		private String prefix;
		
		public LogBuildListener(Log log, String prefix) {
			this.log = log;
			this.prefix = prefix; 
		}
		
		public void buildFinished(BuildEvent buildEvent) {
			messageLogged(buildEvent);
		}
		
		public void buildStarted(BuildEvent buildEvent) {
			messageLogged(buildEvent);
		}
		
		public void targetFinished(BuildEvent buildEvent) {
			messageLogged(buildEvent);
		}

		public void targetStarted(BuildEvent buildEvent) {
			messageLogged(buildEvent);
		}
		
		public void taskFinished(BuildEvent buildEvent) {
			messageLogged(buildEvent);
		}

		public void taskStarted(BuildEvent buildEvent) {
			messageLogged(buildEvent);
		}
		
		public void messageLogged(BuildEvent buildEvent) {
			int priority = buildEvent.getPriority();
			String message = prefix + ": " + buildEvent.getMessage();
			Throwable exception = buildEvent.getException();
			switch (priority) {
			case Project.MSG_ERR:
				if (exception != null) {
					log.error(message, exception);
				} else {
					log.error(message);					
				}
				break;

			case Project.MSG_WARN:
				if (exception != null) {
					log.warn(message, exception);
				} else {
					log.warn(message);					
				}
				break;

			case Project.MSG_INFO:
				if (exception != null) {
					log.info(message, exception);
				} else {
					log.info(message);					
				}
				break;

			default:
				if (exception != null) {
					log.debug(message, exception);
				} else {
					log.debug(message);					
				}
				break;
			}
		}
	}
}
