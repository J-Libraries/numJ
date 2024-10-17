package com.library.numj;

import com.library.numj.enums.DType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class NumJTest {

    private NumJ<Integer> numJ;

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
    void testArrayCreationWithSingleValue(int value, int expectedShape) throws Exception {
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
    void testArrayCreationWithShape(int value, int expectedShape) throws Exception {
        int[] shape = {expectedShape, expectedShape};
        NDArray<Integer> result = numJ.array(value, shape, 2, DType.INT32);
        assertNotNull(result);
        assertEquals(2, result.ndim());
        assertArrayEquals(shape, result.shape().stream().mapToInt(Integer::intValue).toArray());
    }

    @Test
    void testZerosCreation() throws Exception {
        int[] shape = {2, 2};
        NDArray<Integer> result = numJ.zeros(shape);
        assertNotNull(result);
        assertEquals(2, result.ndim());
        assertArrayEquals(new Integer[]{0, 0, 0, 0}, (Integer[]) result.flatten().getArray());
    }


    @Test
    void testOnesCreation() throws Exception {
        int[] shape = {2, 2};
        NDArray<Integer> result = numJ.ones(shape);
        assertNotNull(result);
        assertEquals(2, result.ndim());
        assertArrayEquals(new Integer[]{1, 1, 1, 1}, (Integer[]) result.flatten().getArray());
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

    @Test
    void testAddition() throws Exception {
        Integer[][] data1 = {{1, 2}, {3, 4}};
        Integer[][] data2 = {{5, 6}, {7, 8}};
        NDArray<Integer[][]> arr1 = numJ.array(data1);
        NDArray<Integer[][]> arr2 = numJ.array(data2);
        NDArray<Integer> result = numJ.add(arr1, arr2);
        assertArrayEquals(new Integer[][]{{6, 8}, {10, 12}}, (Integer[][]) result.getArray());
    }

    @Test
    void testSubtraction() throws Exception {
        Integer[][] data1 = {{5, 6}, {7, 8}};
        Integer[][] data2 = {{1, 2}, {3, 4}};
        NDArray<Integer[][]> arr1 = numJ.array(data1);
        NDArray<Integer[][]> arr2 = numJ.array(data2);
        NDArray<Integer> result = numJ.subtract(arr1, arr2);
        assertArrayEquals(new Integer[][]{{4, 4}, {4, 4}}, (Integer[][]) result.getArray());
    }

    @Test
    void testMultiplication() throws Exception {
        Integer[][] data1 = {{1, 2}, {3, 4}};
        Integer[][] data2 = {{5, 6}, {7, 8}};
        NDArray<Integer[][]> arr1 = numJ.array(data1);
        NDArray<Integer[][]> arr2 = numJ.array(data2);
        NDArray<Integer> result = numJ.multiply(arr1, arr2);
        assertArrayEquals(new Integer[][]{{5, 12}, {21, 32}}, (Integer[][]) result.getArray());
    }

    @Test
    void testDivision() throws Exception {
        Integer[][] data1 = {{10, 20}, {30, 40}};
        Integer[][] data2 = {{2, 2}, {5, 10}};
        NDArray<Integer[][]> arr1 = numJ.array(data1);
        NDArray<Integer[][]> arr2 = numJ.array(data2);
        NDArray<Integer> result = numJ.divide(arr1, arr2);
        assertArrayEquals(new Integer[][]{{5, 10}, {6, 4}}, (Integer[][]) result.getArray());
    }

    @Test
    void testTranspose() throws Exception {
        Integer[][] data = {{1, 2}, {3, 4}};
        NDArray<Integer[][]> arr = numJ.array(data);
        NDArray<Integer> transposed = numJ.transpose(arr);
        Integer[][] expected = {{1, 3}, {2, 4}};
        assertArrayEquals(expected, (Integer[][]) transposed.getArray());
    }
}
