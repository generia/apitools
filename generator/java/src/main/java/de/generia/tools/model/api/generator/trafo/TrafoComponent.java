package de.generia.tools.model.api.generator.trafo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ResourceNotFoundException;

public abstract class TrafoComponent {
	private Template mTemplate = null;
	protected TrafoGenerator mGenerator;
	private TrafoComponent mContext;
	private boolean mIndent = false;
	
	public void setIndent(boolean pIndent) {
		mIndent = pIndent;
	}

	public TrafoComponent(TrafoGenerator pGenerator, TrafoComponent pContext) {
		mGenerator = pGenerator;
		mContext = pContext;
	}
	
	private static class NullWriter extends Writer {

		@Override
		public void write(char[] pArg0, int pArg1, int pArg2) throws IOException {
			// just do nothing
		}

		@Override
		public void flush() throws IOException {
			// just do nothing
		}

		@Override
		public void close() throws IOException {
			// just do nothing
		}
	}
	
	private static class IndentWriter extends NullWriter {
		private static final String INDENT = "    ";
		private Writer mWriter;
		private String mIndent = INDENT;
		private boolean mStartNewline = true;
		
		public IndentWriter(Writer pWriter) {
			if (pWriter instanceof IndentWriter) {
				IndentWriter lIndentWriter = (IndentWriter)pWriter;
				mWriter = lIndentWriter.mWriter;
				mIndent = lIndentWriter.mIndent + INDENT;
			} else {
				mWriter = pWriter;
			}
		}
		
		@Override
		public void write(char[] pCharBuf, int pOffset, int pLength) throws IOException {
			String lBuffer = new String(pCharBuf, pOffset, pLength);
			if (mStartNewline) {
				lBuffer = mIndent + lBuffer;
				mStartNewline = false;
			}
			lBuffer = lBuffer.replace("\n", "\n" + mIndent);
			if (lBuffer.endsWith("\n" + mIndent)) {
				lBuffer = lBuffer.substring(0, lBuffer.length() - mIndent.length());
				mStartNewline = true;
			}
			mWriter.write(lBuffer.toCharArray(), 0, lBuffer.length());
		}
	}

	public File getFile() {
		return null;
	}
	
	public void render() throws Exception {
    	render(new ArrayList<Object>());
	}

	public void render(List<Object> pArgs) throws Exception {
		Writer lWriter = new NullWriter();
		VelocityContext lContext = new VelocityContext();

		Velocity.setProperty("resource.loader", "class");
		Velocity.setProperty("class.resource.loader.class", org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader.class.getName());
		Velocity.init();

        Template lTemplate = Velocity.getTemplate(TrafoComponent.class.getName().replace('.', '/') + ".vm");
    	lTemplate.merge(lContext, lWriter);
    	lContext.put("context", lContext);
    	lContext.put("writer", lWriter);

    	render(lContext, pArgs);
	}

	public void render(VelocityContext pContext) throws Exception {
		render(pContext, new ArrayList<Object>());
	}
	
	public void render(VelocityContext pContext, List<Object> pArgs) throws Exception {
		TrafoComponent lThis = (TrafoComponent) pContext.get("this");
		@SuppressWarnings("unchecked")
		List<Object> lSavedArgs = (List<Object>) pContext.get("args");
		pContext.put("this", this);
		pContext.put("args", pArgs);
		Template lTemplate = getTemplate();
		if (lTemplate == null) {
			throw new RuntimeException("no vm-template found for trafo-component '" + getClass().getName() + "'");
		}
		Writer lSavedWriter = (Writer) pContext.get("writer");
		Writer lWriter = getWriter(pContext);
		pContext.put("writer", lWriter);
		lTemplate.merge(pContext, lWriter);
		if (lSavedWriter != lWriter) {
			lWriter.flush();
			lWriter.close();
		}
		pContext.put("this", lThis);
		pContext.put("args", lSavedArgs);
		pContext.put("writer", lSavedWriter);
	}
	
	protected Writer getWriter(VelocityContext pContext) throws Exception {
		File lFile = getFile();
		
		if (lFile == null) {
			Writer lWriter = (Writer) pContext.get("writer");
			if (mIndent) {
				lWriter = new IndentWriter(lWriter);
			}
			return lWriter;
		}
		
		log("rendering '" + lFile + "' ...");
		lFile.getParentFile().mkdirs();
		Writer lWriter = new BufferedWriter(new FileWriter(lFile));
		return lWriter;
	}

	protected void log(String pMessage) {
		getGenerator().log(pMessage);
	}

	protected Template getTemplate() {
		if (mTemplate != null) {
			return mTemplate;
		}
		Class<?> lClass = getClass();
		while (!lClass.equals(TrafoComponent.class)) {
			try {
				String lResource = lClass.getName().replace('.', '/') + ".vm";
				mTemplate = Velocity.getTemplate(lResource);
				return mTemplate;
			} catch (ResourceNotFoundException e) {
				lClass = lClass.getSuperclass();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}
	
	public interface ContentFilter {
		public boolean accept(Object pContent);
	}
	
	@SuppressWarnings("unchecked")
	public List wrapList(Class pContentClass, Class pWrapperClass, Collection<? extends Object> pList) {
		List lResult = new ArrayList();
		try {
			Constructor lWrapper = pWrapperClass.getConstructor(TrafoGenerator.class, getClass(), pContentClass);
			for (Object lContent : pList) {
				if (pContentClass.isAssignableFrom(lContent.getClass())) {
					Object lWrapped = lWrapper.newInstance(new Object[]{mGenerator, this, lContent});
					lResult.add(lWrapped);
				}
			}
			return lResult;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	public TrafoGenerator getGenerator() {
		return mGenerator;
	}

	public TrafoComponent getContext() {
		return mContext;
	}
}
