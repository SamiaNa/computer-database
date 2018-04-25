package com.excilys.formation.exceptions;

public class MapperException extends Exception{
	
	public MapperException() {
		super();
	}
	
	public MapperException(String message) {
		super(message);
	}
	
	public MapperException(Exception e) {
		super(e);
	}
}
