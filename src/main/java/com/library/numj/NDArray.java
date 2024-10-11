package com.library.numj;

import com.library.numj.exceptions.ShapeException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Represents an N-dimensional array in the NumJ library.
 * Provides methods for array manipulation such as reshaping, flattening, and transposing.
 *
 * @param <T> The type of elements stored in the array.
 */
@SuppressWarnings("unchecked")
public final class NDArray<T> {
	/** The underlying array data. */
	private T array;
	/** The number of dimensions of the array. */
	private int ndim;
	/** The shape of the array, represented as a list of integers. */
	private final List<Integer> shape = new ArrayList<>();
	/** The total number of elements in the array. */
	private long size = 0;
	/** The total size in bytes of the array elements. */
	private long itemSize = 0;
	/** Utility class instance for helper methods. */
	Utils<T> utils;
	/**
	 * Constructs an NDArray from the given data array.
	 *
	 * @param data The array data to initialize the NDArray with.
	 * @throws ShapeException If the array has an inhomogeneous shape.
	 */
	NDArray(T data) throws ShapeException {
		utils = new Utils<>();
		calculateDimensions(data, 0);
		calculateSize();
		int[] arrayShape = shape.stream().mapToInt(Integer::intValue).toArray();
		this.array = (T) Array.newInstance(Integer.class, arrayShape);
		this.array = createDeepCopy(data);
	}
	@SuppressWarnings("unchecked")
	private T createDeepCopy(T array) {
		if (!array.getClass().isArray()) {
			return array;  // Not an array, return as is
		}

		int length = Array.getLength(array);
		T newArray = (T) Array.newInstance(array.getClass().getComponentType(), length);

		// Parallel stream to copy array elements concurrently
		IntStream.range(0, length).parallel().forEach(i -> {
			Object element = Array.get(array, i);
			if (element != null && element.getClass().isArray()) {
				// Recursively copy nested arrays in parallel
				Array.set(newArray, i, createDeepCopy((T) element));
			} else {
				// Copy primitive or object
				Array.set(newArray, i, element);
			}
		});

		return newArray;
	}

	/**
	 * Calculates the total size and item size of the array based on its shape.
	 */
	private void calculateSize() {
		long size = 1;
		for (int i : shape) {
			size *= i;
		}
		this.size = size;
		this.itemSize = size + ndim;
	}

	/**
	 * Recursively calculates the dimensions of the array.
	 *
	 * @param arr   The array to calculate dimensions for.
	 * @param level The current level of recursion (dimension depth).
	 * @return The number of dimensions.
	 * @throws ShapeException If the array has an inhomogeneous shape.
	 */
	public int calculateDimensions(Object arr, int level) throws ShapeException {
		if (!arr.getClass().isArray()) {
			return ndim;
		}

		int length = Array.getLength(arr);

		synchronized (this) {  // Synchronize access to shared state (shape, ndim)
			if (shape.size() > level) {
				if (length != shape.get(level)) {
					throw new ShapeException(ExceptionMessages.getShapeException(ndim, shape));
				}
			} else {
				shape.add(length);
				ndim++;
			}
		}

		// Check the first element's class type
		Class<?> previousClass = (length > 0 && Array.get(arr, 0) != null)
				? Array.get(arr, 0).getClass()
				: null;

		AtomicInteger previousDim = new AtomicInteger();

		// Parallel stream to process the array
		IntStream.range(0, length).sequential().forEach(i -> {
			Object element = Array.get(arr, i);

			if (element != null && element.getClass().isArray()) {
				if (previousClass != null && previousClass.isArray()) {
					try {
						int dim = calculateDimensions(element, level + 1);
						synchronized (this) {
							if (i == 0) {
								previousDim.set(dim);
							} else if (dim != previousDim.get()) {
								throw new RuntimeException(new ShapeException(ExceptionMessages.getShapeException(ndim, shape)));
							}
						}
					} catch (ShapeException e) {
						throw new RuntimeException(e);
					}
				} else {
					throw new RuntimeException(new ShapeException(ExceptionMessages.getShapeException(ndim, shape)));
				}
			} else {
				if (previousClass != null && previousClass.isArray()) {
					throw new RuntimeException(new ShapeException(ExceptionMessages.getShapeException(ndim, shape)));
				}
			}
		});

		return ndim;
	}


	/**
	 * Returns the underlying array data.
	 *
	 * @return The array data.
	 */
	public T getArray() {
		return this.array;
	}

	/**
	 * Returns the total number of elements in the array.
	 *
	 * @return The total size of the array.
	 */
	public long size() {
		return size;
	}

	/**
	 * Returns the total size in bytes of the array elements.
	 *
	 * @return The item size in bytes.
	 */
	public long itemSize() {
		return this.itemSize;
	}

	/**
	 * Prints the array in a multi-dimensional format.
	 */
	public void printArray() {
		System.out.println(Arrays.deepToString((Object[]) array));
	}

	/**
	 * Returns the number of dimensions of the array.
	 *
	 * @return The number of dimensions (ndim).
	 */
	public int ndim() {
		return this.ndim;
	}

	/**
	 * Returns the shape of the array.
	 *
	 * @return A list representing the size in each dimension.
	 */
	public List<Integer> shape() {
		return shape;
	}

	/**
	 * Recursively flattens the array into a one-dimensional list.
	 *
	 * @param currentArray The current sub-array being processed.
	 * @param flatList     The list to accumulate the flattened elements.
	 */
	private void flattenRecursive(T currentArray, List<Object> flatList) {
		if (currentArray.getClass().isArray()) {
			Arrays.stream(((T[])currentArray)).sequential()
					.forEach(element ->flattenRecursive(element, flatList));

		} else {
			flatList.add(currentArray);
		}
	}

	/**
	 * Flattens the array into a one-dimensional NDArray.
	 *
	 * @return A new NDArray that is a flattened version of the original array.
	 * @throws ShapeException If an error occurs during flattening.
	 */
	@SuppressWarnings("rawtypes")
	public NDArray flatten() throws ShapeException {
		List<Object> flatList = new CopyOnWriteArrayList<>();
		flattenRecursive(this.array, flatList);
		Object[] flattenedArray = flatList.toArray(new Object[0]);
		return new NDArray(flattenedArray);
	}

	/**
	 * Transposes the array by reversing its axes.
	 *
	 * @return A new NDArray that is the transposed version of the original array.
	 * @throws ShapeException If an error occurs during transposition.
	 */
	public NDArray<T> transpose() throws ShapeException {
		int[] axes = new int[ndim];
		for (int i = 0; i < ndim; i++) {
			axes[i] = ndim - 1 - i;
		}
		Object transposedArray = transposeRecursive(array, axes, new int[0]);
		return new NDArray<>((T) transposedArray);
	}

	/**
	 * Recursively transposes the array based on the provided axes.
	 *
	 * @param arr            The array to transpose.
	 * @param axes           The order of axes for transposition.
	 * @param currentIndices The current indices during recursion.
	 * @return The transposed array.
	 * @throws ShapeException If an error occurs during transposition.
	 */
	private Object transposeRecursive(Object arr, int[] axes, int[] currentIndices) throws ShapeException {
		if (currentIndices.length == ndim) {
			int[] originalIndices = new int[ndim];
			for (int i = 0; i < ndim; i++) {
				originalIndices[i] = currentIndices[axes[i]];
			}
			Object element = array;
			for (int idx : originalIndices) {
				element = Array.get(element, idx);
			}
			return element;
		} else {
			int axis = currentIndices.length;
			int dimSize = shape.get(axes[axis]);
			Object[] newArr = new Object[dimSize];
			for (int i = 0; i < dimSize; i++) {
				int[] newIndices = Arrays.copyOf(currentIndices, currentIndices.length + 1);
				newIndices[currentIndices.length] = i;
				Object value = transposeRecursive(arr, axes, newIndices);
				newArr[i] = value;
			}
			return newArr;
		}
	}

	/**
	 * Reshapes the array to the specified new shape.
	 *
	 * @param newShape The desired shape dimensions.
	 * @return A new NDArray with the specified shape.
	 * @throws ShapeException If the total size does not match the original array.
	 */
	public NDArray<T> reshape(int... newShape) throws ShapeException {
		long newSize = 1;
		for (int dim : newShape) {
			newSize *= dim;
		}
		if (this.size != newSize) {
			throw new ShapeException(ExceptionMessages.shapeMismatchedException(size, Arrays.toString(newShape)));
		}
		List<Object> flatList = new CopyOnWriteArrayList<>();
		flattenRecursive(this.array, flatList);
		Object reshapedArray = buildArrayFromFlatList(flatList, newShape, 0);
		return new NDArray<>((T) reshapedArray);
	}

	/**
	 * Builds a multi-dimensional array from a flat list based on the given shape.
	 *
	 * @param flatList The flat list of array elements.
	 * @param shape    The desired shape of the new array.
	 * @param depth    The current depth in the recursive construction.
	 * @return The constructed multi-dimensional array.
	 */
	private Object buildArrayFromFlatList(List<Object> flatList, int[] shape, int depth) {
		int size = shape[depth];
		Object[] array = new Object[size];
		if (depth == shape.length - 1) {
			for (int i = 0; i < size; i++) {
				Array.set(array, i, flatList.remove(0));
			}
		} else {
			for (int i = 0; i < size; i++) {
				Object subArray = buildArrayFromFlatList(flatList, shape, depth + 1);
				Array.set(array, i, subArray);
			}
		}
		return array;
	}

	/**
	 * Calculates the strides of the array based on the given shape.
	 *
	 * @param shape The shape of the array.
	 * @return An array of strides corresponding to each dimension.
	 */
	public int[] strides(int[] shape) {
		int[] strides = new int[shape.length];
		int stride = utils.getElementSize(Double.class);
		for (int i = shape.length - 1; i >= 0; i--) {
			strides[i] = stride;
			stride *= shape[i];
		}
		return strides;
	}

	/**
	 * Calculates the strides of the array based on its current shape.
	 *
	 * @return An array of strides corresponding to each dimension.
	 */
	public int[] strides() {
		int[] strides = new int[shape.size()];
		int stride = utils.getElementSize(shape.get(0).getClass());

		for (int i = shape.size() - 1; i >= 0; i--) {
			strides[i] = stride;
			stride *= shape.get(i);
		}
		return strides;
	}
}
