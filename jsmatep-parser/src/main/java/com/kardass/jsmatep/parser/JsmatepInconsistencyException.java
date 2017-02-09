package com.kardass.jsmatep.parser;

/**
 * Exception for problems within jSmaTeP configuration / inconsistency.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class JsmatepInconsistencyException extends RuntimeException {
	
	private static final long serialVersionUID = -2310768856202279139L;

	/** The problem record */
	private String causeRecord;

	/**
	 * @param causeRecord   The problem record
	 * @param t   The underlying (root) exception 
	 */
	public JsmatepInconsistencyException(String causeRecord, Throwable t) {
		super(t);
		this.causeRecord = causeRecord;
	}

	public JsmatepInconsistencyException(String causeRecord, String message) {
		super(message);
		this.causeRecord = causeRecord;
	}

	/** Default constructor */
	public JsmatepInconsistencyException() {
		super();
	}

	/**
	 * @param message
	 */
	public JsmatepInconsistencyException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public JsmatepInconsistencyException(Throwable cause) {
		super(cause);
	}

	/**
	 * Returns the problem record.
	 * 
	 * @return
	 */
	public String getCauseRecord() {
		return causeRecord;
	}
	
	@Override
	public String getMessage() {
		return "Error while parsing record: [" + causeRecord + "] - " + super.getMessage();
	}
	
	@Override
	public String toString() {
		return "Error while parsing record: [" + causeRecord + "] - " + super.toString();
	}
	
}
