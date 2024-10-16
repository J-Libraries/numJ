/**
 * The App class serves as the entry point for the NumJ library application.
 * It demonstrates basic arithmetic operations using the NumJ library.
 */
package com.library.numj;

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
//        int size = (int)Math.pow(10, 9);
//        int[] a = new int[size];
//        long t1 = System.currentTimeMillis();
//        IntStream.range(0, size).forEach(index -> a[index] = 2);
//        long t2 = System.currentTimeMillis();
//        for(int i=0;i<size;i++)
//        {
//            a[i] = 1;
//        }
//        long t3 = System.currentTimeMillis();
//        Arrays.fill(a, 3);
//        long t4 = System.currentTimeMillis();
//        System.out.println("Time : T1 -> "+(t2-t1));
//        System.out.println("Time : T2 -> "+(t3-t2));
//        System.out.println("Time : T3 -> "+(t4-t3));
        testArrayCreationOperations();
    }

    /**
     * Demonstrates arithmetic operations using the NumJ library.
     * It creates NDArray objects from data arrays and performs subtraction between them.
     * The result is printed to the console.
     */
    @SuppressWarnings("unchecked")
    private static void testArithmaticOperations() {
        NumJ numj = new NumJ<>();
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
    }
}
