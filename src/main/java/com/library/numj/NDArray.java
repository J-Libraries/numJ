package com.library.numj;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.library.numj.exceptions.ShapeException;

@SuppressWarnings("unchecked")
public final class NDArray<T>{
	private T[] array;
	private int ndim;
	private List<Integer> shape = new ArrayList<Integer>();;
	
	NDArray(T[] data) throws ShapeException
	{
		this.array = data;
		calculateDimensions(data, 0);
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
	public int getLength(T[] array) {return array.length;}
	public void printArray() {System.out.println(Arrays.deepToString(array));};
	public int ndim() {
		System.out.println(ndim);
		return this.ndim;
	};
	public List<Integer> shape() {
		System.out.println(shape.toString());
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
	NDArray<T> arange(int last) throws ShapeException
	{
		Object arr[] = new Object[last];
		Arrays.setAll(array, i -> 0+i);
		return new NDArray(arr);
	}
}
