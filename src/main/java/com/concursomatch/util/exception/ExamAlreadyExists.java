package com.concursomatch.util.exception;

public class ExamAlreadyExists extends RuntimeException{

	public ExamAlreadyExists(){}

	public ExamAlreadyExists(String message) {
		super(message);
	}

	public ExamAlreadyExists(String message, Throwable cause) {
		super(message, cause);
	}

}