package com.library.numj;

import com.library.numj.exceptions.ShapeException;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Object arr[] = {
//        		new int[] {1,2},
//                new double[] {1.1,2.1},
//                new String[] {"Hello", "Hi"},
//                new Object[] {new int[] {1,2}, new double[] {1.2, 3.4}},
                new Object[] {new Object[] {new int[] {1,2}}, new Object[] {new double[] {1.2, 3.4}}}
        };
        
		try {
			NDArray array = new NDArray<>(arr);
			array.shape();
			array.ndim();
			array.printArray();
			NDArray transposedArray = array.transpose();
			transposedArray.printArray();
			transposedArray.shape();
			transposedArray.ndim();
			NDArray flatArray = array.flatten();
			flatArray.printArray();
			flatArray.shape();
			flatArray.ndim();
			
//			Integer[][][] originalArray = {
//		            {
//		                {1, 2, 3},
//		                {4, 5, 6}
//		            },
//		            {
//		                {7, 8, 9},
//		                {10, 11, 12}
//		            }
//		        };

//	        NDArray ndArray = new NDArray<>(originalArray);
//	        System.out.println("Original Array:");
//	        ndArray.ndim();
//	        ndArray.shape();
//	        ndArray.printArray();
//	        
//	        System.out.println("Transposed Array:");
//	        NDArray transposed = ndArray.transpose();
//	        transposed.ndim();
//	        transposed.shape();
//	        transposed.printArray();
			
		} catch (ShapeException e) {
			e.printStackTrace();
		}
        
    }
}
