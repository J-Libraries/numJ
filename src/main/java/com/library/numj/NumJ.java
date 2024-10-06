package com.library.numj;

import java.lang.reflect.Array;
import java.util.Arrays;

import com.library.numj.enums.DType;
import com.library.numj.exceptions.ShapeException;


public class NumJ<T> {
	public NDArray<Integer> arange(int end) throws ShapeException
	{
		if(end < 0) throw new IllegalArgumentException(ExceptionMessages.negativeSizeException(end));
		Integer[] arr = new Integer[end];
		Arrays.setAll(arr, i -> Integer.valueOf(0+i));
		return new NDArray<Integer>(arr);
	}
	public NDArray<Integer> arange(int start, int end) throws ShapeException
	{
		if((end-start) < 0) throw new IllegalArgumentException(ExceptionMessages.negativeSizeException(end-start));
		Integer[] arr = new Integer[end];
		Arrays.setAll(arr, i -> Integer.valueOf(start+i));
		return new NDArray<Integer>(arr);
	}
	@SuppressWarnings("unchecked")
	public NDArray<T> arange(int start, int end, DType dType) throws ShapeException
	{
		if((end-start) < 0) throw new IllegalArgumentException(ExceptionMessages.negativeSizeException(end-start));
		final Class<?> dataType;
		switch(dType) {
			case FLOAT32:
				dataType = Float.class; break;
			case FLOAT64:
				dataType = Double.class; break;
			case UINT8:
				dataType = Byte.class; break;
			case UINT16:
				dataType = Short.class; break;
			case UINT64:
				dataType = Long.class; break;
			default:
				dataType = Integer.class;
		}
		T[] arr =(T[]) Array.newInstance(dataType, end-start);
		Arrays.setAll(arr, i -> dataType.cast(start+i));
		return new NDArray<T>(arr);
	}
}
