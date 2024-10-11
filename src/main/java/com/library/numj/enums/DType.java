package com.library.numj.enums;

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
	static Map<DType, Class> map = new HashMap();
	static {
		map.put(FLOAT32, Float.class);
		map.put(FLOAT64, Double.class);
		map.put(INT8, Byte.class);
		map.put(INT16, Short.class);
		map.put(INT32, Integer.class);
		map.put(INT64, Long.class);
	}
	public Class<?> is()
	{
		return map.get(this);
	}
}
