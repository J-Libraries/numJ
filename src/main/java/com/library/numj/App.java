/**
 * The App class serves as the entry point for the NumJ library application.
 * It demonstrates basic arithmetic operations using the NumJ library.
 */
package com.library.numj;

import com.library.numj.exceptions.ShapeException;

import java.util.Arrays;


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
        NumJ numJ = new NumJ();
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
        NDArray<Integer[]> arr = numJ.array(arrayData);
        arr.printArray();
        NDArray<Integer[]> arr1 = numJ.arange(100);
//        arr1.printArray();

        NDArray<Integer[][]> arr2 = numJ.zeros(new int[]{3,3});
        NDArray<Integer[][]> arr3 = numJ.ones(new int[]{3,3});
        NDArray<Integer[][]> arr4 = numJ.subtract(arr2, arr3);
        arr4.printArray();


    }
}
