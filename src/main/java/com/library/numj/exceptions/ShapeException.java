package com.library.numj.exceptions;

/**
 * Exception thrown when there is a mismatch in array shapes during operations.
 */
public class ShapeException extends Exception {
	/**
	 * Constructs a new ShapeException with the specified detail message.
	 *
	 * @param message the detail message.
	 */
	public ShapeException(String message) {
		super(message);
	}
}
