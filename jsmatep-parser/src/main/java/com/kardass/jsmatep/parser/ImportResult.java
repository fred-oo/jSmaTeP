package com.kardass.jsmatep.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the import processor's result.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class ImportResult {

	private final List<String> errorRecords;

	private final List<String> skippedRecords;
	
	private int countMappedValueObjects = 0;


	
	ImportResult() {
		this.errorRecords = new ArrayList<String>();
		this.skippedRecords = new ArrayList<String>();
	}

	public void incrementCountMappedValueObject() {
		++countMappedValueObjects;
	}

	public int getCountMappedValueObjects() {
		return countMappedValueObjects;
	}

	public void addErrorRecord(String record) {
		errorRecords.add(record);
	}

	public List<String> getErrorRecords() {
		return errorRecords;
	}
    
    /**
     * Use {@link #getCountErrorRecords()}
     * 
     * @return Quantity of error records
     */
	@Deprecated
    public int getErrorRecordsCount() {
        return getCountErrorRecords();
    }

    public int getCountErrorRecords() {
        return errorRecords.size();
    }

	public void addSkippedRecord(String record) {
		skippedRecords.add(record);
	}

	public List<String> getSkippedRecords() {
		return skippedRecords;
	}

    /**
     * Use {@link #getCountSkippedRecords()}
     * 
     * @return Quantity of skipped records
     */
    @Deprecated
    public int getSkippedRecordsCount() {
        return getCountSkippedRecords();
    }
    
    public int getCountSkippedRecords() {
        return skippedRecords.size();
    }
    
}
