package com.ibeacon.exception;

public class RunException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String property;

	private String[] values;

	public RunException() {
		super();
	}

	public RunException(String message) {
		super(message);
	}

	public RunException(String message, String property) {
		super(message);
		this.property = property;
	}

	public RunException(String message, String[] values) {
		super(message);
		this.values = values;
	}

	public RunException(String message, Throwable cause) {
		super(message, cause);
	}

	public RunException(Throwable cause) {
		super(cause);
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String[] getValues() {
		return this.values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}
}
