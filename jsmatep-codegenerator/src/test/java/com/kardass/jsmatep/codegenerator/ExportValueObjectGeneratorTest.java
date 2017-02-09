package com.kardass.jsmatep.codegenerator;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.kardass.jsmatep.common.UrlUtil;
import com.kardass.jsmatep.common.io.ContentReader;
import com.kardass.jsmatep.common.io.FileUtil;

public class ExportValueObjectGeneratorTest {

	private static final String genDestPath = "generated-junit";
	
	@Test
	public void testGenerator() throws Exception {
        final String configFileName = UrlUtil.packageToRealPath(getClass()) + "/Export-FixedLength-Config.xml";
        final String fileNameExpectedResult = UrlUtil.packageToRealPath(getClass())
                + "/Export-ValueObject-ExpectedResult.txt";
		final String expectedResult = 
			cutComment(ContentReader.readContents(fileNameExpectedResult));
		File javaFile = null;
		try {
			javaFile = ExportValueObjectGenerator.generateValueObject(configFileName, genDestPath);	
			List<String> contentList = ContentReader.readContents(javaFile);
			String javaFileContents = cutComment(contentList);
			assertEquals("ValueObjectGenerator generated invalid ValueObject", 
					expectedResult, javaFileContents);
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
