package com.kardass.jsmatep.parser.validation;

/**
 * Exception for treating illegal record length. 
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class IllegalRecordLengthException extends RuntimeException {
	
	private static final long serialVersionUID = -2310768856202279139L;
	
	/** The record the problem occurred. */
	private String causeRecord;
	
	/**
	 * @param causeRecord
	 * @param rowNumInFile
	 */
	public IllegalRecordLengthException(String message, String causeRecord) {
		super(message);
		this.causeRecord = causeRecord;
	}

	/**
	 * @return
	 */
	public String getCauseRecord() {
		return causeRecord;
	}

	@Override
	public String getMessage() {
		return super.getMessage() + " - record: " + causeRecord;
	}
	
	@Override
	public String toString() {
		return super.getMessage() + " - record: " + causeRecord;
	}
	
}
