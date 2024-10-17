/**
 * The App class serves as the entry point for the NumJ library application.
 * It demonstrates basic arithmetic operations using the NumJ library.
 */
package com.library.numj;

import com.library.numj.enums.DType;
import com.library.numj.exceptions.ShapeException;

import java.util.Arrays;
import java.util.stream.IntStream;

public class App
{
    /**
     * The main method that executes the testArithmaticOperations method.
     * @param args Command line arguments (not used).
     */
    public static void main( String[] args ) throws ShapeException {
        System.out.println( "Hello World!" );
        testArrayCreationOperations();
    }
    private static void testArrayCreationOperations() throws ShapeException {
        NumJ<Integer[][][]> numJ = new NumJ();
        Integer[][][] arrayData = {
                {
                        {2,5},
                        {4,6},
                        {3,5}
                },
                {
                        {1,3},
                        {6,9},
                        {6,8}
                },
                {
                        {1,3},
                        {9,10},
                        {11,12}
                }
        };
        NDArray<Integer[][][]> arr = numJ.array(arrayData);
        NDArray<Integer[][]> arr1 = arr.reshape(2, 9);
        arr.printArray();
        arr1.printArray();
        NDArray<Integer[][][]> ndArray = numJ.zeros(new int[]{3,6});
        ndArray.printArray();
//        ndArray.flatten();
//        long t1 = System.currentTimeMillis();
//       numJ.eye(10000, 10000).flatten();
////       numJ.eye(3, 3, -1);
//       long t2 = System.currentTimeMillis();
//        System.out.println("Time -> "+(t2-t1));
    }
}
