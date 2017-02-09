package com.kardass.jsmatep.common;

public final class StringUtil {

    private StringUtil() {
    }

    /**
     * Checks whether a string is null or it's length (untrimmed) equals 0.
     * 
     * @param string
     * @return
     */
    public static boolean isEmpty(final String string) {
        return string == null || string.length() == 0;
    }
    
    /**
     * Checks whether a string is not null and it's length (untrimmed) not equals 0.
     * 
     * @param string
     * @return
     */
    public static boolean isNotEmpty(final String string) {
        return !isEmpty(string);
    }
    
    /**
     * Checks whether a string is null or it's length (trimmed) equals 0.
     * 
     * @param string
     * @return true => empty, false => not empty
     */
    public static boolean isEmptyWithTrim(final String string) {
        return isEmpty(string) || string.trim().length() == 0;
    }
    
    /**
     * Checks whether a string is not null and it's length (trimmed) not equals 0.
     * 
     * @param string
     * @return
     */
    public static boolean isNotEmptyWithTrim(final String string) {
        return !isEmptyWithTrim(string);
    }

}
