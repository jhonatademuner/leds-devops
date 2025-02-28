package com.concursomatch.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(Exception ex, WebRequest request) {
		String path = ((ServletWebRequest) request).getRequest().getRequestURI();
		LOG.warn("Resource not found at {}: {}", path, ex.getMessage(), ex);

		ErrorResponse errorResponse = ErrorResponse.builder()
				.timestamp(new Date())
				.status(HttpStatus.NOT_FOUND.value())
				.error(HttpStatus.NOT_FOUND.getReasonPhrase())
				.message(ex.getMessage())
				.path(path)
				.build();

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({CandidateAlreadyExistsException.class,
			ExamAlreadyExistsException.class})
	public ResponseEntity<Object> handleAlreadyExistsException(Exception ex, WebRequest request) {
		String path = ((ServletWebRequest) request).getRequest().getRequestURI();
		LOG.warn("Entity already exists excption at {}: {}", path, ex.getMessage(), ex);

		ErrorResponse errorResponse = ErrorResponse.builder()
				.timestamp(new Date())
				.status(HttpStatus.CONFLICT.value())
				.error(HttpStatus.CONFLICT.getReasonPhrase())
				.message(ex.getMessage())
				.path(path)
				.build();

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleRuntimeException(Exception ex, WebRequest request) {
		String path = ((ServletWebRequest) request).getRequest().getRequestURI();
		LOG.error("Runtime exception at {}: {}", path, ex.getMessage(), ex);

		ErrorResponse errorResponse = ErrorResponse.builder()
				.timestamp(new Date())
				.status(HttpStatus.BAD_REQUEST.value())
				.error(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.message(ex.getMessage())
				.path(path)
				.build();

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({Exception.class, IllegalStateException.class})
	public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		String path = ((ServletWebRequest) request).getRequest().getRequestURI();
		LOG.error("Unhandled exception at {}: {}", path, ex.getMessage(), ex);

		ErrorResponse errorResponse = ErrorResponse.builder()
				.timestamp(new Date())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
				.message(ex.getMessage())
				.path(path)
				.build();

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}