package com.library.numj;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.library.numj.exceptions.ShapeException;

import static com.library.numj.Utils.broadcastShapes;
import static com.library.numj.Utils.getElementSize;

@SuppressWarnings("unchecked")
public final class NDArray<T>{
	private T[] array;
	private int ndim;
	private List<Integer> shape = new ArrayList<Integer>();
	private long size = 0;
	private long itemSize = 0;
	
	protected NDArray(T[] data) throws ShapeException
	{
		this.array = data;
		calculateDimensions(data, 0);
		calculateSize();
	}
	private void calculateSize() {
		long size = 1;
		for(int i : shape)
		{
			size *= i;
		}
		this.size = size;
		this.itemSize = size+ndim;
	}
	private int calculateDimensions(Object arr, int level) throws ShapeException {
        if (!arr.getClass().isArray()) {
            return ndim;
        }
        int length = Array.getLength(arr);
        if(shape.size() > level)
        {
        	if(length != shape.get(level))
        	{
        		throw new ShapeException(ExceptionMessages.getShapeException(ndim, shape));
        	}
        }
        else {
        	shape.add(length);
            ndim++;
        }
        int previousDim = 0;
        Class<?> previousClass = null;
        if (length > 0) {
            Object firstElement = Array.get(arr, 0);
            if (firstElement != null) {
                previousClass = firstElement.getClass();
            }
        }

        for (int i = 0; i < length; i++) {
            Object element = Array.get(arr, i);

            if (element != null && element.getClass().isArray()) {
                if (previousClass != null && previousClass.isArray()) {
                    int dim = calculateDimensions(element, level+1);
                    if(i == 0)
                    {
                    	previousDim = dim;
                    }
                    else if(dim != previousDim)
                    {
                    	throw new ShapeException(ExceptionMessages.getShapeException(ndim, shape));
                    }
                    
                } else {
                    throw new ShapeException(ExceptionMessages.getShapeException(ndim, shape));
                }
            } else {
                if (previousClass != null && previousClass.isArray()) {
                	throw new ShapeException(ExceptionMessages.getShapeException(ndim, shape));
                }
            }
        }
        return ndim;
    }
	public T[] getArray() {return this.array;}
	public long size() {return size;}
	public long itemSize() {return this.itemSize;}
	public void printArray() {System.out.println(Arrays.deepToString(array));}
	public int ndim() {
		System.out.println(ndim);
		return this.ndim;
	};
	public List<Integer> shape() {
//		System.out.println(shape.toString());
		return shape;
	}
	private void flattenRecursive(Object currentArray, List<Object> flatList) {
	    if (currentArray.getClass().isArray()) {
	        int length = Array.getLength(currentArray);
	        for (int i = 0; i < length; i++) {
	            Object element = Array.get(currentArray, i);
	            flattenRecursive(element, flatList); 
	        }
	    } else {
	        flatList.add(currentArray);
	    }
	}
	@SuppressWarnings("rawtypes")
	public NDArray flatten() throws ShapeException {
		List<Object> flatList = new ArrayList<>();
	    flattenRecursive(this.array, flatList);
	    Object[] flattenedArray = flatList.toArray(new Object[flatList.size()]);
		NDArray flatNDArray = new NDArray(flattenedArray);
		return flatNDArray;
	}
	public NDArray<T> transpose() throws ShapeException {
	    int[] axes = new int[ndim];
	    for (int i = 0; i < ndim; i++) {
	        axes[i] = ndim - 1 - i;
	    }
	    if (axes.length != ndim) {
	        throw new ShapeException("Axes don't match array dimensions.");
	    }
	    Object transposedArray = transposeRecursive(array, axes, new int[0]);
	    NDArray<T> transposedNDArray = new NDArray<>((T[]) transposedArray);
	    return transposedNDArray;
	}

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
	        Object newArr[]=new Object[dimSize];
	        for (int i = 0; i < dimSize; i++) {
	            int[] newIndices = Arrays.copyOf(currentIndices, currentIndices.length + 1);
	            newIndices[currentIndices.length] = i;
	            Object value = transposeRecursive(arr, axes, newIndices);
	            newArr[i] = value;
	        }
	        return newArr;
	    }
	}
	public NDArray<T> reshape(int...newShape) throws ShapeException
	{
		long newSize = 1;
		ArrayList<Integer> outputShape = new ArrayList<>();
		for(int dim : newShape)
		{
			outputShape.add(dim);
			newSize *= dim;
		}
		if(this.size != newSize) {
			throw new ShapeException(ExceptionMessages.shapeMismatchedException(size, Arrays.toString(newShape)));
		}
		List<Object> flatList = new ArrayList<>();
	    flattenRecursive(this.array, flatList);
	    Object reshapedArray = buildArrayFromFlatList(flatList, newShape, 0);
	    NDArray<T> reshapedNDArray = new NDArray<>((T[]) reshapedArray);
	    return reshapedNDArray;
		
	}
	
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
	private Class<?> getComponentType(Object obj) {
	    if (obj == null) {
	        return Object.class;
	    }
	    Class<?> cls = obj.getClass();
	    while (cls.isArray()) {
	        cls = cls.getComponentType();
	    }
	    return cls;
	}


		//Arithmatic Operations : ADDITION
	
		public NDArray<?> add(NDArray<?> other) throws ShapeException {
			
			int[] broadcastedShape = broadcastShapes(this.shape, other.shape);
			
			int[] thisStrides = this.strides();
			int[] otherStrides = other.strides();
			int[] resultStrides = strides(broadcastedShape);
			
			int totalElements = 1;
		    for (int dim : broadcastedShape) {
		        totalElements *= dim;
		    }
		    List<T> resultData = new ArrayList<>(totalElements);
		    for (int idx = 0; idx < totalElements; idx++) {
		        int[] multiDimIndex = getMultiDimIndex(idx, broadcastedShape, resultStrides);

		        T val1 = getValueAtFlatIndex(this.array, multiDimIndex, this.shape, thisStrides);
		        T val2 = getValueAtFlatIndex((T[])other.array, multiDimIndex, other.shape, otherStrides);
		        System.out.println("value to T1 -> "+val1);
		        System.out.println("value to T2 -> "+val2);
//		        resultData.add(1);
		    }
		    NDArray<Number> resultNDArray = new NDArray(resultData.toArray());
		    return resultNDArray;
		}
		
		private T getValueAtFlatIndex(T[] data, int[] indices, List<Integer> shape, int[] strides) {
		    if (data.length == 1) {
		        return data[0];
		    }
		    int flatIndex = 0;
		    int offset = indices.length - shape.size();
		    for (int i = 0; i < shape.size(); i++) {
		        int idx = indices[offset + i];
		        if (shape.get(i) == 1) {
		            idx = 0;
		        }
		        flatIndex += idx * strides[i];
		    }
		    return data[flatIndex];
		}


		private int[] getMultiDimIndex(int flatIndex, int[] shape, int[] strides) {
			int[] indices = new int[shape.length];
			for (int i = 0; i < shape.length; i++) {
				indices[i] = (flatIndex / strides[i]) % shape[i];
			}
			return indices;
		}


		private void addRecursive(Object arr1, Object arr2, List<Integer> shape1, List<Integer> shape2,
							  List<Integer> resultShape, int[] indices, Object result) {
		int dim = indices.length;
		if (dim == resultShape.size()) {
			// Base case: perform the addition of scalar elements
			Number val1 = getValueAt(arr1, indices, shape1);
			Number val2 = getValueAt(arr2, indices, shape2);
			Double sum = val1.doubleValue() + val2.doubleValue();
			setValueAt(result, indices, sum);
		} else {
			int size = resultShape.get(dim);
			for (int i = 0; i < size; i++) {
				int[] newIndices = Arrays.copyOf(indices, indices.length + 1);
				newIndices[indices.length] = i;
				addRecursive(arr1, arr2, shape1, shape2, resultShape, newIndices, result);
			}
		}
		}
		private Number getValueAt(Object arr, int[] indices, List<Integer> shape) {
		    Object current = arr;
		    int ndimArr = shape.size();
		    int ndimIndices = indices.length;
		    int ndimDiff = ndimIndices - ndimArr; // Difference in dimensions

		    for (int i = 0; i < ndimArr; i++) {
		        int idx = indices[ndimDiff + i];
		        int dimSize = shape.get(i);
		        if (dimSize == 1) {
		            idx = 0; // Broadcasting dimension
		        }
		        current = Array.get(current, idx);
		    }
		    if (current instanceof Number) {
		        return (Number) current;
		    } else {
		        throw new IllegalArgumentException("Array contains non-numeric elements.");
		    }
		}

		private void setValueAt(Object arr, int[] indices, Double value) {
			Object current = arr;
			for (int i = 0; i < indices.length - 1; i++) {
				current = Array.get(current, indices[i]);
			}
			Array.set(current, indices[indices.length - 1], value);
		}
		private Object createArrayOfShape(List<Integer> shape, Class<?> componentType) {
		    int[] dimensions = shape.stream().mapToInt(Integer::intValue).toArray();
		    Object array = Array.newInstance(componentType, dimensions);
		    if (dimensions.length > 1) {
		        initializeArray(array, 0, dimensions, componentType);
		    }
		    return array;
		}
		private void initializeArray(Object array, int dimension, int[] shape, Class<?> componentType) {
		    int actualLength = Array.getLength(array);
		    if (dimension == shape.length - 1) {
		        // Base case: elements are of componentType, no further initialization needed
		        return;
		    }
		    int length = shape[dimension];
		    if (length != actualLength) {
		        throw new IllegalStateException("Mismatch between shape and actual array length at dimension " + dimension);
		    }
		    for (int i = 0; i < actualLength; i++) {
		        Object subArray = Array.newInstance(componentType, shape[dimension + 1]);
		        Array.set(array, i, subArray);
		        initializeArray(subArray, dimension + 1, shape, componentType);
		    }
		}
		public int[] strides(int[] shape) {
		    int[] strides = new int[shape.length];
			int stride = getElementSize(Double.class);
		    for (int i = shape.length - 1; i >= 0; i--) {
		        strides[i] = stride;
		        stride *= shape[i];
		    }
		    return strides;
		}
		public int[] strides() {
			int[] strides = new int[shape.size()];
			int stride = getElementSize(shape.get(0).getClass());

			for (int i = shape.size() - 1; i >= 0; i--) {
				strides[i] = stride;
				stride *= shape.get(i);
			}
			return strides;
		}



//Dimension: 0, Expected Length: 2, Actual Length: 2

}