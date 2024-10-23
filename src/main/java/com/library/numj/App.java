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
//        test();/
        testArrayCreationOperations();
    }
    private  static <T> void test()
    {
        T n1 = (T) Integer.valueOf(10);
        T n2 = (T) Integer.valueOf(10);
        Number out = ((Number)n1).intValue() + ((Number)n2).intValue();
        System.out.println(out.getClass().getName());
    }
    private static void testArrayCreationOperations() throws ShapeException {
        NumJ numJ = new NumJ();
        int[][][] arrayData = {
                {
                        {1, 2},
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
//        NDArray arr = numJ.array(new int[][]{{1,2, 3},{4, 5, 6}});
        NDArray arr1 = numJ.array(new Integer[][]{{1,2},{3, 4}});
        NDArray arr2 = numJ.array(new Integer[][]{{5,6}, {7,8}});

        NDArray out = numJ.multiply(arr1, arr2);
        out.printArray();
//        NDArray transposed = numJ.transpose(arr);
//        transposed.printArray();

    }
}
