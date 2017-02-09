package com.kardass.jsmatep.parser.reader;

import java.util.Arrays;
import java.util.List;

public class CsvBufferedRecordSplitter {

    private static final String TERMINATOR_ONE = "@";
    private static final String TERMINATOR_TWO = "~";

    private static String lastReadRecord = null;
    private static List<String> lastParsedValues;

    /**
     * Reads a field from a given CSV string.
     * 
     * @param aString
     *            CSV string
     * @param deliminator
     *            configured deliminator
     * @param fieldNo
     *            requested field no
     * @return field's string representation
     */
    public final String readField(final String aString, final String deliminator,
            final int fieldNo) {
        return splittStringToRecord(aString, deliminator).get(fieldNo);
    }

    /**
     * @param aString
     *            string to be splitted
     * @param deliminator
     *            string to use to split the given string
     * @return the splitted string as a list
     */
    public final List<String> splittStringToRecord(final String aString, final String deliminator) {
        if (!isSameRecord(aString)) {
            lastParsedValues = splittedRead(aString, deliminator);
            lastReadRecord = aString;
        }
        return lastParsedValues;
    }

    private boolean isSameRecord(final String aRecord) {
        return aRecord.equals(lastReadRecord);
    }

    /**
     * @return Last read record
     */
    public final String getLastReadRecord() {
        return lastReadRecord;
    }

    private static List<String> splittedRead(final String aString, final String delim) {
        final String terminator = delim.equals(TERMINATOR_ONE) ? TERMINATOR_TWO : TERMINATOR_ONE;
        final String parsableString = aString + delim + terminator;
        final String[] lineTwoSplit = parsableString.split(delim);
        final String[] finalResult = new String[lineTwoSplit.length - 1];
        System.arraycopy(lineTwoSplit, 0, finalResult, 0, lineTwoSplit.length - 1);
        return Arrays.asList(finalResult);
    }

}
