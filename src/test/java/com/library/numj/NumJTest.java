package com.library.numj;

import com.library.numj.enums.DType;
import com.library.numj.exceptions.InvalidShapeException;
import com.library.numj.exceptions.ShapeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class contains unit tests for the NumJ library.
 * It covers various operations such as array creation, arithmetic operations,
 * and matrix manipulations. Each method represents a specific test case.
 *
 * @author Anmol Raghuvanshi(ershadow786)
 */

class NumJTest {

    /**
     * Instance of NumJ to be tested.
     */
    private NumJ numJ;

    /**
     * Sets up the NumJ instance before each test.
     */
    @BeforeEach
    void setUp() {
        numJ = new NumJ();
    }

    /**
     * Tests the default constructor of NumJ.
     */
    @Test
    void testDefaultConstructor() {
        assertNotNull(numJ.arithmaticOperations);
        assertNotNull(numJ.arrayModification);
        assertNotNull(numJ.arrayCreation);
    }

    /**
     * Tests array creation with a single value.
     *
     * @param value The value used to create the array.
     */
    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 1",
            "3, 1"
    })
    <T> void testArrayCreationWithSingleValue(T value) throws Exception {
        NumJ numJ = new NumJ();
        NDArray<Integer> result = numJ.array(value);
        assertNotNull(result);
        assertEquals(0, result.ndim());
        assertEquals(1L, result.size());
        //assertArrayEquals(new Integer[]{value}, (Integer[]) result.getArray());
        assertEquals(value, result.getArray());
    }

    /**
     * Tests array creation with a given shape and data type.
     *
     * @param value The initial value of the array.
     * @param expectedShape The expected shape of the resulting array.
     * @param dType The data type of the array elements.
     */
    @ParameterizedTest
    @MethodSource("provideArrayCreationData")
    <T> void testArrayCreationWithShape(T value, int expectedShape, DType dType) throws Exception {
        NumJ numJ = new NumJ();
        int[] shape = {expectedShape, expectedShape};

        NDArray<T> result = numJ.array(value, shape, 2, dType);

        assertNotNull(result);
        assertEquals(2, result.ndim());
        assertArrayEquals(shape, result.shape().stream().mapToInt(Integer::intValue).toArray());
    }


    /**
     * Tests zeros array creation with different shapes and data types.
     *
     * @param zeroValue The zero value for the array.
     * @param shape The shape of the resulting array.
     * @param dType The data type of the array elements.
     */
    @ParameterizedTest
    @MethodSource("provideDataForZerosAndOnes")
    <T> void testZerosCreation(T zeroValue, int[] shape, DType dType) throws Exception {
        NumJ numJ = new NumJ();
        NDArray<T> result = numJ.zeros(shape, dType);
        assertNotNull(result);
        assertEquals(2, result.ndim());
        assertArrayEquals((Object[]) Collections.nCopies(shape[0] * shape[1], zeroValue).toArray(), (Object[]) result.flatten().getArray());
    }


    /**
     * Tests ones array creation with different shapes and data types.
     *
     * @param oneValue The one value for the array.
     * @param shape The shape of the resulting array.
     * @param dType The data type of the array elements.
     */

    @ParameterizedTest
    @MethodSource("provideDataForZerosAndOnes")
    <T> void testOnesCreation(T oneValue, int[] shape, DType dType) throws Exception {
        NumJ numJ = new NumJ();
        NDArray<T> result = numJ.ones(shape, dType);
        assertNotNull(result);
        assertEquals(2, result.ndim());
        assertArrayEquals((Object[]) java.util.Collections.nCopies(shape[0] * shape[1], oneValue).toArray(), (Object[]) result.flatten().getArray());
    }


    /**
     * Tests arange function with different parameters.
     *
     * @param start The starting value of the sequence.
     * @param end The ending value of the sequence.
     * @param expectedSize The expected size of the resulting array.
     */
    @ParameterizedTest
    @CsvSource({
            "0, 5, 5",
            "1, 6, 5",
            "2, 8, 6"
    })
    void testArange(int start, int end, int expectedSize) throws Exception {
        NDArray<Integer[]> result = numJ.arange(start, end);
        assertNotNull(result);
        assertEquals(1, result.ndim());
        assertEquals(expectedSize, result.size());
        Integer[] expectedData = generateExpectedArangeData(start, end).toArray(new Integer[0]);
        assertArrayEquals(expectedData, result.getArray());
    }


    /**
     * Generates expected data for arange tests.
     *
     * @param start The starting value of the sequence.
     * @param end The ending value of the sequence.
     * @return A list of integers representing the expected arange result.
     */
    private List<Integer> generateExpectedArangeData(int start, int end) {
        List<Integer> data = new ArrayList<>();
        for (int i = start; i < end; i++) {
            data.add(i);
        }
        return data;
    }

    /**
     * Tests arange function with shape and skip parameters.
     */
    @Test
    void testArangeWithShapeAndSkip() throws Exception {
        int[] shape = {3};
        NDArray<Integer[]> result = numJ.arange(0, 6, 2, shape);
        assertNotNull(result);
        assertEquals(1, result.ndim());
        assertEquals(3L, result.size());
        Integer[] expectedData = {0, 2, 4};
        assertArrayEquals(expectedData, result.getArray());
    }


    /*private void assertArrayCreation(NDArray<Integer> result, Integer[] expected) {
        assertEquals(expected.length, result.shape()[0]);
        assertArrayEquals(expected, result.data());
    }*/

    /**
     * Tests addition operation between two arrays.
     *
     * @param data1 The first array for addition.
     * @param data2 The second array for addition.
     * @param expected The expected result of the addition.
     */
    @ParameterizedTest
    //@MethodSource("provideDataForArithmeticOperations")
    @MethodSource("provideValidDataForAddition")
    <T> void testAddition(T data1, T data2, T expected) throws Exception {
        NumJ numJ = new NumJ();
        NDArray<T> arr1 = numJ.array(data1);
        NDArray<T> arr2 = numJ.array(data2);
        NDArray<T> result = numJ.add(arr1, arr2);
        assertArrayEquals((Object[]) expected, (Object[]) result.getArray());
    }

    /**
     * Tests addition operation between two arrays.
     *
     * @param data1 The first array for Subtraction.
     * @param data2 The second array for Subtraction.
     * @param expected The expected result of the Subtraction.
     */
    @ParameterizedTest
//    @MethodSource("provideDataForArithmeticOperations")
    @MethodSource("provideValidDataForSubtraction")
    <T> void testSubtraction(T data1, T data2, T expected) throws Exception {
        NumJ numJ = new NumJ();
        NDArray<T> arr1 = numJ.array(data1);
        NDArray<T> arr2 = numJ.array(data2);
        NDArray<T> result = numJ.subtract(arr1, arr2);
        assertArrayEquals((Object[]) expected, (Object[]) result.getArray());
    }
    /**
     * Tests addition operation between two arrays.
     *
     * @param data1 The first array for Multiplication.
     * @param data2 The second array for Multiplication.
     * @param expected The expected result of the Multiplication.
     */

    @ParameterizedTest
    @MethodSource("provideValidDataForMultiplication")
    <T> void testMultiplication(T data1, T data2, T expected) throws Exception {
        NumJ numJ = new NumJ();
        NDArray<T> arr1 = numJ.array(data1);
        NDArray<T> arr2 = numJ.array(data2);
        NDArray<T> result = numJ.multiply(arr1, arr2);
        arr1.printArray();
        arr2.printArray();
        result.printArray();
        assertArrayEquals((Object[]) expected, (Object[]) result.getArray());
    }

    /**
     * Tests addition operation between two arrays.
     *
     * @param data1 The first array for division.
     * @param data2 The second array for division.
     * @param expected The expected result of the division.
     */
    @ParameterizedTest
    @MethodSource("provideValidDataForDivision")
    <T> void testDivision(T data1, T data2, T expected) throws Exception {
        NumJ numJ = new NumJ();
        NDArray<T> arr1 = numJ.array(data1);
        NDArray<T> arr2 = numJ.array(data2);
        NDArray<T> result = numJ.divide(arr1, arr2);
        assertArrayEquals((Object[]) expected, (Object[]) result.getArray());
    }


    /**
     * Tests the transpose operation on an array.
     *
     * @param data The input array to be transposed.
     * @param expected The expected result after transposing.
     */
    @ParameterizedTest
    @MethodSource("provideDataForTranspose")
   <T> void testTranspose(T data , T expected) throws Exception {
        NumJ numJ = new NumJ();
        NDArray<T> arr = numJ.array(data);
        NDArray<T> transposed = numJ.transpose(arr);
        assertArrayEquals((Object[]) expected, (Object[]) transposed.getArray());
    }

    /**
     * Tests that an IllegalArgumentException is thrown when creating an arange with a negative range.
     */
    @Test
    void testArangeWithNegativeRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            numJ.arange(-1);
        }, "Expected IllegalArgumentException when range is negative");
    }

    /**
     * Tests that a ShapeException is thrown when creating zeros array with an invalid shape.
     *
     * @param dType The data type of the array elements.
     */
    @ParameterizedTest
    @MethodSource("provideDataForZeros")
    <T> void testZerosWithInvalidShape(DType dType) {
        NumJ numJ = new NumJ();
        int[] invalidShape = {0, 2};
        assertThrows(ShapeException.class, () -> {
            numJ.zeros(invalidShape, dType);
        }, "Expected ShapeException for invalid shape");
    }

    /**
     * Tests that an IllegalArgumentException is thrown when creating eye matrix with an invalid diagonal index.
     */
    @Test
    void testEyeWithInvalidDiagonal() {
        assertThrows(IllegalArgumentException.class, () -> {
            numJ.eye(3, 3, 4);  // Invalid diagonal index
        }, "Expected IllegalArgumentException for invalid diagonal index");
    }

    /**
     * Tests that a ShapeException is thrown when adding arrays with mismatched shapes.
     *
     * @param data1 The first array for addition.
     * @param data2 The second array for addition.
     */
    @ParameterizedTest
    @MethodSource("provideDataForAddition")
    <T> void testAdditionWithMismatchedShapes(T data1, T data2) throws ShapeException {
        NumJ numJ = new NumJ();

        NDArray<T> arr1 = numJ.array(data1);
        NDArray<T> arr2 = numJ.array(data2);

        assertThrows(ShapeException.class, () -> {
            numJ.add(arr1, arr2);
        }, "Expected ShapeException for mismatched shapes");
    }

    /**
     * Tests that an ArithmeticException is thrown when dividing by zero.
     *
     * @param data1 The dividend array.
     * @param data2 The divisor array.
     */
    @ParameterizedTest
    @MethodSource("provideDataForDivision")
    <T> void testDivisionByZero(T data1, T data2) throws ShapeException {
        NumJ numJ = new NumJ();

        NDArray<T> arr1 = numJ.array(data1);
        NDArray<T> arr2 = numJ.array(data2);

        assertThrows(ArithmeticException.class, () ->
        {
            numJ.divide(arr1, arr2);
        }, "Expected ArithmeticException for division by zero");
    }

    /**
     * Tests that a ShapeException is thrown when creating zeros array with an empty shape.
     */
    @Test
    public void testZerosWithEmptyShape() {
        NumJ numJ = new NumJ();
        int[] emptyShape = {};
        assertThrows(InvalidShapeException.class, () -> {
            numJ.zeros(emptyShape);
        });
    }

    /**
     * Tests arange function with zero skip parameter.
     *
     * @throws ShapeException If the shape is invalid.
     */
    @Test
    public void testArangeWithZeroSkip() throws ShapeException {
        NumJ numJ = new NumJ();
        NDArray<Integer[]> result = numJ.arange(0, 5, DType.INT32, 0);
        assertArrayEquals(new Integer[]{0, 1, 2, 3, 4}, result.getArray());
    }



    /**
     * Provides data for zeros array creation tests.
     *
     * @return A stream of arguments for testing zeros creation.
     */
    static Stream<Arguments> provideDataForZerosAndOnes() {
        return Stream.of(
                Arguments.of(0, new int[]{2, 2}, DType.INT32),
                Arguments.of(1.0, new int[]{2, 2}, DType.FLOAT64)
                //Arguments.of("0", new int[]{2, 2}, DType.STR)
        );
    }

    static Stream<Arguments> provideValidDataForAddition() {
        return Stream.of(
                Arguments.of(new Integer[][]{{1, 2}, {3, 4}}, new Integer[][]{{5, 6}, {7, 8}}, new Integer[][]{{6, 8}, {10, 12}}, DType.INT32),
                Arguments.of(new Double[][]{{1.0, 2.0}, {3.0, 4.0}}, new Double[][]{{5.0, 6.0}, {7.0, 8.0}}, new Double[][]{{6.0, 8.0}, {10.0, 12.0}}, DType.FLOAT64)
        );
    }

    static Stream<Arguments> provideValidDataForMultiplication() {
        return Stream.of(
                Arguments.of(new Integer[][]{{1, 2}, {3, 4}}, new Integer[][]{{5, 6}, {7, 8}}, new Integer[][]{{5, 12}, {21, 32}}, DType.INT32),
                Arguments.of(new Double[][]{{1.0, 2.0}, {3.0, 4.0}}, new Double[][]{{5.0, 6.0}, {7.0, 8.0}}, new Double[][]{{5.0, 12.0}, {21.0, 32.0}}, DType.FLOAT64)
        );
    }

    static Stream<Arguments> provideValidDataForSubtraction() {
        return Stream.of(
                Arguments.of(new Integer[][]{{1, 2}, {3, 4}}, new Integer[][]{{5, 6}, {7, 8}}, new Integer[][]{{-4, -4}, {-4, -4}}, DType.INT32),
                Arguments.of(new Double[][]{{1.0, 2.0}, {3.0, 4.0}}, new Double[][]{{5.0, 6.0}, {7.0, 8.0}}, new Double[][]{{-4.0, -4.0}, {-4.0, -4.0}}, DType.FLOAT64)
        );
    }

    static Stream<Arguments> provideValidDataForDivision() {
        return Stream.of(
                Arguments.of(new Integer[][]{{10, 20}, {30, 40}}, new Integer[][]{{5, 4}, {3, 2}}, new Integer[][]{{2, 5}, {10, 20}}, DType.INT32),
                Arguments.of(new Double[][]{{10.0, 20.0}, {30.0, 40.0}}, new Double[][]{{5.0, 4.0}, {3.0, 2.0}}, new Double[][]{{2.0, 5.0}, {10.0, 20.0}}, DType.FLOAT64)
        );
    }

    static Stream<Arguments> provideDataForTranspose() {
        return Stream.of(
                Arguments.of(new Integer[][]{{1, 2}, {3, 4}}, new Integer[][]{{1, 3}, {2, 4}}),
                Arguments.of(new Double[][]{{1.0, 2.0}, {3.0, 4.0}}, new Double[][]{{1.0, 3.0}, {2.0, 4.0}})
        );
    }

    static Stream<Arguments> provideDataForZeros() {
        return Stream.of(
                Arguments.of(DType.INT32),
                Arguments.of(DType.FLOAT64)
                //Arguments.of(DType.STR)
        );
    }

    static Stream<Arguments> provideDataForAddition() {
        return Stream.of(
                Arguments.of(new Integer[][]{{1, 2}, {3, 4}}, new Integer[]{5, 6, 7}, DType.INT32),
                Arguments.of(new Double[][]{{1.1, 2.2}, {3.3, 4.4}}, new Double[]{5.5, 6.6, 7.7}, DType.FLOAT64)
        );
    }

    static Stream<Arguments> provideDataForDivision() {
        return Stream.of(
                Arguments.of(new Integer[][]{{10, 20}, {30, 40}}, new Integer[][]{{0, 2}, {0, 10}}, DType.INT32),
                Arguments.of(new Double[][]{{10.5, 20.5}, {30.5, 40.5}}, new Double[][]{{0.0, 2.0}, {0.0, 10.0}}, DType.FLOAT64)
        );
    }

    static Stream<Arguments> provideArrayCreationData() {
        return Stream.of(
                Arguments.of(1, 2, DType.INT32),
                Arguments.of(2.0, 3, DType.FLOAT64)
               // Arguments.of("3", 4, DType.STR)
        );
    }

    static Stream<Arguments> shapeProvider() {
        return Stream.of(
                Arguments.of(1, 2, DType.INT32),
                Arguments.of(3.14, 3, DType.FLOAT64)
                //Arguments.of("a", 4, DType.STR)
        );
    }

}
