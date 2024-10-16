package com.library.numj.operations;

import com.library.numj.NDArray;
import com.library.numj.NumJ;
import com.library.numj.enums.DType;
import com.library.numj.exceptions.ShapeException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;

/**
 * The ArrayCreation class provides methods to create NDArray objects filled with zeros or ones.
 * It supports recursive filling of multi-dimensional arrays.
 */
@SuppressWarnings("unchecked")
public class ArrayCreation<T> {
    ForkJoinPool pool = new ForkJoinPool();

    /**
     * Recursively fills an array with the specified value based on the given data type.
     * This method works for multi-dimensional arrays as well.
     *
     * @param array  The array to be filled.
     * @param dType  The data type of the array elements.
     * @param value  The value to fill in the array.
     * @return The filled array.
     */
    private T fillRecursive(T array, DType dType, double value) {
        if (!array.getClass().isArray()) return array;

        int length = Array.getLength(array);
        IntStream.range(0, length).forEach(index -> {
            T element = (T) Array.get(array, index);
            if (element == null) {
                dType.set(array, index, value);
            } else {
                fillRecursive((T) Array.get(array, index), dType, value);
            }
        });
        return array;
    }

    /**
     * Creates an NDArray filled with zeros of the specified shape and data type.
     *
     * @param shape The shape of the NDArray.
     * @param dType The data type of the array elements.
     * @return An NDArray filled with zeros.
     */
    public NDArray<T> zeros(int[] shape, DType dType) {
        T array = (T) Array.newInstance(dType.is(), shape);
        return new NumJ<T>().array(fillRecursive(array, dType, 0), shape, shape.length, dType);
    }

    /**
     * Creates an NDArray filled with ones of the specified shape and data type.
     *
     * @param shape The shape of the NDArray.
     * @param dType The data type of the array elements.
     * @return An NDArray filled with ones.
     */
    public NDArray<T> ones(int[] shape, DType dType) {
        T array = (T) Array.newInstance(dType.is(), shape);
        return new NumJ<T>().array(fillRecursive(array, dType, 1), shape, shape.length, dType);
    }


    /**
     * Creates an identity matrix with specified rows and columns, and a diagonal offset.
     * The matrix is filled using parallel processing to enhance performance.
     *
     * @param rows             The number of rows in the identity matrix.
     * @param cols             The number of columns in the identity matrix.
     * @param identityDiagonal  The diagonal index of the identity matrix (0 for the main diagonal).
     * @param dType            The data type of the elements in the NDArray.
     * @return A new NDArray representing the identity matrix.
     * @throws ShapeException If there is an issue creating the identity matrix.
     */
    public NDArray<T> eye(int rows, int cols, int identityDiagonal, DType dType) throws ShapeException {
        T array = (T) Array.newInstance(dType.is(), new int[]{rows, cols});
        IntStream.range(0, rows).parallel().forEach(index ->{
            int j = index + identityDiagonal;
            if(j >= 0 && j < cols)
            {
                Array.set(Array.get(array, index), j, 1);
            }
        });
        return new NumJ<T>().array(array);
    }
}
