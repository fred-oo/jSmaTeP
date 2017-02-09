package com.kardass.jsmatep.common;

import java.net.URL;

/**
 * Helper class for dealing with URL and file/path.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public final class UrlUtil {

    public static final char FILE_SEPARATOR = System.getProperty("file.separator").charAt(0);

    private UrlUtil() {
    }

	/**
	 * Returns the class' full qualified name as a path representation.
	 * @param clazz
	 * @return
	 */
	public static String packageToRealPath(Class<?> clazz) {
		return packageToRealPath(clazz.getPackage().getName());
	}
	
	/**
	 * Returns the className a path representation.
	 * 
	 * @param className
	 * @return
	 */
	public static String packageToRealPath(String className) {
		return className.replace('.', '/');
	}
	
	/**
	 * Returns a URL for the given relative path using the current threads classloader.
	 * 
	 * @param relativePath
	 * @return
	 */
	public static URL getResourceURL(String relativePath) {
		return Thread.currentThread().getContextClassLoader().getResource(relativePath);
	}

	/**
	 * Returns the complete path by using {@link #getResourceURL}
	 * @param relativePath
	 * @return
	 */
	public static String getResourceCompletePath(String relativePath) {
		final URL aURL = getResourceURL(relativePath);
		return aURL.getPath();
	}
}
