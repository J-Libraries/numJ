package com.library.numj;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.library.numj.exceptions.ShapeException;



@SuppressWarnings("unchecked")
public final class NDArray<T>{
	private final T[] array;
	private int ndim;
	private final List<Integer> shape = new ArrayList<>();
	private long size = 0;
	private long itemSize = 0;
	Utils<T> utils;
	
	NDArray(T[] data) throws ShapeException
	{
		utils = new Utils<>();
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
		return this.ndim;
	}
	public List<Integer> shape() {
		return shape;
	}
	private void flattenRecursive(T currentArray, List<Object> flatList) {
	    if (currentArray.getClass().isArray()) {
	        int length = Array.getLength(currentArray);
	        for (int i = 0; i < length; i++) {
	            T element = (T) Array.get(currentArray, i);
	            flattenRecursive(element, flatList); 
	        }
	    } else {
	        flatList.add(currentArray);
	    }
	}
	@SuppressWarnings("rawtypes")
	public NDArray flatten() throws ShapeException {
		List<Object> flatList = new ArrayList<>();
	    flattenRecursive((T) this.array, flatList);
	    Object[] flattenedArray = flatList.toArray(new Object[0]);
        return new NDArray(flattenedArray);
	}
	public NDArray<T> transpose() throws ShapeException {
	    int[] axes = new int[ndim];
	    for (int i = 0; i < ndim; i++) {
	        axes[i] = ndim - 1 - i;
	    }
        Object transposedArray = transposeRecursive(array, axes, new int[0]);
        return new NDArray<>((T[]) transposedArray);
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
	        Object[] newArr =new Object[dimSize];
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
		for(int dim : newShape)
		{
			newSize *= dim;
		}
		if(this.size != newSize) {
			throw new ShapeException(ExceptionMessages.shapeMismatchedException(size, Arrays.toString(newShape)));
		}
		List<Object> flatList = new ArrayList<>();
	    flattenRecursive((T) this.array, flatList);
	    Object reshapedArray = buildArrayFromFlatList(flatList, newShape, 0);
        return new NDArray<>((T[]) reshapedArray);
		
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
	public int[] strides(int[] shape) {
		int[] strides = new int[shape.length];
		int stride = utils.getElementSize(Double.class);
		for (int i = shape.length - 1; i >= 0; i--) {
			strides[i] = stride;
			stride *= shape[i];
		}
		return strides;
	}
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
