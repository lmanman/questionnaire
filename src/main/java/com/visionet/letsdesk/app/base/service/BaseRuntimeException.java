/**
 * Copyright (c) 2012, DNE and/or SONY, All rights reserved.
 */
package com.visionet.letsdesk.app.base.service;

/**
 * Custom base runtimeException
 */
public abstract class BaseRuntimeException extends RuntimeException {
	private int source;
	private int severity;
	/**
	 * Override RuntimeException constructor
	 */
	public BaseRuntimeException() {
		super();
	}
	/**
	 * Override RuntimeException constructor
	 * @param message
	 * @param cause
	 */
	public BaseRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * Custome constructor
	 * @param message
	 * @param cause
	 * @param source Error source
	 * @param severity Error severity
	 */
	public BaseRuntimeException(String message, Throwable cause, int source, int severity) {
		super(message, cause);
		this.source=source;
		this.severity=severity;
	}
	/**
	 * Override RuntimeException constructor
	 * @param message
	 */
	public BaseRuntimeException(String message) {
		super(message);
	}
	/**
	 * Override RuntimeException constructor
	 * @param cause
	 */
	public BaseRuntimeException(Throwable cause) {
		super(cause);
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getSeverity() {
		return severity;
	}
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	
}
