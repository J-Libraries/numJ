package com.library.numj;

import java.lang.reflect.Array;
import java.util.Arrays;

import com.library.numj.enums.DType;
import com.library.numj.enums.OperationType;
import com.library.numj.exceptions.ShapeException;
import com.library.numj.operations.ArithmaticOperations;

import static com.library.numj.ExceptionMessages.illegalDataType;

/**
 * The NumJ class provides methods to create and manipulate NDArray objects.
 * It includes functionalities similar to NumPy in Python.
 *
 * @param <T> The type of elements in the arrays.
 */
@SuppressWarnings("unchecked")
public class NumJ<T> {
	/** Instance of ArithmaticOperations for performing arithmetic operations on NDArrays. */
	ArithmaticOperations<T> arithmaticOperations;

	/**
	 * Default constructor initializes the arithmetic operations.
	 */
	public NumJ(){
		arithmaticOperations = new ArithmaticOperations<>();
	}

	/**
	 * Creates an NDArray from the given data array.
	 *
	 * @param data The data to be stored in the NDArray.
	 * @return An NDArray containing the provided data.
	 * @throws ShapeException If the data cannot be converted into an NDArray due to shape issues.
	 */
	public NDArray<T> array(T[] data) throws ShapeException{
		return new NDArray<>(data);
	}

	/**
	 * Generates an NDArray with a range of integers from 0 up to (but not including) end.
	 *
	 * @param end The end value (exclusive).
	 * @return An NDArray containing integers from 0 to end - 1.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 * @throws IllegalArgumentException If end is negative.
	 */
	public NDArray<Integer> arange(int end) throws ShapeException
	{
		if(end < 0) throw new IllegalArgumentException(ExceptionMessages.negativeSizeException(end));
		Integer[] arr = new Integer[end];
		Arrays.setAll(arr, i -> Integer.valueOf(0+i));
		return new NDArray<Integer>(arr);
	}

	/**
	 * Generates an NDArray with a range of integers from start up to (but not including) end.
	 *
	 * @param start The starting value (inclusive).
	 * @param end The end value (exclusive).
	 * @return An NDArray containing integers from start to end - 1.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 * @throws IllegalArgumentException If the resulting size is negative.
	 */
	public NDArray<Integer> arange(int start, int end) throws ShapeException
	{
		if((end - start) < 0) throw new IllegalArgumentException(ExceptionMessages.negativeSizeException(end - start));
		int size = end - start;
		Integer[] arr = new Integer[size];
		Arrays.setAll(arr, i -> Integer.valueOf(start + i));
		return new NDArray<Integer>(arr);
	}

	/**
	 * Generates an NDArray with a range of numbers from start up to (but not including) end, cast to the specified data type.
	 *
	 * @param start The starting value (inclusive).
	 * @param end The end value (exclusive).
	 * @param dType The data type of the elements in the array.
	 * @return An NDArray containing numbers from start to end - 1, cast to the specified data type.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 * @throws IllegalArgumentException If the resulting size is negative or the data type is unsupported.
	 */
	@SuppressWarnings("unchecked")
	public NDArray<T> arange(int start, int end, DType dType) throws ShapeException
	{
		if((end - start) < 0) throw new IllegalArgumentException(ExceptionMessages.negativeSizeException(end - start));
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
		int size = end - start;
		T[] arr = (T[]) Array.newInstance(dataType, size);
		Arrays.setAll(arr, i -> dataType.cast(start + i));
		return new NDArray<T>(arr);
	}

	/**
	 * Adds two NDArrays element-wise.
	 *
	 * @param arr1 The first NDArray.
	 * @param arr2 The second NDArray.
	 * @return A new NDArray containing the result of element-wise addition.
	 * @throws ShapeException If the shapes of arr1 and arr2 are not compatible for broadcasting.
	 */
	public NDArray<T> add(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException {
		return (NDArray<T>) arithmaticOperations.operate(arr1, arr2, OperationType.ADDITION);
	}

	/**
	 * Subtracts the second NDArray from the first NDArray element-wise.
	 *
	 * @param arr1 The first NDArray.
	 * @param arr2 The second NDArray.
	 * @return A new NDArray containing the result of element-wise subtraction.
	 * @throws ShapeException If the shapes of arr1 and arr2 are not compatible for broadcasting.
	 */
	public NDArray<T> subtract(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException {
		return (NDArray<T>) arithmaticOperations.operate(arr1, arr2, OperationType.SUBTRACTION);
	}

	/**
	 * Multiplies two NDArrays element-wise.
	 *
	 * @param arr1 The first NDArray.
	 * @param arr2 The second NDArray.
	 * @return A new NDArray containing the result of element-wise multiplication.
	 * @throws ShapeException If the shapes of arr1 and arr2 are not compatible for broadcasting.
	 */
	public NDArray<T> multiply(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException {
		return (NDArray<T>) arithmaticOperations.operate(arr1, arr2, OperationType.MULTIPLICATION);
	}

	/**
	 * Divides the first NDArray by the second NDArray element-wise.
	 *
	 * @param arr1 The numerator NDArray.
	 * @param arr2 The denominator NDArray.
	 * @return A new NDArray containing the result of element-wise division.
	 * @throws ShapeException If the shapes of arr1 and arr2 are not compatible for broadcasting.
	 */
	public NDArray<T> divide(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException {
		return (NDArray<T>) arithmaticOperations.operate(arr1, arr2, OperationType.DIVISION);
	}
}
