package com.library.numj.enums;

import com.library.numj.exceptions.UnsupportedDataTypeException;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration representing different data types supported in NumJ.
 */
public enum DType {
	/** 32-bit floating point number. */
	FLOAT32,
	/** 64-bit floating point number. */
	FLOAT64,
	/** 8-bit integer. */
	INT8,
	/** 16-bit integer. */
	INT16,
	/** 32-bit integer. */
	INT32,
	/** 64-bit integer. */
	INT64;

	/** Mapping between DType and corresponding Java class types. */
	static Map<DType, Class<?>> map = new HashMap<>();
	static {
		map.put(FLOAT32, Float.class);
		map.put(FLOAT64, Double.class);
		map.put(INT8, Byte.class);
		map.put(INT16, Short.class);
		map.put(INT32, Integer.class);
		map.put(INT64, Long.class);
	}

	/**
	 * Returns the corresponding Java class for the DType.
	 *
	 * @return The Java class associated with the DType.
	 */
	public Class<?> is() {
		return map.get(this);
	}

	/**
	 * Sets the value at the specified index in an array, casting it according to the DType.
	 *
	 * @param array The array in which the value is to be set.
	 * @param index The index at which the value should be set.
	 * @param value The value to set, which will be cast based on the DType.
	 */
	public <T> void set(T array, int index, double value) {
		switch (this) {
			case INT8:
				Array.set(array, index, (byte) value);
				break;
			case INT16:
				Array.set(array, index, (short) value);
				break;
			case INT32:
				Array.set(array, index, (int) value);
				break;
			case INT64:
				Array.set(array, index, (long) value);
				break;
			case FLOAT32:
				Array.set(array, index, (float) value);
				break;
			case FLOAT64:
				Array.set(array, index, value);
				break;
			default:
				throw new UnsupportedDataTypeException("Unsupported data type: " + this);
		}
	}
}
