package com.kardass.jsmatep.codegenerator;

import java.io.File;
import java.io.IOException;

import com.kardass.jsmatep.common.UrlUtil;

/**
 * Utility for creating a directory structure depending a given java package within a given 
 * directory as a starting.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class PackageToRealFile {
	
	private static final char FILE_SEPARATOR = System.getProperty("file.separator").charAt(0);
	private static final String JAVA = ".java";

	/**
	 * Creates a directory structure for a given java package (within starting point (directory))
	 * @param destinationDirPath
	 * @param packageWithClass
	 * @return
	 * @throws IOException
	 */
	public static File createPackageStructureAndFile(String destinationDirPath, String packageWithClass) throws IOException {
		createPackageStructureAsPath(destinationDirPath, packageWithClass);
		String packageAsPath = UrlUtil.packageToRealPath(packageWithClass);
		String completePath = destinationDirPath + FILE_SEPARATOR + packageAsPath + JAVA;
		File destFile = new File(completePath);
		boolean isCreated = false;
		if (destFile.exists()) {
			throw new RuntimeException("File '" + destFile.getAbsolutePath() + "' already exists");
		} else {
			isCreated = destFile.createNewFile();
		}
		if (!isCreated || !destFile.exists()) {
			throw new RuntimeException("Cannot create file: " + destFile.getAbsolutePath());
		}
		return destFile;
	}

	/**
	 * @see {@link #createPackageStructureAndFile(String, String)}
	 * 
	 * @param destinationPath
	 * @param packageWithClass
	 * @return
	 * @throws IOException
	 */
	private static File createPackageStructureAsPath(String destinationPath, String packageWithClass) throws IOException {
		String cleanedPackage = cutClassName(packageWithClass);
		String packageAsPath = packageToPath(cleanedPackage);
		String relativePath = isEmpty(destinationPath) ? packageAsPath : (destinationPath + FILE_SEPARATOR + packageAsPath);
		File destFile = new File(relativePath);
		boolean dirCreated = false;
		if (destFile.exists()) {
			// TODO
//			throw new RuntimeException("Directory " + destFile.getAbsolutePath() + " already exists");
		} else {
			dirCreated = destFile.mkdirs();
		}
		if (!dirCreated || !destFile.exists()) {
			throw new RuntimeException("Cannot create directory: " + destFile.getAbsolutePath());
		}
		return destFile;
	}

	/**
	 * @param str
	 * @return
	 */
	private static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * @param packageName
	 * @return
	 */
	public static String packageToPath(String packageName) {
		return packageName.replace('.', FILE_SEPARATOR);
	}

	/**
	 * Cuts the class name from a given full qualified class name and return the package.
	 * 
	 * @param packageWithClass
	 * @return
	 */
	public static String cutClassName(String packageWithClass) {
		return packageWithClass.substring(0, packageWithClass.lastIndexOf("."));
	}
	
}
