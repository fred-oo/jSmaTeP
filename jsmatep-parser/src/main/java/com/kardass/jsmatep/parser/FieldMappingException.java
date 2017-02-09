package com.kardass.jsmatep.parser;

public class FieldMappingException extends Exception {

	private static final long serialVersionUID = -8531186733455286777L;
	
	/** The problem record */
	private final String causeField;

	/**
	 * @param causeRecord   The problem record
	 * @param t   The underlying (root) exception 
	 */
	public FieldMappingException(String causeRecord, Throwable t) {
		super(t);
		this.causeField = causeRecord;
	}

	public FieldMappingException(String causeRecord, String message) {
		super(message);
		this.causeField = causeRecord;
	}

	public String getCauseField() {
		return causeField;
	}

	
	@Override
	public String getMessage() {
		return "Error while parsing field: [" + causeField + "] " + super.getMessage();
	}
	
	@Override
	public String toString() {
		return "Error while parsing field: [" + causeField + "] " + super.toString();
	}
}
