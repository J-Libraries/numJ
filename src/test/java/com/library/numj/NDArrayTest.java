
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

    @BeforeEach
    public void setUp() {
        Integer[][][] data = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
        try {
            array = new NDArray<>(data);
        }catch (ShapeException ignored) {

        }
    }

    @Test
    public void testValidConstruction()
    {
        assertNotNull(array);
        assertEquals(3 , array.ndim());
        assertEquals(Arrays.asList(2,2,2) , array.shape());
        assertEquals(8L , array.size());

        Integer[][][] expectedData = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
        assertArrayEquals(expectedData , (Integer[][][])array.getArray());
    }

    @Test
    public void testShapeExceptionInConstructor()
    {
        Integer[][][] invalidData = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}, {9, 10}}};
        assertThrows(ShapeException.class , () -> new NDArray<>(invalidData) );
    }

    @Test
    public void testFlatten() throws ShapeException
    {
        NDArray<Integer[]> flatArray = array.flatten();
        assertEquals(1, flatArray.ndim());
        assertEquals(8L, flatArray.size());

        // Convert Object[] to Integer[] safely
        Object[] flattenedArray = (Object[]) flatArray.getArray();
        Integer[] result = Arrays.copyOf(flattenedArray, flattenedArray.length, Integer[].class);

        // Expected flattened data
        Integer[] expectedFlattenedData = {1, 2, 3, 4, 5, 6, 7, 8};
        assertArrayEquals(expectedFlattenedData, result);
    }
}
