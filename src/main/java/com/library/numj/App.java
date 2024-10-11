/**
 * The App class serves as the entry point for the NumJ library application.
 * It demonstrates basic arithmetic operations using the NumJ library.
 */
package com.library.numj;

import com.library.numj.exceptions.ShapeException;

public class App
{
    /**
     * The main method that executes the testArithmaticOperations method.
     * @param args Command line arguments (not used).
     */
    public static void main( String[] args ) throws ShapeException {
        System.out.println( "Hello World!" );
//        TestClass.test();
//        testArithmaticOperations();
        testArrayCreationOperations();
    }

    /**
     * Demonstrates arithmetic operations using the NumJ library.
     * It creates NDArray objects from data arrays and performs subtraction between them.
     * The result is printed to the console.
     */
    @SuppressWarnings("unchecked")
    private static void testArithmaticOperations() {
        try {
            NumJ numj = new NumJ<>();
//            NDArray<Integer> arr = numj.arange(100);
//            arr.printArray();
//            NDArray<Integer> arr1 =  arr.reshape(10, 10);
//            arr1.printArray();
//            int size = (int)Math.pow(10, 6);
//            NDArray<Number> arrayB = numj.arange(size, 100, 10000);

//            long t1 = System.currentTimeMillis();
            Number[][] dataA = {
                    {30, 20, 10L},
                    {1, 2, 3},
                    {4, 5, 6.0}
            };
            NDArray<Number> arrayA = numj.array(dataA);
            arrayA.printArray();
            Object[][][] dataB = {
                    {{10, 20, 30}},
                    {{40, 50, 60}}
            };
            NDArray<Number> arrayB = numj.array(dataB);
            arrayB.printArray();
            NDArray<?> resultArray = numj.add(arrayA, arrayB);

            System.out.println("Resultant Array after Addition:");
            resultArray.printArray();
        } catch (ShapeException e) {
            e.printStackTrace();
        }
    }
    private static void testArrayCreationOperations() throws ShapeException {
        NumJ numJ = new NumJ();
        NDArray arr  = numJ.arange(10, 21, 1);
        arr.printArray();
    }
}
