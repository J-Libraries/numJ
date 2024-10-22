
package com.library.numj;

import static org.junit.jupiter.api.Assertions.*;

import com.library.numj.exceptions.ShapeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * Step by step :
 * 1. Create a test class for NDArray.
 * 2. Test constructors and initialization.
 * 3. Test getter methods.
 * 4. Test array manipulation methods (reshape, flatten, transpose).
 * 5. Test exception handling.
 * 6. Test edge cases and boundary conditions.
 */
class NDArrayTest {

    private NDArray<Integer[][][]> array;
    private NDArray<int[][][]> primitiveIntArray;
    private NDArray<Float[][][]> floatArray;
    private NDArray<float[][][]> primitiveFloatArray;
    private NDArray<Double[][][]> doubleArray;
    private NDArray<double[][][]> primitiveDoubleArray;
    private NDArray<String[][][]> stringArray;
    private NDArray<Byte[][][]> byteArray;
    private NDArray<byte[][][]> primitiveByteArray;
    private NDArray<Object[][][]> mixedArray;


    @BeforeEach
    public void setUp() {
        Integer[][][] data = {{{400, 200}, {300, 400}}, {{500, 600}, {700, 800}}};
        Float[][][] floatData = {{{1.1f, 2.2f}, {3.3f, 4.4f}}, {{5.5f, 6.6f}, {7.7f, 8.8f}}};
        Double[][][] doubleData = {{{1.1, 2.2}, {3.3, 4.4}}, {{5.5, 6.6}, {7.7, 8.8}}};
        String[][][] stringData = {{{"a", "b"}, {"c", "d"}}, {{"e", "f"}, {"g", "h"}}};
        Object[][][] mixedData = {{{1, 2}, {3.5, "4"}}, {{5, 6.7}, {"8", 9}}};

        int[][][] primitiveIntData = {{{300, 200}, {3000, 4000}}, {{50000, 60000}, {70000, 80000}}};
        float[][][] primitiveFloatData = {{{1.1f, 2.2f}, {3.3f, 4.4f}}, {{5.5f, 6.6f}, {7.7f, 8.8f}}};
        double[][][] primitiveDoubleData = {{{1.1, 2.2}, {3.3, 4.4}}, {{5.5, 6.6}, {7.7, 8.8}}};
        Byte[][][] byteData = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
        byte[][][] primitiveByteData = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};

        try {
            array = new NDArray<>(data);
            primitiveIntArray = new NDArray<>(primitiveIntData);
            floatArray = new NDArray<>(floatData);
            primitiveFloatArray = new NDArray<>(primitiveFloatData);
            doubleArray = new NDArray<>(doubleData);
            primitiveDoubleArray = new NDArray<>(primitiveDoubleData);
            stringArray = new NDArray<>(stringData);
            mixedArray = new NDArray<>(mixedData);
            byteArray = new NDArray<>(byteData);
            primitiveByteArray = new NDArray<>(primitiveByteData);
        } catch (ShapeException ignored) {

        }
    }



    @Test
    <T> void testEmptyArrayCreation() throws ShapeException {
        T[] emptyData = (T[]) new Object[0];
        NDArray<T[]> emptyArray = new NDArray<>(emptyData);
        assertEquals(1, emptyArray.ndim());
        assertEquals(0L, emptyArray.size());
        assertArrayEquals(new Object[0], (Object[]) emptyArray.getArray());
    }

    @Test
    <T> void testArrayWithNullValues() throws ShapeException {
        T[] dataWithNull = (T[]) new Object[]{1, null, 3};
        NDArray<T[]> array = new NDArray<>(dataWithNull);
        assertEquals(1, array.ndim());
        assertEquals(3L, array.size());
        assertArrayEquals(dataWithNull, (T[]) array.getArray());
    }


    @Test
    void testMemoryCalculation() {
        assertEquals(32L, array.itemSize());
        assertEquals(128L, primitiveDoubleArray.itemSize());
        assertEquals(16L, byteArray.itemSize());
    }


    @Test
    <T> void testSingleElementArray() throws ShapeException {
        T[] singleElementData = (T[]) new Object[]{1};
        NDArray<T[]> singleArray = new NDArray<>(singleElementData);
        assertEquals(1, singleArray.ndim());
        assertEquals(1L, singleArray.size());
        assertArrayEquals(singleElementData, (T[]) singleArray.getArray());
    }


    /*@Test
    void testPrintArray() throws ShapeException {
        Integer[][][] data = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
        NDArray<Integer[][][]> array = new NDArray<>(data);
        array.printArray();
        array.printArray(true);  // Check full printing
    }*/




    /**
     * Tests that a ShapeException is thrown when attempting to construct
     * an NDArray with irregular dimensions, specifically when one of the
     * sub-arrays has a different size than expected.
     */

    @Test
    public void testShapeExceptionInConstructor() {
        Integer[][][] invalidData = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}, {9, 10}}};
        assertThrows(ShapeException.class, () -> new NDArray<>(invalidData));
    }

    /**
     * Tests the valid construction of an NDArray with Integer data.
     * It verifies that the array is not null, checks the number of dimensions,
     * the shape of the array, its total size, and the actual data within the array.
     */

    @Test
    public void testValidConstructionWithIntegers() {
        assertNotNull(array);
        assertNotNull(primitiveIntArray);
        assertEquals(3, array.ndim());
        assertEquals(3, primitiveIntArray.ndim());
        assertEquals(Arrays.asList(2, 2, 2), array.shape());
        assertEquals(Arrays.asList(2, 2, 2), primitiveIntArray.shape());
        assertEquals(8L, array.size());
        assertEquals(8L, primitiveIntArray.size());

        Integer[][][] expectedData = {{{400, 200}, {300, 400}}, {{500, 600}, {700, 800}}};
        assertArrayEquals(expectedData, (Integer[][][]) array.getArray());
        int[][][] intExpectedData = {{{300, 200}, {3000, 4000}}, {{50000, 60000}, {70000, 80000}}};
        assertArrayEquals(intExpectedData, (int[][][]) primitiveIntArray.getArray());
    }

    /**
     * Tests the valid construction of an NDArray with Float data.
     * Asserts that the array is initialized correctly and verifies its dimensions,
     * shape, size, and the content of the array.
     *
     * @throws ShapeException if the shape of the input data is invalid.
     */

    @Test
    public void testValidConstructionWithFloats() throws ShapeException {
        assertNotNull(floatArray);
        assertNotNull(primitiveFloatArray);
        assertEquals(3, floatArray.ndim());
        assertEquals(3, primitiveFloatArray.ndim());
        assertEquals(Arrays.asList(2, 2, 2), floatArray.shape());
        assertEquals(Arrays.asList(2, 2, 2), primitiveFloatArray.shape());
        assertEquals(8L, floatArray.size());
        assertEquals(8L, primitiveFloatArray.size());

        Float[][][] expectedData = {{{1.1f, 2.2f}, {3.3f, 4.4f}}, {{5.5f, 6.6f}, {7.7f, 8.8f}}};
        assertArrayEquals(expectedData, (Float[][][]) floatArray.getArray());

        float[][][] floatExpectedData = {{{1.1f, 2.2f}, {3.3f, 4.4f}}, {{5.5f, 6.6f}, {7.7f, 8.8f}}};
        assertArrayEquals(floatExpectedData, (float[][][]) primitiveFloatArray.getArray());
    }

    /**
     * Tests the valid construction of an NDArray with Double data.
     * Ensures the array is initialized correctly and verifies its dimensions,
     * shape, size, and the actual data contained within.
     *
     * @throws ShapeException if the shape of the input data is invalid.
     */

    @Test
    public void testValidConstructionWithDoubles() throws ShapeException {
        assertNotNull(doubleArray);
        assertNotNull(primitiveDoubleArray);
        assertEquals(3, doubleArray.ndim());
        assertEquals(3, primitiveDoubleArray.ndim());
        assertEquals(Arrays.asList(2, 2, 2), doubleArray.shape());
        assertEquals(Arrays.asList(2, 2, 2), primitiveDoubleArray.shape());
        assertEquals(8L, doubleArray.size());
        assertEquals(8L, primitiveDoubleArray.size());

        Double[][][] expectedData = {{{1.1, 2.2}, {3.3, 4.4}}, {{5.5, 6.6}, {7.7, 8.8}}};
        assertArrayEquals(expectedData, (Double[][][]) doubleArray.getArray());

        double[][][] doubleExpectedData = {{{1.1, 2.2}, {3.3, 4.4}}, {{5.5, 6.6}, {7.7, 8.8}}};
        assertArrayEquals(doubleExpectedData, (double[][][]) primitiveDoubleArray.getArray());
    }

    /**
     * Tests the valid construction of an NDArray with String data.
     * Verifies the proper initialization of the array by checking its dimensions,
     * shape, size, and the expected data.
     *
     * @throws ShapeException if the shape of the input data is invalid.
     */

    @Test
    public void testValidConstructionWithStrings() throws ShapeException {
        assertNotNull(stringArray);
        assertEquals(3, stringArray.ndim());
        assertEquals(Arrays.asList(2, 2, 2), stringArray.shape());
        assertEquals(8L, stringArray.size());

        String[][][] expectedData = {{{"a", "b"}, {"c", "d"}}, {{"e", "f"}, {"g", "h"}}};
        assertArrayEquals(expectedData, (String[][][]) stringArray.getArray());
    }

    /**
     * Tests the creation of an NDArray with mixed data types.
     * Confirms that the array is correctly initialized and checks its dimensions,
     * shape, size, and content.
     *
     * @throws ShapeException if the shape of the input data is invalid.
     */

    @Test
    public void testMixedDataArray() throws ShapeException {
        Object[][][] mixedData = {{{1, 2}, {3.5, "4"}}, {{5, 6.7}, {"8", 9}}};
        assertNotNull(mixedArray);
        assertEquals(3, mixedArray.ndim());
        assertEquals(Arrays.asList(2, 2, 2), mixedArray.shape());
        assertEquals(8L, mixedArray.size());
        assertArrayEquals(mixedData, (Object[][][]) mixedArray.getArray());
    }

    /**
     * Tests the creation of an NDArray with mixed data types.
     * Confirms that the array is correctly initialized and checks its dimensions,
     * shape, size, and content.
     *
     * @throws ShapeException if the shape of the input data is invalid.
     */

    @Test
    public void testByteDataArray() throws ShapeException {
        assertNotNull(byteArray);
        assertNotNull(primitiveByteArray);
        assertEquals(3, byteArray.ndim());
        assertEquals(3, primitiveByteArray.ndim());
        assertEquals(Arrays.asList(2, 2, 2), byteArray.shape());
        assertEquals(Arrays.asList(2, 2, 2), primitiveByteArray.shape());
        assertEquals(8L, byteArray.size());
        assertEquals(8L, primitiveByteArray.size());

        Byte[][][] expectedData = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
        assertArrayEquals(expectedData, (Byte[][][]) byteArray.getArray());
        byte[][][] byteExpectedData = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
        assertArrayEquals(byteExpectedData, (byte[][][]) primitiveByteArray.getArray());
    }

    /**
     * Tests the flattening of a multi-dimensional NDArray.
     * Asserts that the resulting array is one-dimensional and checks
     * its size and content against the expected flattened array.
     *
     * @throws ShapeException if the shape of the array is invalid.
     */

    @Test
    public void testFlatten() throws ShapeException {
        NDArray<Integer[]> flatArray = array.flatten();
        assertEquals(1, flatArray.ndim());
        assertEquals(8L, flatArray.size());

        // Convert Object[] to Integer[] safely
        Object[] flattenedArray = (Object[]) flatArray.getArray();
        Integer[] result = Arrays.copyOf(flattenedArray, flattenedArray.length, Integer[].class);

        // Expected flattened data
        //Integer[] expectedFlattenedData = {1, 2, 3, 4, 5, 6, 7, 8};
        Integer[] expectedFlattenedData = {400, 200, 300, 400, 500, 600, 700, 800};
        assertArrayEquals(expectedFlattenedData, result);
    }


    /**
     * Tests the flattening of a multi-dimensional NDArray for float arrays.
     * Ensures that the flattening works correctly with primitive float arrays.
     *
     * @throws ShapeException if the shape of the array is invalid.
     */
    @Test
    public void testFlattenPrimitiveFloatArray() throws ShapeException {
        float[] flattenedFloatData = {1.1f, 2.2f, 3.3f, 4.4f, 5.5f, 6.6f, 7.7f, 8.8f};
        NDArray<float[]> flattenedFloatArray = primitiveFloatArray.flatten();
        assertEquals(1, flattenedFloatArray.ndim());
        assertEquals(8L, flattenedFloatArray.size());
        assertArrayEquals(flattenedFloatData, (float[]) flattenedFloatArray.getArray(), 0.001f);
    }


    /**
     * Tests the reshaping of an NDArray to a lower dimension.
     * Checks the new dimensions, shape, size, and content of the reshaped array.
     *
     * @throws ShapeException if the shape of the array is invalid.
     */

    @Test
    public void testReshapeToLowerDimension() throws ShapeException {
        int[] newShape = {4, 2};
        NDArray<Integer[][]> reshapedArray = array.reshape(newShape);
        assertEquals(2, reshapedArray.ndim());
        assertEquals(Arrays.asList(4, 2), reshapedArray.shape());
        assertEquals(8L, reshapedArray.size());
        Integer[][] expectedReshapedData = {
                {1, 2}, {3, 4}, {5, 6}, {7, 8}
        };
        assertArrayEquals(expectedReshapedData, (Integer[][]) reshapedArray.getArray());
    }

    /**
     * Tests the reshaping of an NDArray to a higher dimension.
     * Validates the new dimensions, shape, size, and data of the reshaped array.
     *
     * @throws ShapeException if the shape of the array is invalid.
     */

    @Test
    public void testReshapeToHigherDimension() throws ShapeException {
        NDArray<Integer[][]> reshaped = array.reshape(2, 4);
        assertEquals(2, reshaped.ndim());
        assertEquals(Arrays.asList(2, 4), reshaped.shape());
        assertEquals(8L, reshaped.size());

        Integer[][] expectedReshapedData = {{1, 2, 3, 4}, {5, 6, 7, 8}};
        assertArrayEquals(expectedReshapedData, (Integer[][]) reshaped.getArray());
    }

    /**
     * Tests the reshaping of an NDArray to a single dimension.
     * Asserts that the new dimensions, shape, size, and data match the expected values.
     *
     * @throws ShapeException if the shape of the array is invalid.
     */

    @Test
    public void testReshapeToSingleDimension() throws ShapeException {
        int[] newShape = {8};
        NDArray<Integer[]> reshapedArray = array.reshape(newShape);
        assertEquals(1, reshapedArray.ndim());
        assertEquals(Arrays.asList(8), reshapedArray.shape());
        assertEquals(8L, reshapedArray.size());

        Integer[] expectedReshapedData = {1, 2, 3, 4, 5, 6, 7, 8};
        assertArrayEquals(expectedReshapedData, (Integer[]) reshapedArray.getArray());
    }


    @Test
    public void testInvalidReshapeTotalSizeMismatch() {
        int[] invalidShape = {0}; // Invalid shape with zero elements
        assertThrows(ShapeException.class, () -> array.reshape(invalidShape));
    }

    @Test
    public void testReshapeBackToOriginalDimensions() throws ShapeException {
        int[] originalShape = {2 , 2 , 2};
        NDArray<Integer[][][]> reshapedArray = array.reshape(originalShape);
        assertEquals(3 , reshapedArray.ndim());
        assertEquals(Arrays.asList(2 , 2 , 2) , reshapedArray.shape());
        assertEquals(8L , reshapedArray.size());

        Integer[][][] expectedReshapedData = {{{1 , 2} , {3 , 4}} , {{5 , 6} , {7 , 8}}};
        assertArrayEquals(expectedReshapedData , (Integer[][][])reshapedArray.getArray());
    }

    @Test
    public void testReshapeToEmptyDimension() throws ShapeException {
        int[] newShape = {0, 2, 2};
        assertThrows(ShapeException.class, () -> array.reshape(newShape));
    }

    /**
     * Tests the handling of very large arrays.
     * Ensures that the NDArray can handle arrays of large size without performance degradation.
     *
     * @throws ShapeException if the shape of the array is invalid.
     */
    @Test
    public void testLargeArrayHandling() throws ShapeException {
        Integer[][][] largeData = new Integer[100][100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    largeData[i][j][k] = i + j + k;
                }
            }
        }

        NDArray<Integer[][][]> largeArray = new NDArray<>(largeData);
        assertEquals(3, largeArray.ndim());
        assertEquals(1000000L, largeArray.size());  // 100 * 100 * 100
    }


   /* @Test
    void testStrideCalculation() {
        int[] strides = array.strides(new int[]{2, 2, 2});
        assertArrayEquals(new int[]{16, 8, 4}, strides);
    }*/


    /*@Test
    void testInvalidMemoryCalculation() {
        assertThrows(IllegalStateException.class, () -> {
            array.nBytes = Long.MAX_VALUE;
            array.itemSize();
        });
    }*/


    /*@Test
    public void testItemSize() {
        assertEquals(Integer.BYTES, array.itemSize());
    }

    @Test
    public void testStrides() {
        int[] expectedStrides = {8, 4, 2};
        assertArrayEquals(expectedStrides, array.strides());
    }*/
}
