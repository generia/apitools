package de.generia.tools.model.api.generator.table;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

public class PdhTableGeneratorTest {

	@Test
	public void test() throws Exception {
		String schemaFile = getFile("productdata.v0.9.3.api");
		String jsonInputFile = getFile("bmw-feed-de.json");
		File tmpDir = File.createTempFile("pdhTableGenerator", "Test.csv").getParentFile();
		File csvDir = new File(tmpDir, "pdhTableGenerator.csv");
		csvDir.mkdirs();
		String xlsOutputFile = csvDir.getAbsolutePath();
		String[] json2XlsArgs = {
			"json2xls",
			schemaFile,
			"ProductData",
			jsonInputFile,
			xlsOutputFile
		};
		TableGenerator.main(json2XlsArgs);

		String xlsInputFile = xlsOutputFile;
		String jsonOutputFile = File.createTempFile("pdhTableGenerator", "Test.json").getAbsolutePath();
		String[] xls2JsonArgs = {
			"xls2json",
			schemaFile,
			"ProductData",
			xlsInputFile,
			jsonOutputFile
		};
		TableGenerator.main(xls2JsonArgs);
		
		String expectedJson = readFile(jsonInputFile);
		String actualJson = readFile(jsonOutputFile);
		assertEquals(expectedJson, actualJson);
	}

	private String readFile(String file) throws IOException {
		StringWriter writer = new StringWriter();
		Reader reader = new FileReader(file);
		int c = reader.read();
		while (c != -1) {
			writer.write(c);
			c = reader.read();
		}
		reader.close();
		writer.flush();
		writer.close();
		return writer.toString();
	}

	private String getFile(String fileName) {
		URL resource = PdhTableGeneratorTest.class.getResource(fileName);
		File file;
		try {
			file = new File(resource.toURI());
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("can't access file '" + fileName + "'", e);
		}
		return file.getAbsolutePath();
	}

}
