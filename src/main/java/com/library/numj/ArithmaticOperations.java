package com.library.numj;

import com.library.numj.exceptions.ShapeException;

import java.lang.reflect.Array;

import static com.library.numj.Utils.*;

@SuppressWarnings("unchecked")
class ArithmaticOperations<T> {
    public NDArray add(NDArray<T> arr1, NDArray<T> arr2) throws ShapeException {
        int[] broadcastedShape = broadcastShapes(arr1.shape(), arr2.shape());
        int[] arr1Strides = arr1.strides();
        int[] arr2Strides = arr2.strides();
        int[] resultStrides = arr1.strides(broadcastedShape);
        int totalElements = 1;
        for (int dim : broadcastedShape) {
            totalElements *= dim;
        }
        T[] flatArr1 = (T[]) arr1.flatten().getArray();
        T[] flatArr2 = (T[]) arr2.flatten().getArray();
        Object[] outputArray = new Object[totalElements];//(Object[]) Array.newInstance(Object.class, broadcastedShape);
        for(int i = 0;i<totalElements;i++)
        {
            int[] multiDimIndices = getMultiDimIndices(i, broadcastedShape);
            int[] arr1Indices = new int[arr1.shape().size()];
            int arr1Offset = broadcastedShape.length - arr1.shape().size();
            for (int j = 0; j < arr1.shape().size(); j++) {
                if (arr1.shape().get(j) == 1) {
                    arr1Indices[j] = 0;
                } else {
                    arr1Indices[j] = multiDimIndices[arr1Offset + j];
                }
            }

            int[] arr2Indices = new int[arr2.shape().size()];
            int arr2Offset = broadcastedShape.length - arr2.shape().size();
            for (int j = 0; j < arr2.shape().size(); j++) {
                if (arr2.shape().get(j) == 1) {
                    arr2Indices[j] = 0;
                } else {
                    arr2Indices[j] = multiDimIndices[arr2Offset + j];
                }
            }
            int arr1FlatIndex = getFlatIndex(arr1Indices, arr1Strides);
            int arr2FlatIndex = getFlatIndex(arr2Indices, arr2Strides);
            int elementSize1 = getElementSize(flatArr1[0].getClass());
            int elementSize2 = getElementSize(flatArr2[0].getClass());
            Double val1 = ((Number) flatArr1[arr1FlatIndex/elementSize1]).doubleValue();
            Double val2 = ((Number) flatArr2[arr2FlatIndex/elementSize2]).doubleValue();
            double sum = val1 + val2;
            outputArray[i] = (Object) sum;

        }
        return new NDArray<>(outputArray).reshape(broadcastedShape);
    }
}
