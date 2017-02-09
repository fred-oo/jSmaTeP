package com.kardass.jsmatep.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Buffer for storing all mapped records within valueobjects.
 * 
 * @author Manfred Kardass (manfred@kardass.de)
 *
 * @param <T>
 * 			Value objects' type in this buffer
 */
public class BufferedRecordMapperListener<T> implements RecordMapperListener<T> {
	
	private final List<T> mappedValueObjects = new ArrayList<T>();

	/* (non-Javadoc)
	 * @see de.kardass.jsmatep.in.RecordMapperListener#afterRecordMapped(de.kardass.jsmatep.in.RecordMappedEvent)
	 */
	public void afterRecordMapped(RecordMappedEvent<T> e) {
		mappedValueObjects.add(e.getMappedValueObject());
	}

	/**
	 * @return
	 * 		Mapped value objects.
	 */
	public List<T> getMappedValueObjects() {
		return mappedValueObjects;
	}
	
	/**
	 * @return
	 * 		Size / quantity of buffered value objects
	 */
	public int size() {
		return mappedValueObjects.size();
	}

}
