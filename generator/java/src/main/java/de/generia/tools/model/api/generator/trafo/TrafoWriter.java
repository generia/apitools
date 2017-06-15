package de.generia.tools.model.api.generator.trafo;

import java.io.IOException;
import java.io.Writer;

public class TrafoWriter extends Writer {
	public Writer mWriter;
	
	public TrafoWriter(Writer pWriter) {
		mWriter = pWriter;
	}
	
	@Override
	public void write(char[] pArg0, int pArg1, int pArg2) throws IOException {
		//System.out.println("write: " + pArg0.toString());
		mWriter.write(pArg0, pArg1, pArg2);
	}

	@Override
	public void flush() throws IOException {
		mWriter.flush();
	}

	@Override
	public void close() throws IOException {
		mWriter.close();
	}

	public void indent() {
		
	}
	
	public void outdent() {
		
	}
}
