package com.library.numj;

import com.library.numj.exceptions.ShapeException;

public class App
{
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        testArithmaticOperations();
        
    }
    @SuppressWarnings("unchecked")
	private static void testArithmaticOperations() {
    	try {
    		
    		NumJ numj = new NumJ<>();

            Number[][] dataA = {
    				{30, 20, 10L},
    				{1, 2, 3},
                	{4, 5, 6.0}
            };
            NDArray<Number> arrayA = numj.array(dataA);

            Object[][][] dataB = {
               {{10, 20, 30}},
               {{40, 50, 60}}
            };
            NDArray<Number> arrayB = numj.array(dataB);

            NDArray<?> resultArray = numj.subtract(arrayA, arrayB);

            System.out.println("Resultant Array after Addition:");
            resultArray.printArray();
        } catch (ShapeException e) {
            e.printStackTrace();
        }
    }
}
