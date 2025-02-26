package com.concursomatch.util.exception;

public class CandidateAlreadyExists extends RuntimeException{

	public CandidateAlreadyExists(){}

	public CandidateAlreadyExists(String message) {
		super(message);
	}

	public CandidateAlreadyExists(String message, Throwable cause) {
		super(message, cause);
	}

}