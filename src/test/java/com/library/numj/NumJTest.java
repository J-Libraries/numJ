package com.library.numj;

import com.library.numj.enums.DType;
import com.library.numj.exceptions.ShapeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class NumJTest {

    private NumJ<?> numJ;

    @BeforeEach
    void setUp() {
        numJ = new NumJ<>();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(numJ.arithmaticOperations);
        assertNotNull(numJ.arrayModification);
        assertNotNull(numJ.arrayCreation);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 1",
            "3, 1"
    })
    <T> void testArrayCreationWithSingleValue(T value) throws Exception {
        NumJ<T> numJ = new NumJ<>();
        NDArray<Integer> result = numJ.array(value);
        assertNotNull(result);
        assertEquals(1, result.ndim());
        assertEquals(1L, result.size());
        //assertArrayEquals(new Integer[]{value}, (Integer[]) result.getArray());
        assertEquals(value, result.getArray());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 2",
            "2, 3, 3",
            "3, 4, 4"
    })
    <T> void testArrayCreationWithShape(T value, int expectedShape, DType dType) throws Exception {
        NumJ<T> numJ = new NumJ<>();
        int[] shape = {expectedShape, expectedShape};

        NDArray<T> result = numJ.array(value, shape, 2, dType);

        assertNotNull(result);
        assertEquals(2, result.ndim());
        assertArrayEquals(shape, result.shape().stream().mapToInt(Integer::intValue).toArray());
    }

    static Stream<Arguments> shapeProvider() {
        return Stream.of(
                Arguments.of(1, 2, DType.INT32),
                Arguments.of(3.14, 3, DType.FLOAT64),
                Arguments.of("a", 4, DType.STR)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForZerosAndOnes")
    <T> void testZerosCreation(T zeroValue, int[] shape, DType dType) throws Exception {
        NumJ<T> numJ = new NumJ<>();
        NDArray<T> result = numJ.zeros(shape, dType);
        assertNotNull(result);
        assertEquals(2, result.ndim());
        assertArrayEquals((Object[]) java.util.Collections.nCopies(shape[0] * shape[1], zeroValue).toArray(), (Object[]) result.flatten().getArray());
    }


    @ParameterizedTest
    @MethodSource("provideDataForZerosAndOnes")
    <T> void testOnesCreation(T oneValue, int[] shape, DType dType) throws Exception {
        NumJ<T> numJ = new NumJ<>();
        NDArray<T> result = numJ.ones(shape, dType);
        assertNotNull(result);
        assertEquals(2, result.ndim());
        assertArrayEquals((Object[]) java.util.Collections.nCopies(shape[0] * shape[1], oneValue).toArray(), (Object[]) result.flatten().getArray());
    }


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


    private List<Integer> generateExpectedArangeData(int start, int end) {
        List<Integer> data = new ArrayList<>();
        for (int i = start; i < end; i++) {
            data.add(i);
        }
        return data;
    }


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

    @ParameterizedTest
    @MethodSource("provideDataForArithmeticOperations")
    <T> void testAddition(T[][] data1, T[][] data2, T[][] expected, DType dType) throws Exception {
        NumJ<T> numJ = new NumJ<>();
        NDArray<T> arr1 = numJ.array(data1);
        NDArray<T> arr2 = numJ.array(data2);
        NDArray<T> result = numJ.add(arr1, arr2);
        assertArrayEquals((Object[]) expected, (Object[]) result.getArray());
    }

    @ParameterizedTest
    @MethodSource("provideDataForArithmeticOperations")
    <T> void testSubtraction(T[][] data1, T[][] data2, T[][] expected, DType dType) throws Exception {
        NumJ<T> numJ = new NumJ<>();
        NDArray<T> arr1 = numJ.array(data1);
        NDArray<T> arr2 = numJ.array(data2);
        NDArray<T> result = numJ.subtract(arr1, arr2);
        assertArrayEquals((Object[]) expected, (Object[]) result.getArray());
    }

    @ParameterizedTest
    @MethodSource("provideDataForArithmeticOperations")
    <T> void testMultiplication(T[][] data1, T[][] data2, T[][] expected, DType dType) throws Exception {
        NumJ<T> numJ = new NumJ<>();
        NDArray<T> arr1 = numJ.array(data1);
        NDArray<T> arr2 = numJ.array(data2);
        NDArray<T> result = numJ.multiply(arr1, arr2);
        assertArrayEquals((Object[]) expected, (Object[]) result.getArray());
    }

    @ParameterizedTest
    @MethodSource("provideDataForArithmeticOperations")
    <T> void testDivision(T[][] data1, T[][] data2, T[][] expected, DType dType) throws Exception {
        NumJ<T> numJ = new NumJ<>();
        NDArray<T> arr1 = numJ.array(data1);
        NDArray<T> arr2 = numJ.array(data2);
        NDArray<T> result = numJ.divide(arr1, arr2);
        assertArrayEquals((Object[]) expected, (Object[]) result.getArray());
    }


    @Test
    void testTranspose() throws Exception {
        Integer[][] data = {{1, 2}, {3, 4}};
        NDArray<Integer[][]> arr = numJ.array(data);
        NDArray<Integer> transposed = numJ.transpose(arr);
        Integer[][] expected = {{1, 3}, {2, 4}};
        assertArrayEquals(expected, (Integer[][]) transposed.getArray());
    }

    @Test
    void testArangeWithNegativeRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            numJ.arange(-1);
        }, "Expected IllegalArgumentException when range is negative");
    }

    @Test
    void testZerosWithInvalidShape() {
        int[] invalidShape = {0, 2};  // Invalid dimension
        assertThrows(ShapeException.class, () -> {
            numJ.zeros(invalidShape);
        }, "Expected ShapeException for invalid shape");
    }

    @Test
    void testEyeWithInvalidDiagonal() {
        assertThrows(IllegalArgumentException.class, () -> {
            numJ.eye(3, 3, 4);  // Invalid diagonal index
        }, "Expected IllegalArgumentException for invalid diagonal index");
    }

    @Test
    void testAdditionWithMismatchedShapes() throws ShapeException {
        Integer[][] data1 = {{1, 2}, {3, 4}};
        Integer[] data2 = {5, 6, 7};  // Mismatched shape

        NDArray<Integer[][]> arr1 = numJ.array(data1);
        NDArray<Integer[]> arr2 = numJ.array(data2);

        assertThrows(ShapeException.class, () -> {
            numJ.add(arr1, arr2);
        }, "Expected ShapeException for mismatched shapes");
    }

    @Test
    void testDivisionByZero() throws ShapeException {
        Integer[][] data1 = {{10, 20}, {30, 40}};
        Integer[][] data2 = {{0, 2}, {0, 10}};  // Division by zero

        NDArray<Integer[][]> arr1 = numJ.array(data1);
        NDArray<Integer[][]> arr2 = numJ.array(data2);

        assertThrows(ArithmeticException.class, () -> {
            numJ.divide(arr1, arr2);
        }, "Expected ArithmeticException for division by zero");
    }

    static Stream<Arguments> provideDataForZerosAndOnes() {
        return Stream.of(
                Arguments.of(0, new int[]{2, 2}, DType.INT32),
                Arguments.of(1.0, new int[]{2, 2}, DType.FLOAT64),
                Arguments.of("0", new int[]{2, 2}, DType.STR)
        );
    }

    static Stream<Arguments> provideDataForArithmeticOperations() {
        return Stream.of(
                Arguments.of(new Integer[][]{{1, 2}, {3, 4}}, new Integer[][]{{5, 6}, {7, 8}}, new Integer[][]{{6, 8}, {10, 12}}, DType.INT32),
                Arguments.of(new Double[][]{{1.0, 2.0}, {3.0, 4.0}}, new Double[][]{{5.0, 6.0}, {7.0, 8.0}}, new Double[][]{{6.0, 8.0}, {10.0, 12.0}}, DType.FLOAT64)
        );
    }

    static Stream<Arguments> provideDataForTranspose() {
        return Stream.of(
                Arguments.of(new Integer[][]{{1, 2}, {3, 4}}, new Integer[][]{{1, 3}, {2, 4}}),
                Arguments.of(new Double[][]{{1.0, 2.0}, {3.0, 4.0}}, new Double[][]{{1.0, 3.0}, {2.0, 4.0}})
        );
    }


}
