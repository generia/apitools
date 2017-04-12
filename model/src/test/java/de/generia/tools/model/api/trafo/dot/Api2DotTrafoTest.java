package de.generia.tools.model.api.trafo.dot;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.resource.stream.ModelInputStream;
import de.generia.tools.model.api.runtime.EObject;

public class Api2DotTrafoTest {
    @Before
	public void setup() {
	}

    @Test
    public void test() {
		ModelInputStream apiStream = new ModelInputStream();
		InputStream inputStream = EObject.class.getResourceAsStream("companymgmt.api");
		EPackage api = apiStream.read(inputStream);
		assertNotNull(api);
    	
		Api2DotTrafo trafo = new Api2DotTrafo();
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		trafo.write(api, printWriter);
		printWriter.close();
		
		String dot = stringWriter.toString();
		assertNotNull(dot);
	}
}
