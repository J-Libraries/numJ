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
        Integer[] arr6 = {1,2,3,4,5};
        Integer[] arr5 = {5,4,3,2,1};

        NDArray<Integer[]> arrData1 = numJ.array(arr6);
        NDArray<Integer[]> arrData2 = numJ.array(arr5);

        numJ.bitwiseAnd(arrData1, arrData2).printArray();
        numJ.bitwiseOr(arrData1, arrData2).printArray();
        numJ.bitwiseXor(arrData1, arrData2).printArray();
        numJ.invert(arrData1).printArray();



    }
}
