package com.readonlydev.lib.client.render.model;

public class InvalidModelFormatException extends RuntimeException {

	private static final long serialVersionUID = 6342202630552642961L;

	public InvalidModelFormatException() {
		super();
	}

	public InvalidModelFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidModelFormatException(String message) {
		super(message);
	}

	public InvalidModelFormatException(Throwable cause) {
		super(cause);
	}
}
