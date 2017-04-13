package de.generia.tools.model.api.cmdline;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.resource.stream.ModelInputStream;
import de.generia.tools.model.api.trafo.dot.Api2DotTrafo;

public class ApiToolsCmd {

	private static final String API2DOT_TRAFO = "api2dot";

	public static void main(String[] args) {
		
		if (args.length != 3) {
			usage();
			return;
		}

		String trafo = args[0];
		String inputFile = args[1];
		String outputFile = args[2];
		
		ApiToolsCmd apiTrafoCmd = new ApiToolsCmd();
		if (trafo.equals(API2DOT_TRAFO)) {
			try {
				InputStream inputStream = new FileInputStream(inputFile);
				OutputStream outputStream = new FileOutputStream(outputFile);
				apiTrafoCmd.api2dot(inputStream, outputStream);
				inputStream.close();
				outputStream.close();
			} catch (Exception e) {
				System.err.println("error: " + e.getMessage());
			}
		} else {
			usage();
		}
	}

	private static void usage() {
		System.err.println("usage: apitools <trafo> <input-file> <output-file>");
		System.err.println("- trafo := 'api2dot'");
	}

	private void api2dot(InputStream inputStream, OutputStream outputStream) {
		ModelInputStream apiStream = new ModelInputStream();
		EPackage api = apiStream.read(inputStream);
		
		Api2DotTrafo trafo = new Api2DotTrafo();
		Writer outputWriter = new OutputStreamWriter(outputStream);
		PrintWriter printWriter = new PrintWriter(outputWriter);
		trafo.write(api, printWriter);
		printWriter.flush();
	}
}
