package com.library.numj;

import java.util.List;

final class ExceptionMessages {
	static String getShapeException(int ndim, List<Integer> shape)
	{
		shape.remove(shape.size()-1);
		ndim--;
		return "The requested array has an inhomogeneous shape after "
                + ndim + " dimensions. The detected shape was " + shape.toString()
                + " + inhomogeneous part.";
	}
	static String negativeSizeException(int size)
	{
		return "Invalid Argument : "+size+". Size of the array can not be negative.";
	}
}
