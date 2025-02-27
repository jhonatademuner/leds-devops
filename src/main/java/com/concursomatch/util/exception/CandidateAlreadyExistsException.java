package com.concursomatch.util.exception;

public class CandidateAlreadyExistsException extends RuntimeException{

	public CandidateAlreadyExistsException(){}

	public CandidateAlreadyExistsException(String message) {
		super(message);
	}

	public CandidateAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

}