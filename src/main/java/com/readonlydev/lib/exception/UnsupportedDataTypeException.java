package com.readonlydev.lib.exception;

import java.io.IOException;

public class UnsupportedDataTypeException extends IOException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an UnsupportedDataTypeException with no detail
     * message.
     */
    public UnsupportedDataTypeException() {
    super();
    }

    /**
     * Constructs an UnsupportedDataTypeException with the specified 
     * message.
     * 
     * @param s The detail message.
     */
    public UnsupportedDataTypeException(String s) {
    super(s);
    }
}
