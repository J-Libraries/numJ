package com.library.numj;

import com.library.numj.enums.DType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

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
            "1, {1}",
            "2, {2}",
            "3, {3}"
    })
    void testArrayCreationWithSingleValue(int value, String expectedShape) throws Exception {
        NDArray<Integer> result = numJ.array(value);
        assertEquals(expectedShape, Arrays.toString(result.shape().toArray()));
    }

    @ParameterizedTest
    @CsvSource({
            "1, {1}",
            "2, {2}",
            "3, {3}"
    })
    void testArrayCreationWithShape(int value, String expectedShape) throws Exception {
        int[] shape = {1};
        NDArray<Integer> result = numJ.array(value, shape, 1, DType.INT32);
        assertEquals(expectedShape, Arrays.toString(result.shape().toArray()));
    }

   /* @Test
    void testZerosCreation() throws Exception {
        assertArrayCreation(numJ.zeros(new int[]{2, 3}), new Integer[]{0, 0, 0, 0, 0, 0});
    }

    @Test
    void testOnesCreation() throws Exception {
        assertArrayCreation(numJ.ones(new int[]{2, 3}), new Integer[]{1, 1, 1, 1, 1, 1});
    }

    @ParameterizedTest
    @CsvSource({
            "0, 5, {5}",
            "1, 6, {5}",
            "2, 8, {6}"
    })
    void testArange(int start, int end, String expectedShape) throws Exception {
        NDArray<Integer> result = numJ.arange(start, end);
        assertEquals(expectedShape, Arrays.toString(result.shape()));
        List<Integer> expectedData = generateExpectedArangeData(start, end);
        assertEquals(expectedData, Arrays.asList(result.data()));
    }

    private List<Integer> generateExpectedArangeData(int start, int end) {
        return Arrays.asList(start, start + 1, start + 2, start + 3, start + 4);
    }

    @Test
    void testArangeWithShapeAndSkip() throws Exception {
        NDArray<Integer> result = numJ.arange(0, 10, 2, new int[]{3});
        assertArrayCreation(result, new Integer[]{0, 2, 4});
    }

    private void assertArrayCreation(NDArray<Integer> result, Integer[] expected) {
        assertEquals(expected.length, result.shape()[0]);
        assertArrayEquals(expected, result.data());
    }

    @Test
    void testAddition() throws Exception {
        NDArray<Integer> arr1 = numJ.array(new int[]{1, 2, 3});
        NDArray<Integer> arr2 = numJ.array(new int[]{4, 5, 6});
        NDArray<Integer> result = numJ.add(arr1, arr2);
        assertArrayEquals(new Integer[]{5, 7, 9}, result.data());
    }

    @Test
    void testSubtraction() throws Exception {
        NDArray<Integer> arr1 = numJ.array(new int[]{5, 5, 5});
        NDArray<Integer> arr2 = numJ.array(new int[]{3, 3, 3});
        NDArray<Integer> result = numJ.subtract(arr1, arr2);
        assertArrayEquals(new Integer[]{2, 2, 2}, result.data());
    }

    @Test
    void testMultiplication() throws Exception {
        NDArray<Integer> arr1 = numJ.array(new int[]{1, 2, 3});
        NDArray<Integer> arr2 = numJ.array(new int[]{4, 5, 6});
        NDArray<Integer> result = numJ.multiply(arr1, arr2);
        assertArrayEquals(new Integer[]{4, 10, 18}, result.data());
    }

    @Test
    void testDivision() throws Exception {
        NDArray<Integer> arr1 = numJ.array(new int[]{10, 20, 30});
        NDArray<Integer> arr2 = numJ.array(new int[]{2, 4, 6});
        NDArray<Integer> result = numJ.divide(arr1, arr2);
        assertArrayEquals(new Integer[]{5, 5, 5}, result.data());
    }

    @Test
    void testTranspose() throws Exception {
        NDArray<Integer> array = numJ.array(new int[]{1, 2, 3, 4});
        NDArray<Integer> result = numJ.transpose(array);
        assertEquals(2, result.shape()[0]);
        assertEquals(2, result.shape()[1]);
        assertArrayEquals(new Integer[]{1, 2, 3, 4}, result.data());
    }*/
}
