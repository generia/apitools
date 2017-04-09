package de.generia.tools.model.api.resource.stream;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Test;

import de.generia.tools.model.api.EModelElement;
import de.generia.tools.model.api.EPackage;
import de.generia.tools.model.api.resource.stream.ModelInputStream;

public class ModelInputStreamTest {

	@Test
	public void test() {
		ModelInputStream apiStream = new ModelInputStream();
		InputStream inputStream = EModelElement.class.getResourceAsStream("api.api");
		EPackage api = apiStream.read(inputStream);
		assertNotNull(api);
	}

}
