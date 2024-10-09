package com.library.numj.operations;

import com.library.numj.NDArray;
import com.library.numj.NumJ;
import com.library.numj.Utils;
import com.library.numj.enums.OperationType;
import com.library.numj.exceptions.ShapeException;


import static com.library.numj.ExceptionMessages.unsupportedOperation;


@SuppressWarnings("unchecked")
public class ArithmaticOperations<T> {
    Utils<T> utils;
    public ArithmaticOperations()
    {
        utils = new Utils<>();
    }
     public NDArray<Object> operate(NDArray<T> arr1, NDArray<T> arr2, OperationType operation) throws ShapeException {
         int[] broadcastedShape = utils.broadcastShapes(arr1.shape(), arr2.shape());
        int[] arr1Strides = arr1.strides();
        int[] arr2Strides = arr2.strides();
        int totalElements = 1;
        for (int dim : broadcastedShape) {
            totalElements *= dim;
        }
        T[] flatArr1 = (T[]) arr1.flatten().getArray();
        T[] flatArr2 = (T[]) arr2.flatten().getArray();
        int elementSize1 = utils.getElementSize(flatArr1[0].getClass());
        int elementSize2 = utils.getElementSize(flatArr2[0].getClass());

        Object[] outputArray = new Object[totalElements];
        for(int i = 0;i<totalElements;i++)
        {
            int[] multiDimIndices = utils.getMultiDimIndices(i, broadcastedShape);
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
            int arr1FlatIndex = utils.getFlatIndex(arr1Indices, arr1Strides)/elementSize1;
            int arr2FlatIndex = utils.getFlatIndex(arr2Indices, arr2Strides)/elementSize2;

            if(flatArr1[arr1FlatIndex] instanceof Number && flatArr2[arr2FlatIndex] instanceof Number)
            {
                Number v1 = (Number) flatArr1[arr1FlatIndex];
                Number v2 = (Number) flatArr2[arr2FlatIndex];

                boolean type = (v1 instanceof Double || v1 instanceof Float || v2 instanceof Double || v2 instanceof Float);
                outputArray[i] = type ? getFloatingPointResult(v1, v2, operation) : getNumericResult(v1, v2, operation);
            }
            else{
                outputArray[i] = stringOperation(flatArr1[arr1FlatIndex].toString(), flatArr2[arr2FlatIndex].toString(), operation);
            }
        }
        return new NumJ().array(outputArray).reshape(broadcastedShape);
    }
    private Number getFloatingPointResult(Number v1, Number v2, OperationType o)
    {
        if(utils.getElementSize(v1.getClass()) == utils.getElementSize(v2.getClass()) && v1 instanceof Float)
        {
            switch (o){
                case ADDITION:
                    return v1.floatValue()+v2.floatValue();
                case SUBTRACTION:
                    return v1.floatValue()-v2.floatValue();
                case MULTIPLICATION:
                    return v1.floatValue()*v2.floatValue();
                case DIVISION:
                    return v1.floatValue()/v2.floatValue();
                default:
                    throw new UnsupportedOperationException(unsupportedOperation);
            }
        }
        switch (o){
            case ADDITION:
                return v1.doubleValue()+v2.doubleValue();
            case SUBTRACTION:
                return v1.doubleValue()-v2.doubleValue();
            case MULTIPLICATION:
                return v1.doubleValue()*v2.doubleValue();
            case DIVISION:
                return v1.doubleValue()/v2.doubleValue();
            default:
                throw new UnsupportedOperationException(unsupportedOperation);
        }
    }
    private Number getTypedValue(Number v1, Number v2, Number dominating, OperationType o)
    {

        if (dominating instanceof Byte) {
            switch (o){
                case ADDITION: return v1.byteValue()+v2.byteValue();
                case SUBTRACTION: return v1.byteValue()-v2.byteValue();
                case MULTIPLICATION: return v1.byteValue()*v2.byteValue();
                case DIVISION: return v1.byteValue()/v2.byteValue();
                case MODULO: return v1.byteValue()%v2.byteValue();
                default: throw new UnsupportedOperationException(unsupportedOperation);
            }
        }
        else if(dominating instanceof Short)
        {
            switch (o){
                case ADDITION: return v1.shortValue()+v2.shortValue();
                case SUBTRACTION: return v1.shortValue() - v2.shortValue();
                case MULTIPLICATION: return v1.shortValue() * v2.shortValue();
                case DIVISION: return v1.shortValue()/v2.shortValue();
                case MODULO: return v1.shortValue()%v2.shortValue();
                default: throw new UnsupportedOperationException(unsupportedOperation);
            }
        }else if(dominating instanceof Integer)
        {
            switch (o) {
                case ADDITION: return v1.intValue() + v2.intValue();
                case SUBTRACTION: return v1.intValue() - v2.intValue();
                case MULTIPLICATION: return v1.intValue() * v2.intValue();
                case DIVISION: return v1.intValue() / v2.intValue();
                case MODULO: return v1.intValue() % v2.intValue();
                default: throw new UnsupportedOperationException("Unsupported operation for Integer.");
            }
        }
        else{
            switch (o) {
                case ADDITION: return v1.longValue() + v2.longValue();
                case SUBTRACTION: return v1.longValue() - v2.longValue();
                case MULTIPLICATION: return v1.longValue() * v2.longValue();
                case DIVISION: return v1.longValue() / v2.longValue();
                case MODULO: return v1.longValue() % v2.longValue();
                default: throw new UnsupportedOperationException("Unsupported operation for Long.");
            }
        }
    }
    private Number getNumericResult(Number v1, Number v2, OperationType o)
    {
        if(utils.getElementSize(v1.getClass()) > utils.getElementSize(v2.getClass()))
        {
           return getTypedValue(v1, v2, v1, o);
        }else{
            return getTypedValue(v1, v2, v2, o);
        }
    }
    private String stringOperation(String v1, String v2, OperationType o){
        if(o == OperationType.ADDITION)
        {
            return v1+v2;
        }
        throw new UnsupportedOperationException(unsupportedOperation);
    }
}
