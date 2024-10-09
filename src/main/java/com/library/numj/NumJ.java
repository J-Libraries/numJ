package com.library.numj;

import java.lang.reflect.Array;
import java.util.Arrays;

import com.library.numj.enums.DType;
import com.library.numj.enums.OperationType;
import com.library.numj.exceptions.ShapeException;
import com.library.numj.operations.ArithmaticOperations;

import static com.library.numj.ExceptionMessages.illegalDataType;


@SuppressWarnings("unchecked")
public class NumJ<T> {
	ArithmaticOperations<T> arithmaticOperations;
	public NumJ(){
		arithmaticOperations = new ArithmaticOperations<>();
	}
	public NDArray<T> array(T[] data) throws ShapeException{
		return new NDArray<>(data);
	}
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
			case INT8:
				dataType = Byte.class; break;
			case INT16:
				dataType = Short.class; break;
			case INT32:
				dataType = Integer.class; break;
			case INT64:
				dataType = Long.class; break;
			default:
				throw new IllegalArgumentException(illegalDataType(dType));
		}
		T[] arr =(T[]) Array.newInstance(dataType, end-start);
		Arrays.setAll(arr, i -> dataType.cast(start+i));
		return new NDArray<T>(arr);
	}
	public NDArray<T> add(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException {
		return (NDArray<T>) arithmaticOperations.operate(arr1, arr2, OperationType.ADDITION);
	}

	public NDArray<T> subtract(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException {
		return (NDArray<T>) arithmaticOperations.operate(arr1, arr2, OperationType.SUBTRACTION);
	}
	public NDArray<T> multiply(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException {
		return (NDArray<T>) arithmaticOperations.operate(arr1, arr2, OperationType.MULTIPLICATION);
	}
	public NDArray<T> divide(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException {
		return (NDArray<T>) arithmaticOperations.operate(arr1, arr2, OperationType.DIVISION);
	}
}
