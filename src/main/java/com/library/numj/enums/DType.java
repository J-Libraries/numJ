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
	static Map<DType, Class<?>> typeToClassMap = new HashMap<>();
	static Map<Integer, DType> sizeToTypeNumericMap = new HashMap<>();
	static Map<Integer, DType> sizeToTypeFloatingPointMap = new HashMap<>();
	static {
		typeToClassMap.put(FLOAT32, float.class);
		typeToClassMap.put(FLOAT64, double.class);
		typeToClassMap.put(INT8, byte.class);
		typeToClassMap.put(INT16, short.class);
		typeToClassMap.put(INT32, int.class);
		typeToClassMap.put(INT64, long.class);

		sizeToTypeNumericMap.put(1, INT8);
		sizeToTypeNumericMap.put(2, INT16);
		sizeToTypeNumericMap.put(4, INT32);
		sizeToTypeNumericMap.put(8, INT64);
		sizeToTypeFloatingPointMap.put(4, FLOAT32);
		sizeToTypeFloatingPointMap.put(8, FLOAT64);
	}

	public DType fromSize(int size, boolean isFloatingPoint){
		if(isFloatingPoint)
		{
			if(sizeToTypeFloatingPointMap.containsKey(size)) return sizeToTypeFloatingPointMap.get(size);
		}
		else{
			if(sizeToTypeNumericMap.containsKey(size)) return  sizeToTypeNumericMap.get(size);
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Returns the corresponding Java class for the DType.
	 *
	 * @return The Java class associated with the DType.
	 */
	public Class<?> is() {
		return typeToClassMap.get(this);
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
