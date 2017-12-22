package com.ikasoa.core;

/**
 * 通用异常类
 * 
 * @author <a href="mailto:larry7696@gmail.com">Larry</a>
 * @version 0.2
 */
public class STException extends Exception {

	private static final long serialVersionUID = 1L;

	public STException() {
		super();
	}

	public STException(String message) {
		super(message);
	}

	public STException(Throwable cause) {
		super(cause);
	}

	public STException(String message, Throwable cause) {
		super(message, cause);
	}
}
