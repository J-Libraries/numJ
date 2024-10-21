package com.library.numj.operations;

import com.library.numj.NDArray;
import com.library.numj.NumJ;
import com.library.numj.Utils;
import com.library.numj.enums.OperationType;
import com.library.numj.exceptions.ShapeException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.IntStream;

import static com.library.numj.ExceptionMessages.unsupportedOperation;

/**
 * The {@code ArithmaticOperations} class provides methods to perform element-wise arithmetic operations
 * on {@link NDArray} objects. It supports operations like addition, subtraction, multiplication,
 * and division with broadcasting capabilities similar to NumPy.
 *
 */
@SuppressWarnings("unchecked")
public class ArithmaticOperations {
    /** Utility instance for helper methods like broadcasting and indexing. */
    Utils utils;

    /**
     * Constructs an instance of {@code ArithmaticOperations} and initializes utilities.
     */
    public ArithmaticOperations() {
        utils = new Utils();
    }

    /**
     * Performs the specified arithmetic operation on two NDArrays with broadcasting support.
     *
     * @param arr1      The first NDArray operand.
     * @param arr2      The second NDArray operand.
     * @param operation The type of arithmetic operation to perform.
     * @return A new NDArray containing the result of the operation.
     * @throws ShapeException If the shapes of arr1 and arr2 are incompatible for broadcasting.
     */
    public <T, R> NDArray<R> operate(NDArray<T> arr1, NDArray<T> arr2, OperationType operation) throws ShapeException {
        int[] broadcastedShape = utils.broadcastShapes(arr1.shape(), arr2.shape());
        long totalElementsLong = Arrays.stream(broadcastedShape).reduce(1, (a, b) -> a*b);
        int[] arr1Strides = arr1.strides();
        int[] arr2Strides = arr2.strides();

        int totalElements = (int)totalElementsLong;

        T[] flatArr1 = (T[]) arr1.flatten().getArray();
        T[] flatArr2 = (T[]) arr2.flatten().getArray();
        // Get element sizes for proper indexing
        int elementSize1 = utils.getElementSize(Array.get(flatArr1, 0).getClass());
        int elementSize2 = utils.getElementSize(Array.get(flatArr2, 0).getClass());

        // Initialize the output array
        R[] outputArray = (R[]) new Object[totalElements];

        int arr1Offset = broadcastedShape.length - arr1.shape().size();
        int arr2Offset = broadcastedShape.length - arr2.shape().size();

        // Perform the operation element-wise
//        for (int i = 0; i < totalElements; i++){
            IntStream.range(0, totalElements).forEach(i ->{
            // Compute multi-dimensional indices for broadcasting
            int[] multiDimIndices = utils.getMultiDimIndices(i, broadcastedShape);

            // Adjust indices for arr1 based on its shape
            int[] arr1Indices = new int[arr1.shape().size()];

            IntStream.range(0, arr1.shape().size()).parallel().forEach(index -> {
                if (arr1.shape().get(index) == 1) {
                    arr1Indices[index] = 0;
                } else {
                    arr1Indices[index] = multiDimIndices[arr1Offset + index];
                }
            });

            int[] arr2Indices = new int[arr2.shape().size()];
            IntStream.range(0, arr2.shape().size()).parallel().forEach(index -> {
                if (arr2.shape().get(index) == 1) {
                    arr2Indices[index] = 0;
                } else {
                    arr2Indices[index] = multiDimIndices[arr2Offset + index];
                }
            });

            // Compute flat indices for the operands
            int arr1FlatIndex = utils.getFlatIndex(arr1Indices, arr1Strides) / elementSize1;
            int arr2FlatIndex = utils.getFlatIndex(arr2Indices, arr2Strides) / elementSize2;

            // Perform the operation based on the type of elements
            if (flatArr1[arr1FlatIndex] instanceof Number && flatArr2[arr2FlatIndex] instanceof Number) {
                Number v1 = (Number) flatArr1[arr1FlatIndex];
                Number v2 = (Number) flatArr2[arr2FlatIndex];

                boolean isFloatingPoint = (v1 instanceof Double || v1 instanceof Float || v2 instanceof Double || v2 instanceof Float);
                outputArray[i] = (R) (isFloatingPoint ? getFloatingPointResult(v1, v2, operation) : getNumericResult(v1, v2, operation));
            } else {
                outputArray[i] = (R) stringOperation(flatArr1[arr1FlatIndex].toString(), flatArr2[arr2FlatIndex].toString(), operation);
            }
        });

        // Construct and return the result NDArray with the broadcasted shape
        return  new NumJ().array((R) outputArray).reshape(broadcastedShape);
    }

    /**
     * Performs arithmetic operations on floating-point numbers.
     *
     * @param v1 The first operand.
     * @param v2 The second operand.
     * @param o  The operation type.
     * @return The result of the operation as a {@code Number}.
     */
    private Number getFloatingPointResult(Number v1, Number v2, OperationType o) {
        if (utils.getElementSize(v1.getClass()) == utils.getElementSize(v2.getClass()) && v1 instanceof Float) {
            switch (o) {
                case ADDITION:
                    return v1.floatValue() + v2.floatValue();
                case SUBTRACTION:
                    return v1.floatValue() - v2.floatValue();
                case MULTIPLICATION:
                    return v1.floatValue() * v2.floatValue();
                case DIVISION:
                    return v1.floatValue() / v2.floatValue();
                default:
                    throw new UnsupportedOperationException(unsupportedOperation);
            }
        }
        switch (o) {
            case ADDITION:
                return v1.doubleValue() + v2.doubleValue();
            case SUBTRACTION:
                return v1.doubleValue() - v2.doubleValue();
            case MULTIPLICATION:
                return v1.doubleValue() * v2.doubleValue();
            case DIVISION:
                return v1.doubleValue() / v2.doubleValue();
            default:
                throw new UnsupportedOperationException(unsupportedOperation);
        }
    }

    /**
     * Performs arithmetic operations on numeric types with the dominating data type.
     *
     * @param v1         The first operand.
     * @param v2         The second operand.
     * @param dominating The dominating data type.
     * @param o          The operation type.
     * @return The result of the operation as a {@code Number}.
     */
    private Number getTypedValue(Number v1, Number v2, Number dominating, OperationType o) {
        switch (o) {
            case ADDITION:
                return dominating instanceof Byte ? v1.byteValue() + v2.byteValue() :
                        dominating instanceof Short ? v1.shortValue() + v2.shortValue() :
                                dominating instanceof Integer ? v1.intValue() + v2.intValue() :
                                        v1.longValue() + v2.longValue();

            case SUBTRACTION:
                return dominating instanceof Byte ? v1.byteValue() - v2.byteValue() :
                        dominating instanceof Short ? v1.shortValue() - v2.shortValue() :
                                dominating instanceof Integer ? v1.intValue() - v2.intValue() :
                                        v1.longValue() - v2.longValue();

            case MULTIPLICATION:
                return dominating instanceof Byte ? ((short) v1.byteValue() << v2.byteValue()) :
                        dominating instanceof Short ? ((int) v1.shortValue() << v2.shortValue()) :
                                dominating instanceof Integer ? ((long) v1.intValue() << v2.intValue()) :
                                        v1.longValue() << v2.longValue();

            case DIVISION:
                return dominating instanceof Byte ? (v1.byteValue() >> v2.byteValue()) :
                        dominating instanceof Short ? (v1.shortValue() >> v2.shortValue()) :
                                dominating instanceof Integer ? (v1.intValue() >> v2.intValue()) :
                                        v1.longValue() >> v2.longValue();

            case MODULO:
                return dominating instanceof Byte ? v1.byteValue() % v2.byteValue() :
                        dominating instanceof Short ? v1.shortValue() % v2.shortValue() :
                                dominating instanceof Integer ? v1.intValue() % v2.intValue() :
                                        v1.longValue() % v2.longValue();

            default:
                throw new UnsupportedOperationException(unsupportedOperation);
        }

    }

    /**
     * Determines the appropriate numeric result based on the dominating data type.
     *
     * @param v1 The first operand.
     * @param v2 The second operand.
     * @param o  The operation type.
     * @return The result of the operation as a {@code Number}.
     */
    private Number getNumericResult(Number v1, Number v2, OperationType o) {
        if (utils.getElementSize(v1.getClass()) > utils.getElementSize(v2.getClass())) {
            return getTypedValue(v1, v2, v1, o);
        } else {
            return getTypedValue(v1, v2, v2, o);
        }
    }

    /**
     * Performs string concatenation or throws an exception for unsupported operations.
     *
     * @param v1 The first string operand.
     * @param v2 The second string operand.
     * @param o  The operation type.
     * @return The result of the operation as a {@code String}.
     * @throws UnsupportedOperationException If the operation is not supported for strings.
     */
    private String stringOperation(String v1, String v2, OperationType o) {
        if (o == OperationType.ADDITION) {
            return v1 + v2;
        }
        throw new UnsupportedOperationException(unsupportedOperation);
    }
}
