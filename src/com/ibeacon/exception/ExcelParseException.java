package com.ibeacon.exception;

public class ExcelParseException extends RunException {

	private static final long serialVersionUID = 1L;

	public ExcelParseException() {
		super();
	}

	public ExcelParseException(String message) {
		super(message);
	}

	public ExcelParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcelParseException(Throwable cause) {
		super(cause);
	}
}

