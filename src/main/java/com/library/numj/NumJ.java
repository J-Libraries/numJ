package com.library.numj;

import com.library.numj.enums.DType;
import com.library.numj.enums.OperationType;
import com.library.numj.exceptions.ShapeException;
import com.library.numj.exceptions.ShapeMismatchException;
import com.library.numj.operations.ArithmaticOperations;

import java.lang.reflect.Array;
import java.util.Arrays;

import static com.library.numj.ExceptionMessages.shapeMismatchException;

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
	public NDArray<T> array(T data) throws ShapeException{
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
	public NDArray<Integer[]> arange(int end) throws ShapeException {
		return (NDArray<Integer[]>) arange(0, end, DType.INT32, 0);
	}

	/**
	 * Generates an NDArray with a range of integers from 0 up to (but not including) end, with a specific shape.
	 *
	 * @param end   The end value (exclusive).
	 * @param shape The shape of the resulting NDArray.
	 * @return An NDArray containing integers from 0 to end - 1.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 * @throws IllegalArgumentException If the resulting shape does not match the size.
	 */
	public NDArray<Integer[]> arange(int end, int[] shape) throws ShapeException {
		return (NDArray<Integer[]>) arange(0, end, DType.INT32, 0, shape);
	}

	/**
	 * Generates an NDArray with a range of integers from start up to (but not including) end.
	 *
	 * @param start The starting value (inclusive).
	 * @param end   The end value (exclusive).
	 * @return An NDArray containing integers from start to end - 1.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 * @throws IllegalArgumentException If the resulting size is negative.
	 */
	public NDArray<Integer[]> arange(int start, int end) throws ShapeException {
		return (NDArray<Integer[]>) arange(start, end, DType.INT32, 0);
	}

	/**
	 * Generates an NDArray with a range of integers from start up to (but not including) end, with a specific shape.
	 *
	 * @param start The starting value (inclusive).
	 * @param end   The end value (exclusive).
	 * @param shape The shape of the resulting NDArray.
	 * @return An NDArray containing integers from start to end - 1.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 */
	public NDArray<Integer[]> arange(int start, int end, int[] shape) throws ShapeException {
		return (NDArray<Integer[]>) arange(start, end, DType.INT32, 0, shape);
	}

	/**
	 * Generates an NDArray with a range of integers from start up to (but not including) end, with a specified skip.
	 *
	 * @param start The starting value (inclusive).
	 * @param end   The end value (exclusive).
	 * @param skip  The step size for the range.
	 * @return An NDArray containing integers from start to end - 1 with a step size of skip.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 */
	public NDArray<Integer[]> arange(int start, int end, int skip) throws ShapeException {
		return (NDArray<Integer[]>) arange(start, end, DType.INT32, skip);
	}

	/**
	 * Generates an NDArray with a range of integers from start up to (but not including) end, with a specific shape and skip.
	 *
	 * @param start The starting value (inclusive).
	 * @param end   The end value (exclusive).
	 * @param skip  The step size for the range.
	 * @param shape The shape of the resulting NDArray.
	 * @return An NDArray containing integers from start to end - 1 with a step size of skip.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 */
	public NDArray<Integer[]> arange(int start, int end, int skip, int[] shape) throws ShapeException {
		return (NDArray<Integer[]>) arange(start, end, DType.INT32, skip, shape);
	}

	/**
	 * Generates an NDArray with a range of numbers from start up to (but not including) end, cast to the specified data type.
	 *
	 * @param start  The starting value (inclusive).
	 * @param end    The end value (exclusive).
	 * @param dType  The data type of the elements in the array.
	 * @return An NDArray containing numbers from start to end - 1, cast to the specified data type.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 * @throws IllegalArgumentException If the resulting size is negative or the data type is unsupported.
	 */
	public NDArray<T> arange(int start, int end, DType dType) throws ShapeException {
		return arange(start, end, dType, 0);
	}

	/**
	 * Generates an NDArray with a range of numbers from start up to (but not including) end, cast to the specified data type and shape.
	 *
	 * @param start  The starting value (inclusive).
	 * @param end    The end value (exclusive).
	 * @param dType  The data type of the elements in the array.
	 * @param shape  The shape of the resulting NDArray.
	 * @return An NDArray containing numbers from start to end - 1, cast to the specified data type.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 */
	@SuppressWarnings("unchecked")
	public NDArray<T> arange(int start, int end, DType dType, int[] shape) throws ShapeException {
		return arange(start, end, dType, 0, shape);
	}

	/**
	 * Generates an NDArray with a range of numbers from start up to (but not including) end, cast to the specified data type and skip value.
	 *
	 * @param start  The starting value (inclusive).
	 * @param end    The end value (exclusive).
	 * @param dType  The data type of the elements in the array.
	 * @param skip   The step size for the range.
	 * @return An NDArray containing numbers from start to end - 1, cast to the specified data type and step size.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 */
	public NDArray<T> arange(int start, int end, DType dType, int skip) throws ShapeException {
		if(skip < 0)
			throw new IllegalArgumentException(ExceptionMessages.negativeSkipException(skip));
		int size = (int)Math.ceil(((double)(end - start)) / (skip == 0 ? 1 : skip+1));
		return arange(start, end, dType, skip, new int[]{size});
	}

	/**
	 * Generates an NDArray with a range of numbers from start up to (but not including) end, cast to the specified data type, skip value, and shape.
	 *
	 * @param start  The starting value (inclusive).
	 * @param end    The end value (exclusive).
	 * @param dType  The data type of the elements in the array.
	 * @param skip   The step size for the range.
	 * @param shape  The shape of the resulting NDArray.
	 * @return An NDArray containing numbers from start to end - 1, cast to the specified data type, step size, and shape.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 * @throws IllegalArgumentException If the size is negative or the shape does not match the size.
	 */
	public NDArray<T> arange(int start, int end, DType dType, int skip, int[] shape) throws ShapeException {
		if(skip < 0)
			throw new IllegalArgumentException(ExceptionMessages.negativeSkipException(skip));
		int size = (int)Math.ceil(((double)(end - start)) / (skip == 0 ? 1 : skip+1));
		if(size<0)
			throw new IllegalArgumentException(ExceptionMessages.negativeSizeException(size));
		if (shape.length != 0 && size != Arrays.stream(shape).reduce(1, (a, b) -> (a * b)))
			throw new ShapeMismatchException(shapeMismatchException(size, shape));


		final Class<?> dataType = dType.is();
		T arr = (T) Array.newInstance(dataType, size);
		Arrays.parallelSetAll((T[]) arr, i -> i == 0 ? i + start : start + (skip+1)*i);
		return shape.length == 1 ? new NDArray<>(arr)
				: new NDArray<>(arr).reshape(shape);
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
