package com.concursomatch.util.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

public class GlobalExceptionHandlerTest {

	private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

	private ServletWebRequest createWebRequest(String uri) {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI(uri);
		return new ServletWebRequest(request);
	}

	@Test
	public void testHandleResourceNotFoundException() {
		// Assuming ResourceNotFoundException is available in your codebase.
		ResourceNotFoundException ex = new ResourceNotFoundException("Test resource not found");
		ServletWebRequest webRequest = createWebRequest("/test/resource");

		ResponseEntity<Object> responseEntity = exceptionHandler.handleResourceNotFoundException(ex, webRequest);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

		ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
		assertNotNull(errorResponse);
		assertEquals(HttpStatus.NOT_FOUND.value(), errorResponse.getStatus());
		assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), errorResponse.getError());
		assertEquals("Test resource not found", errorResponse.getMessage());
		assertEquals("/test/resource", errorResponse.getPath());
		assertNotNull(errorResponse.getTimestamp());
	}

	@Test
	public void testHandleAlreadyExistsExceptionCandidate() {
		// Testing with CandidateAlreadyExistsException.
		CandidateAlreadyExistsException ex = new CandidateAlreadyExistsException("Candidate already exists");
		ServletWebRequest webRequest = createWebRequest("/test/candidate");

		ResponseEntity<Object> responseEntity = exceptionHandler.handleAlreadyExistsException(ex, webRequest);
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

		ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
		assertNotNull(errorResponse);
		assertEquals(HttpStatus.CONFLICT.value(), errorResponse.getStatus());
		assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), errorResponse.getError());
		assertEquals("Candidate already exists", errorResponse.getMessage());
		assertEquals("/test/candidate", errorResponse.getPath());
		assertNotNull(errorResponse.getTimestamp());
	}

	@Test
	public void testHandleAlreadyExistsExceptionExam() {
		// Testing with ExamAlreadyExistsException.
		ExamAlreadyExistsException ex = new ExamAlreadyExistsException("Exam already exists");
		ServletWebRequest webRequest = createWebRequest("/test/exam");

		ResponseEntity<Object> responseEntity = exceptionHandler.handleAlreadyExistsException(ex, webRequest);
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

		ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
		assertNotNull(errorResponse);
		assertEquals(HttpStatus.CONFLICT.value(), errorResponse.getStatus());
		assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), errorResponse.getError());
		assertEquals("Exam already exists", errorResponse.getMessage());
		assertEquals("/test/exam", errorResponse.getPath());
		assertNotNull(errorResponse.getTimestamp());
	}

	@Test
	public void testHandleRuntimeException() {
		RuntimeException ex = new RuntimeException("Runtime exception occurred");
		ServletWebRequest webRequest = createWebRequest("/test/runtime");

		ResponseEntity<Object> responseEntity = exceptionHandler.handleRuntimeException(ex, webRequest);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
		assertNotNull(errorResponse);
		assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getStatus());
		assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), errorResponse.getError());
		assertEquals("Runtime exception occurred", errorResponse.getMessage());
		assertEquals("/test/runtime", errorResponse.getPath());
		assertNotNull(errorResponse.getTimestamp());
	}

	@Test
	public void testHandleAllExceptions() {
		Exception ex = new Exception("Generic exception occurred");
		ServletWebRequest webRequest = createWebRequest("/test/generic");

		ResponseEntity<Object> responseEntity = exceptionHandler.handleAllExceptions(ex, webRequest);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

		ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
		assertNotNull(errorResponse);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponse.getStatus());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errorResponse.getError());
		assertEquals("Generic exception occurred", errorResponse.getMessage());
		assertEquals("/test/generic", errorResponse.getPath());
		assertNotNull(errorResponse.getTimestamp());
	}
}
