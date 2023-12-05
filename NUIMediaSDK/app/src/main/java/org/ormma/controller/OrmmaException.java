package org.ormma.controller;

public class OrmmaException extends Exception {
	public OrmmaException() {
	}

	public OrmmaException(String detailMessage) {
		super(detailMessage);
	}

	public OrmmaException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public OrmmaException(Throwable throwable) {
		super(throwable);
	}
}
