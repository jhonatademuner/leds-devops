package com.concursomatch.util.exception;

public class ExamAlreadyExistsException extends RuntimeException{

	public ExamAlreadyExistsException(){}

	public ExamAlreadyExistsException(String message) {
		super(message);
	}

	public ExamAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

}