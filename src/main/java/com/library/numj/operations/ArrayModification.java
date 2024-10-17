package com.library.numj.operations;

import com.library.numj.NDArray;
import com.library.numj.NumJ;
import com.library.numj.Utils;
import com.library.numj.enums.DType;
import com.library.numj.exceptions.ShapeException;

import java.lang.reflect.Array;
import java.util.Arrays;


@SuppressWarnings("unchecked")
public class ArrayModification<T> {
    Utils<T> utils = new Utils<>();
    /**
     * Transposes the array by reversing its axes.
     *
     * @return A new NDArray that is the transposed version of the original array.
     * @throws ShapeException If an error occurs during transposition.
     */
    public <R> NDArray<R> transpose(NDArray<T> array) throws ShapeException {
        int ndim = array.ndim();
        int[] axes = new int[ndim];
        for (int i = 0; i < ndim; i++) {
            axes[i] = ndim - 1 - i;
        }

        R transposedArray =
                transposeRecursive(array, axes, new int[0], utils.getComponentType(array.getArray()));
        return new NumJ<R>().array(transposedArray);
    }

    /**
     * Recursively transposes the array based on the provided axes.
     *
     * @param array            The array to transpose.
     * @param axes           The order of axes for transposition.
     * @param currentIndices The current indices during recursion.
     * @return The transposed array.
     * @throws ShapeException If an error occurs during transposition.
     */
    private <R> R transposeRecursive(NDArray<T> array, int[] axes, int[] currentIndices,Class<?> type) throws ShapeException {
        int ndim = array.ndim();
        if (currentIndices.length == ndim) {
            int[] originalIndices = new int[ndim];
            for (int i = 0; i < ndim; i++) {
                originalIndices[i] = currentIndices[axes[i]];
            }
            R element = (R) array.getArray();
            for (int idx : originalIndices) {
                element = (R) Array.get(element, idx);
            }
            return element;
        } else {
            int axis = currentIndices.length;
            int dimSize = array.shape().get(axes[axis]);
            R newArr = (R) Array.newInstance(type, dimSize);
            for (int i = 0; i < dimSize; i++) {
                int[] newIndices = Arrays.copyOf(currentIndices, currentIndices.length + 1);
                newIndices[currentIndices.length] = i;
                R value = transposeRecursive(array, axes, newIndices, type);
                Array.set(newArr, i, value);
            }
            return newArr;
        }
    }
}
