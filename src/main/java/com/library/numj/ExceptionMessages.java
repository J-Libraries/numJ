package com.library.numj;

import com.library.numj.enums.DType;

import java.util.List;

public final class ExceptionMessages {

	public static final String unsupportedOperation = "The operation you have provided is not supported on this type of value";
	public static String getShapeException(int ndim, List<Integer> shape)
	{
		shape.remove(shape.size()-1);
		ndim--;
		return "The requested array has an inhomogeneous shape after "
                + ndim + " dimensions. The detected shape was " + shape.toString()
                + " + inhomogeneous part.";
	}
	public static String negativeSizeException(int size)
	{
		return "IllegalArgumentException : "+size+". Size of the array can not be negative.";
	}
	public static String shapeMismatchedException(long inputShape, String outputShape)
	{
		return "ShapeException : Can not reshape array of size "+inputShape+" into shape"+outputShape;
	}
	public static String illegalDataType(DType dType)
	{
		return "IllegalArgumentException : Data type you have provided is not supported : "+dType;
	}
}
