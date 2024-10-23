package com.library.numj;

import com.library.numj.enums.DType;
import com.library.numj.exceptions.ShapeException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
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
	private List<Integer> shape = new ArrayList<>();
	/** The total number of elements in the array. */
	private long size = 0;
	/** The total size of the array elements. */
	private long itemSize = 0;
	/** The total number of bytes of array elements*/
	private long nBytes = 0;
	private int index = 0;
	/** Utility class instance for helper methods. */
	Utils utils;
	/** Data Type of current array */
	DType dType = DType.INT8;
	int elementSize = 1;
	/**
	 * Constructs an NDArray from the given data array.
	 *
	 * @param data The array data to initialize the NDArray with.
	 * @throws ShapeException If the array has an inhomogeneous shape.
	 */
	NDArray(T data) throws ShapeException {
		utils = new Utils();


		if(utils.isValue(data))
		{
			this.array = data;
			this.size = 1;
			return;
		}
		calculateDimensions(data, 0);
		calculateSize();
		int[] arrayShape = shape.stream().mapToInt(Integer::intValue).toArray();
		this.array = (T) Array.newInstance(dType.is(), arrayShape);
		this.array = createDeepCopy(data);
	}
	/**
	 * Constructs an NDArray with the given data, shape, and number of dimensions.
	 *
	 * @param data  The data to be stored in the NDArray.
	 * @param shape The shape of the NDArray as an array of integers.
	 * @param ndim  The number of dimensions of the NDArray.
	 */
	NDArray(T data, int[] shape, int ndim, DType dType) {
		this.array = data;
		this.dType = dType;
		utils = new Utils();
		Arrays.stream(shape).sequential().forEach(value -> {
			this.shape.add(value);
			this.size *= value;
			if(this.size == 0)
			{
				this.size = value;
			}
			else{
				this.size *= value;
			}
		});
		this.itemSize = this.size + ndim;
		this.ndim = ndim;
		this.nBytes = this.itemSize * utils.getElementSize(dType.is());

	}

	/**
	 * Creates a deep copy of the given array, including nested arrays, using recursion.
	 *
	 * @param array The array to be copied.
	 * @return A deep copy of the array.
	 */
	@SuppressWarnings("unchecked")
	private T createDeepCopy(T array) {
		if (!array.getClass().isArray()) {
			return array;
		}

		int length = Array.getLength(array);
		T newArray = (T) Array.newInstance(array.getClass().getComponentType(), length);

		// Parallel stream to copy array elements concurrently
		IntStream.range(0, length).parallel().forEach(i -> {
			T element = (T) Array.get(array, i);
			if (element != null && element.getClass().isArray()) {
				Array.set(newArray, i, createDeepCopy(element));
			} else {
				if(!(element instanceof  String))
				{
					Class<?> tempClass = array.getClass().getComponentType();
					this.dType = dType.fromSize(utils.getElementSize(tempClass == null ? Object.class : tempClass), utils.isFloatingPoint(element));
				}
				else{
					this.dType = DType.OBJECT;
				}
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
	public int calculateDimensions(T arr, int level) throws ShapeException {
		if (!arr.getClass().isArray()) {
			return ndim;
		}
		if(utils.isPrimitive(arr))
		{
			for(int i = 0; i<Array.getLength(arr);i++)
			{
				T value = (T)Array.get(arr, i);
				if(value == null)
				{
					elementSize = 16;
					this.dType = DType.OBJECT;
				}
				else{
					elementSize = Math.max(elementSize, utils.getElementSize(value.getClass()));
					this.dType = dType.fromSize(elementSize, (value instanceof Double || value instanceof Float));
				}

			}
//			Class<?> classType = Array.get(arr, 0).getClass();
//			if(utils.isValue(classType))
//			{
//				Arrays.stream((T[])arr).parallel().forEach(value->{
//					if(value instanceof Number || value instanceof String)
//					{
//						elementSize = Math.max(elementSize, utils.getElementSize(value.getClass()));
//						this.dType = dType.fromSize(elementSize, (value instanceof Double || value instanceof Float));
//					}
//				});
//			}
//			else{
//				for(int i = 0; i<Array.getLength(arr);i++)
//				{
//					T value = (T)Array.get(arr, i);
//					elementSize = Math.max(elementSize, utils.getElementSize(value.getClass()));
//					this.dType = dType.fromSize(elementSize, (value instanceof Double || value instanceof Float));
//				}
//			}

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
			T element = (T) Array.get(arr, i);
			try{
				if (element != null && element.getClass().isArray()) {
					if (previousClass != null && previousClass.isArray()) {
						int dim = calculateDimensions(element, level + 1);
						synchronized (this) {
							if (i == 0) {
								previousDim.set(dim);
							} else if (dim != previousDim.get()) {
								throw new ShapeException(ExceptionMessages.getShapeException(ndim, shape));
							}
						}
					} else {
						throw new ShapeException(ExceptionMessages.getShapeException(ndim, shape));
					}
				} else {
					if (previousClass != null && previousClass.isArray()) {
						throw new ShapeException(ExceptionMessages.getShapeException(ndim, shape));
					}
				}
			}catch (ShapeException e)
			{
				throw new RuntimeException(e);
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
		printRecurssive(array, 1, false);
	}
	public void printArray(boolean isFullArray) {
		printRecurssive(array, 1, isFullArray);
	}

	private void printRecurssive(T array, int depth, boolean isFull) {
		String indent = getIndent(depth);
		if(utils.isPrimitive(array))
		{
			System.out.print(indent + "[");
			int length = Array.getLength(array);
			if(length > 40 && !isFull) {
				for(int i = 0;i<10;i++)
				{
					if(9 == i ){
						System.out.print(Array.get(array, i));
					}
					else{
						System.out.print(Array.get(array, i)+", ");
					}
				}
				System.out.print("........");
				for(int i = length-11;i<length;i++)
				{
					if(length-1 == i ){
						System.out.print(Array.get(array, i));
					}
					else{
						System.out.print(Array.get(array, i)+", ");
					}
				}
			}else {
				for(int i = 0;i<length;i++)
				{
					if(length-1 == i ){
						System.out.print(Array.get(array, i));
					}
					else{
						System.out.print(Array.get(array, i)+", ");
					}
				}
			}

			System.out.println("],");
		} else if (utils.isValue(array)) {
			System.out.println(array);
		} else{
			System.out.println(indent + "[");
			for(int i = 0;i<Array.getLength(array);i++)
			{
				printRecurssive((T) Array.get(array, i), depth+1, isFull);
			}
			int l = Array.getLength(array);
			if(depth == l-1) {
				System.out.println(indent + "],");
			}else{
				System.out.println(indent + "]");
			}
		}
	}

	// Helper method to create indentation
	private String getIndent(int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < depth; i++) { // Start at 1 to skip the top-level
			sb.append("\t"); // Append a tab for each depth level
		}
		return sb.toString(); // Return the indentation string
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
	 * @param flatArray     The list to accumulate the flattened elements.
	 */
	private synchronized  <R> void flattenRecursive(T currentArray, R flatArray, int level) {

		final int finalLevel = level+1;
		if(utils.isPrimitive(currentArray))
		{
			for(int i = 0;i<Array.getLength(currentArray);i++)
			{
				Array.set(flatArray, index++, Array.get(currentArray, i));
			}
		}else if(utils.isValue(currentArray))
		{
			Array.set(flatArray, index++, currentArray);
		}
		else{
			Arrays.stream(((T[])currentArray)).sequential()
					.forEach(element -> flattenRecursive(element, flatArray, finalLevel));
		}
	}

	/**
	 * Flattens the array into a one-dimensional NDArray.
	 *
	 * @return A new NDArray that is a flattened version of the original array.
	 * @throws ShapeException If an error occurs during flattening.
	 */
	public <R> NDArray<R> flatten() throws ShapeException {
		R flatArray = (R) Array.newInstance(dType.is(), (int)this.size);
		Arrays.fill((R[])flatArray, dType.getDefaultValue());
		flattenRecursive(this.array, flatArray, 0);
		this.index = 0;
		return new NDArray<>(flatArray);
	}

	/**
	 * Reshapes the array to the specified new shape.
	 *
	 * @param newShape The desired shape dimensions.
	 * @return A new NDArray with the specified shape.
	 * @throws ShapeException If the total size does not match the original array.
	 */
	public <R> NDArray<R> reshape(int... newShape) throws ShapeException {
		long newSize = Arrays.stream(newShape)
				.asLongStream()
				.reduce(1, (a, b) -> a * b);




		if (this.size != newSize) {
			throw new ShapeException(ExceptionMessages.shapeMismatchedException(size, Arrays.toString(newShape)));
		}
		Class<?> arrayOutType = this.array.getClass().getComponentType();
		while (arrayOutType.isArray()) {
			arrayOutType = arrayOutType.getComponentType();
		}
		R flatList = (R) Array.newInstance(arrayOutType, (int) newSize);
		flattenRecursive(this.array, flatList, 0);
		R newArray = (R) Array.newInstance(arrayOutType, newShape);
		this.index = 0;
		buildArrayFromFlatList(flatList, newArray);

		return new NDArray<>(newArray);

	}


	/**
	 * Builds a multi-dimensional array from a flat list based on the given shape.
	 *
	 * @param flatList The flat list of array elements.
	 * @param newArray The desired new array to fill the values.
	 */
	private <R> void buildArrayFromFlatList(R flatList, R newArray) {
		if (Array.get(newArray, 0) != null && Array.get(newArray, 0).getClass().isArray()) {
			IntStream.range(0, Array.getLength(newArray))
					.forEach(i -> buildArrayFromFlatList(flatList, Array.get(newArray, i)));
		} else {
			IntStream.range(0, Array.getLength(newArray))
					.forEach(i -> Array.set(newArray, i, Array.get(flatList, index++)));
		}
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
		int stride = utils.getElementSize(dType.is());//shape.get(0).getClass()

		for (int i = shape.size() - 1; i >= 0; i--) {
			strides[i] = stride;
			stride *= shape.get(i);
		}
		return strides;
	}

	public DType type()
	{
		return this.dType;
	}
}
