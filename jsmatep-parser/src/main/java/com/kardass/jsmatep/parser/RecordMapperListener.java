package com.kardass.jsmatep.parser;


/**
 * Listener interface for record processing registration.
 *
 * @author Manfred Kardass (manfred@kardass.de)
 *
 */
public interface RecordMapperListener<T> {

	/**
	 * Is being called after each mapped record.
	 * 
	 * @param e
	 * 		Mapped record event (holds the mapped value object)
	 */
	void afterRecordMapped(RecordMappedEvent<T> e);
	
}
