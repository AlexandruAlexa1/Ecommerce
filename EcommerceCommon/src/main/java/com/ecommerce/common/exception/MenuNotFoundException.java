package com.ecommerce.common.exception;

public class MenuNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public MenuNotFoundException(String message) {
		super(message);
	}
}
