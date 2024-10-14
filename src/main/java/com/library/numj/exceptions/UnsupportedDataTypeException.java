package com.library.numj.exceptions;

/**
 * Exception thrown when an unsupported data type is encountered in the NumJ library.
 */
public class UnsupportedDataTypeException extends RuntimeException {
    /**
     * Constructs a new UnsupportedDataTypeException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public UnsupportedDataTypeException(String message) {
        super(message);
    }
}
