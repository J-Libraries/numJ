package com.library.numj;

import com.library.numj.enums.DType;
import com.library.numj.enums.OperationType;
import com.library.numj.enums.Order;
import com.library.numj.exceptions.ShapeException;
import com.library.numj.exceptions.ShapeMismatchException;
import com.library.numj.operations.ArithmaticOperations;
import com.library.numj.operations.ArrayCreation;
import com.library.numj.operations.ArrayModification;

import java.lang.reflect.Array;
import java.util.Arrays;

import static com.library.numj.ExceptionMessages.shapeMismatchException;

/**
 * The NumJ class provides methods to create and manipulate NDArray objects.
 * It includes functionalities similar to NumPy in Python.
 *
 */
@SuppressWarnings("unchecked")
public class NumJ {
	/** Instance of ArithmaticOperations for performing arithmetic operations on NDArrays. */
	ArithmaticOperations arithmaticOperations;
	ArrayModification arrayModification;
	ArrayCreation arrayCreation;

	/**
	 * Default constructor initializes the arithmetic operations.
	 */
	public NumJ(){
		arithmaticOperations = new ArithmaticOperations();
		arrayModification = new ArrayModification();
		arrayCreation = new ArrayCreation();
	}

	/**
	 * Creates an NDArray from the given data array.
	 *
	 * @param data The data to be stored in the NDArray.
	 * @return An NDArray containing the provided data.
	 * @throws ShapeException If the data cannot be converted into an NDArray due to shape issues.
	 */
	public <T, R>NDArray<R> array(T data) throws ShapeException{
		return (NDArray<R>) new NDArray<>(data);
	}
	public <T, R> NDArray<R> array(T data, int[] shape, int ndim, DType dType)
	{
		return (NDArray<R>) new NDArray<>(data, shape, ndim, dType);
	}


	/**
	 * Creates an NDArray filled with zeros of the given shape, using the default data type (INT32) and C order.
	 *
	 * @param shape The shape of the NDArray.
	 * @return An NDArray filled with zeros.
	 */
	public <T> NDArray<T> zeros(int[] shape) {
		return  zeros(shape, DType.INT32, Order.C);
	}


	/**
	 * Creates an NDArray filled with zeros of the given shape and specified data type, using C order.
	 *
	 * @param shape The shape of the NDArray.
	 * @param dType The data type of the elements in the NDArray.
	 * @return An NDArray filled with zeros.
	 */
	public <T> NDArray<T> zeros(int[] shape, DType dType) {
		return (NDArray<T>) zeros(shape, dType, Order.C);
	}

	/**
	 * Creates an NDArray filled with zeros of the given shape, specified data type, and memory order.
	 *
	 * @param shape The shape of the NDArray.
	 * @param dType The data type of the elements in the NDArray.
	 * @param order The memory layout order, either C (row-major) or F (column-major).
	 * @return An NDArray filled with zeros.
	 */
	public <T> NDArray<T> zeros(int[] shape, DType dType, Order order) {
		return arrayCreation.zeros(shape, dType);
	}

	/**
	 * Creates an NDArray filled with ones of the given shape, using the default data type (INT32) and C order.
	 *
	 * @param shape The shape of the NDArray.
	 * @return An NDArray filled with ones.
	 */
	public <T> NDArray<T> ones(int[] shape) {
		return (NDArray<T>) ones(shape, DType.INT32, Order.C);
	}

	/**
	 * Creates an NDArray filled with ones of the given shape and specified data type, using C order.
	 *
	 * @param shape The shape of the NDArray.
	 * @param dType The data type of the elements in the NDArray.
	 * @return An NDArray filled with ones.
	 */
	public <T> NDArray<T> ones(int[] shape, DType dType) {
		return (NDArray<T>) ones(shape, dType, Order.C);
	}

	/**
	 * Creates an NDArray filled with ones of the given shape, specified data type, and memory order.
	 *
	 * @param shape The shape of the NDArray.
	 * @param dType The data type of the elements in the NDArray.
	 * @param order The memory layout order, either C (row-major) or F (column-major).
	 * @return An NDArray filled with ones.
	 */
	public <T> NDArray<T> ones(int[] shape, DType dType, Order order) {
		return arrayCreation.ones(shape, dType);
	}


	/**
	 * Generates an NDArray with a range of integers from 0 up to (but not including) end.
	 *
	 * @param end The end value (exclusive).
	 * @return An NDArray containing integers from 0 to end - 1.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 * @throws IllegalArgumentException If end is negative.
	 */
	public <T> NDArray<T> arange(int end) throws ShapeException {
		return arange(0, end, DType.INT32, 0);
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
	public <T> NDArray<T> arange(int end, int[] shape) throws ShapeException {
		return arange(0, end, DType.INT32, 0, shape);
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
	public <T> NDArray<T> arange(int start, int end) throws ShapeException {
		return arange(start, end, DType.INT32, 0);
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
	public <T> NDArray<T> arange(int start, int end, int[] shape) throws ShapeException {
		return  arange(start, end, DType.INT32, 0, shape);
	}

	/**
	 * Generates an NDArray with a range of integers from start up to (but not including) end, with a specified step.
	 *
	 * @param start The starting value (inclusive).
	 * @param end   The end value (exclusive).
	 * @param step  The step size for the range.
	 * @return An NDArray containing integers from start to end - 1 with a step size of step.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 */
	public <T> NDArray<T> arange(int start, int end, int step) throws ShapeException {
		return arange(start, end, DType.INT32, step);
	}

	/**
	 * Generates an NDArray with a range of integers from start up to (but not including) end, with a specific shape and step.
	 *
	 * @param start The starting value (inclusive).
	 * @param end   The end value (exclusive).
	 * @param step  The step size for the range.
	 * @param shape The shape of the resulting NDArray.
	 * @return An NDArray containing integers from start to end - 1 with a step size of step.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 */
	public <T> NDArray<T> arange(int start, int end, int step, int[] shape) throws ShapeException {
		return arange(start, end, DType.INT32, step, shape);
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
	public <T> NDArray<T> arange(int start, int end, DType dType) throws ShapeException {
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
	public <T> NDArray<T> arange(int start, int end, DType dType, int[] shape) throws ShapeException {
		return arange(start, end, dType, 0, shape);
	}

	/**
	 * Generates an NDArray with a range of numbers from start up to (but not including) end, cast to the specified data type and step value.
	 *
	 * @param start  The starting value (inclusive).
	 * @param end    The end value (exclusive).
	 * @param dType  The data type of the elements in the array.
	 * @param step   The step size for the range.
	 * @return An NDArray containing numbers from start to end - 1, cast to the specified data type and step size.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 */
	public <T> NDArray<T> arange(int start, int end, DType dType, int step) throws ShapeException {
		if(step < 0)
			throw new IllegalArgumentException(ExceptionMessages.negativeSkipException(step));
		int size = (int)Math.ceil(((double)(end - start)) / (step == 0 ? 1 : step+1));
		return arange(start, end, dType, step, new int[]{size});
	}

	/**
	 * Generates an NDArray with a range of numbers from start up to (but not including) end, cast to the specified data type, step value, and shape.
	 *
	 * @param start  The starting value (inclusive).
	 * @param end    The end value (exclusive).
	 * @param dType  The data type of the elements in the array.
	 * @param step   The step size for the range.
	 * @param shape  The shape of the resulting NDArray.
	 * @return An NDArray containing numbers from start to end - 1, cast to the specified data type, step size, and shape.
	 * @throws ShapeException If there is an issue creating the NDArray.
	 * @throws IllegalArgumentException If the size is negative or the shape does not match the size.
	 */
	public <T> NDArray<T> arange(int start, int end, DType dType, int step, int[] shape) throws ShapeException {
		if(step < 0)
			throw new IllegalArgumentException(ExceptionMessages.negativeSkipException(step));
		int size = (int)Math.ceil(((double)(end - start)) / (step == 0 ? 1 : step));
		if(size<0)
			throw new IllegalArgumentException(ExceptionMessages.negativeSizeException(size));
		if (shape.length != 0 && size != Arrays.stream(shape).reduce(1, (a, b) -> (a * b)))
			throw new ShapeMismatchException(shapeMismatchException(size, shape));


		final Class<?> dataType = dType.is();
		T arr = (T) Array.newInstance(dataType, size);
		Arrays.parallelSetAll((T[]) arr, i -> i == 0 ? i + start : start + (step == 0 ? i : step*i));
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
	public <T> NDArray<T> add(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException {
		return arithmaticOperations.operate(arr1, arr2, OperationType.ADDITION);
	}

	/**
	 * Subtracts the second NDArray from the first NDArray element-wise.
	 *
	 * @param arr1 The first NDArray.
	 * @param arr2 The second NDArray.
	 * @return A new NDArray containing the result of element-wise subtraction.
	 * @throws ShapeException If the shapes of arr1 and arr2 are not compatible for broadcasting.
	 */
	public <T> NDArray<T> subtract(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException {
		return arithmaticOperations.operate(arr1, arr2, OperationType.SUBTRACTION);
	}

	/**
	 * Multiplies two NDArrays element-wise.
	 *
	 * @param arr1 The first NDArray.
	 * @param arr2 The second NDArray.
	 * @return A new NDArray containing the result of element-wise multiplication.
	 * @throws ShapeException If the shapes of arr1 and arr2 are not compatible for broadcasting.
	 */
	public <T> NDArray<T> multiply(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException {
		return arithmaticOperations.operate(arr1, arr2, OperationType.MULTIPLICATION);
	}

	/**
	 * Divides the first NDArray by the second NDArray element-wise.
	 *
	 * @param arr1 The numerator NDArray.
	 * @param arr2 The denominator NDArray.
	 * @return A new NDArray containing the result of element-wise division.
	 * @throws ShapeException If the shapes of arr1 and arr2 are not compatible for broadcasting.
	 */
	public <T> NDArray<T> divide(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException {
		return arithmaticOperations.operate(arr1, arr2, OperationType.DIVISION);
	}

	/**
	 * Performs Bitwise-And operation on first NDArray and second NDArray.
	 *
	 * @param arr1 The numerator NDArray.
	 * @param arr2 The denominator NDArray.
	 * @return A new NDArray containing the result of element-wise Bitwise-And.
	 * @throws ShapeException If the shapes of arr1 and arr2 are not compatible for broadcasting.
	 */
	public <T> NDArray<T> bitwiseAnd(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException{
		return arithmaticOperations.operate(arr1, arr2, OperationType.BITWISE_AND);
	}

	/**
	 * Performs Bitwise-Or operation on first NDArray and second NDArray.
	 *
	 * @param arr1 The numerator NDArray.
	 * @param arr2 The denominator NDArray.
	 * @return A new NDArray containing the result of element-wise Bitwise-Or.
	 * @throws ShapeException If the shapes of arr1 and arr2 are not compatible for broadcasting.
	 */
	public <T> NDArray<T> bitwiseOr(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException{
		return arithmaticOperations.operate(arr1, arr2, OperationType.BITWISE_OR);
	}

	/**
	 * Performs Bitwise-Xor operation on first NDArray and second NDArray.
	 *
	 * @param arr1 The numerator NDArray.
	 * @param arr2 The denominator NDArray.
	 * @return A new NDArray containing the result of element-wise Bitwise-Xor.
	 * @throws ShapeException If the shapes of arr1 and arr2 are not compatible for broadcasting.
	 */
	public <T> NDArray<T> bitwiseXor(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException{
		return arithmaticOperations.operate(arr1, arr2, OperationType.BITWISE_XOR);
	}

	/**
	 * Performs Inversion operation (NOT) on NDArray.
	 *
	 * @param arr1 The numerator NDArray.
	 * @return A new NDArray containing the result of element-wise Inversion (Not).
	 * @throws ShapeException If the shapes of arr1 and arr2 are not compatible for broadcasting.
	 */
	public <T> NDArray<T> invert(NDArray<T> arr1) throws ShapeException{
		return arithmaticOperations.operate(arr1, OperationType.INVERT);
	}

	/**
	 * Transposes the given NDArray.
	 *
	 * @param array The NDArray to be transposed.
	 * @param <R>   The type of elements in the transposed NDArray.
	 * @return A new NDArray that is the transposed version of the input array.
	 * @throws ShapeException If there is an issue during the transposition.
	 */
	public <T, R> NDArray<R> transpose(NDArray<T> array) throws ShapeException {
		return arrayModification.transpose(array);
	}

	/**
	 * Creates an empty NDArray with the specified shape.
	 *
	 * @param shape The shape of the NDArray.
	 * @param <R>   The type of elements in the new NDArray.
	 * @return A new NDArray of the specified shape, filled with null values.
	 * @throws ShapeException If there is an issue creating the empty NDArray.
	 */
	public <R> NDArray<R> empty(int[] shape) throws ShapeException {
		R array = (R) Array.newInstance(int.class, shape);
		return new NDArray<>(array, shape, shape.length, DType.INT32);
	}

	/**
	 * Creates an identity matrix with a specified number of rows and columns,
	 * using the default data type (INT32) and the main diagonal.
	 *
	 * @param rows The number of rows in the identity matrix.
	 * @param <R>  The type of elements in the NDArray.
	 * @return A new NDArray representing the identity matrix.
	 * @throws ShapeException If there is an issue creating the identity matrix.
	 */
	public <R> NDArray<R> eye(int rows) throws ShapeException {
		return eye(rows, rows, 0, DType.INT32);
	}

	/**
	 * Creates an identity matrix with a specified number of rows,
	 * using the specified data type and the main diagonal.
	 *
	 * @param rows  The number of rows in the identity matrix.
	 * @param dType The data type of the elements in the NDArray.
	 * @param <R>   The type of elements in the NDArray.
	 * @return A new NDArray representing the identity matrix.
	 * @throws ShapeException If there is an issue creating the identity matrix.
	 */
	public <R> NDArray<R> eye(int rows, DType dType) throws ShapeException {
		return eye(rows, rows, 0, dType);
	}

	/**
	 * Creates an identity matrix with specified rows and columns,
	 * using the default data type (INT32) and the main diagonal.
	 *
	 * @param rows The number of rows in the identity matrix.
	 * @param cols The number of columns in the identity matrix.
	 * @param <R>  The type of elements in the NDArray.
	 * @return A new NDArray representing the identity matrix.
	 * @throws ShapeException If there is an issue creating the identity matrix.
	 */
	public <R> NDArray<R> eye(int rows, int cols) throws ShapeException {
		return eye(rows, cols, 0, DType.INT32);
	}

	/**
	 * Creates an identity matrix with specified rows and columns,
	 * using the specified data type and the main diagonal.
	 *
	 * @param rows  The number of rows in the identity matrix.
	 * @param cols  The number of columns in the identity matrix.
	 * @param dType The data type of the elements in the NDArray.
	 * @param <R>   The type of elements in the NDArray.
	 * @return A new NDArray representing the identity matrix.
	 * @throws ShapeException If there is an issue creating the identity matrix.
	 */
	public <R> NDArray<R> eye(int rows, int cols, DType dType) throws ShapeException {
		return eye(rows, cols, 0, dType);
	}

	/**
	 * Creates an identity matrix with specified rows, columns, and diagonal offset,
	 * using the default data type (INT32).
	 *
	 * @param rows              The number of rows in the identity matrix.
	 * @param cols              The number of columns in the identity matrix.
	 * @param identityDiagonal   The diagonal index of the identity matrix.
	 * @param <R>               The type of elements in the NDArray.
	 * @return A new NDArray representing the identity matrix.
	 * @throws ShapeException If there is an issue creating the identity matrix.
	 */
	public <R> NDArray<R> eye(int rows, int cols, int identityDiagonal) throws ShapeException {
		return eye(rows, cols, identityDiagonal, DType.INT32);
	}

	/**
	 * Creates an identity matrix with specified rows, columns, diagonal offset, and data type.
	 *
	 * @param rows              The number of rows in the identity matrix.
	 * @param cols              The number of columns in the identity matrix.
	 * @param identityDiagonal   The diagonal index of the identity matrix.
	 * @param dType             The data type of the elements in the NDArray.
	 * @param <R>               The type of elements in the NDArray.
	 * @return A new NDArray representing the identity matrix.
	 * @throws ShapeException If there is an issue creating the identity matrix.
	 * @throws IllegalArgumentException If the diagonal index exceeds the number of columns or rows.
	 */
	public <R> NDArray<R> eye(int rows, int cols, int identityDiagonal, DType dType) throws ShapeException {
		if (identityDiagonal >= cols)
			throw new IllegalArgumentException(ExceptionMessages.diagonalGreaterThanColsException(cols, identityDiagonal));
		if (Math.abs(identityDiagonal) >= rows)
			throw new IllegalArgumentException(ExceptionMessages.diagonalLessThanRowsException(rows, identityDiagonal));
		return (NDArray<R>) arrayCreation.eye(rows, cols, identityDiagonal, dType);
	}


}
