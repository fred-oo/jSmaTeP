package com.kardass.jsmatep.parser.adjust;

/**
 * Interface to adjust read records by given parameters in the XML config file.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public interface RecordAdjuster {

	/**
	 * Adjusts a record vs. it's configuration.
	 * 
	 * @param record
	 * @return
	 * 		The adjusted record string
	 */
	String adjustRecordVsConfig(String record);

}
