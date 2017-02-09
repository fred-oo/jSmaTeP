package com.kardass.jsmatep.codegenerator;

import static com.kardass.jsmatep.common.UrlUtil.packageToRealPath;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.kardass.jsmatep.common.io.ContentReader;
import com.kardass.jsmatep.common.io.FileUtil;

/**
 * Test case for validating ValueObject creation.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 */
public class ImportValueObjectGeneratorTest {
	
	private static final String genDestPath = "generated-junit";
	
	@Test
	public void testGenerator() throws Exception {
        final String configFileName = packageToRealPath(getClass()) + "/Import-Config-Fixed-Length.xml";
        final String fileNameExpectedResult = packageToRealPath(getClass()) + "/Import-ValueObject-ExpectedResult.txt";
		final String expectedResult = ContentReader.readContentsAsString(fileNameExpectedResult);
		File javaFile = null;
		try {
			javaFile = ImportValueObjectGenerator.generateValueObject(configFileName, genDestPath);	
			List<String> contentList = ContentReader.readContents(javaFile);
			String javaFileContents = cutComment(contentList);
			assertEquals("ValueObjectGenerator generated invalid ValueObject", expectedResult, javaFileContents);
		} finally {
			FileUtil.deleteDirRecursivly(genDestPath);
		}
	}

	private String cutComment(List<String> javaFileContentsAsList) {
		final String lineSeparator = System.getProperty("line.separator");
		final StringBuilder builder = new StringBuilder();
		final int startIndexNoComment = 3;
		// first 3 lines hold the comment
		for (int i = startIndexNoComment; i < javaFileContentsAsList.size(); i++) {
			builder.append(javaFileContentsAsList.get(i)).append(lineSeparator);
		}
		return builder.toString();
	}

}
