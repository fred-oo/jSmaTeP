package com.kardass.jsmatep.parser;

/**
 * Thrown event after each processed record.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public class RecordMappedEvent<T> {

	/** The mapped value object */
	private T mappedValueObject;
	
	/** Current record's number */
	private long currentRecordNumber;

	/**
	 * @param mappedValueObject
	 * @param currentRecordNumber
	 */
	public RecordMappedEvent(T mappedValueObject, long currentRecordNumber) {
		this.mappedValueObject = mappedValueObject;
		this.currentRecordNumber = currentRecordNumber;
	}

	/**
	 * @return
	 */
	public T getMappedValueObject() {
		return mappedValueObject;
	}

	/**
	 * @return
	 */
	public long getCurrentRecordNumber() {
		return currentRecordNumber;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder()
				.append('[').append(getMappedValueObject()).append(']')
				.toString();
	}
	
}
