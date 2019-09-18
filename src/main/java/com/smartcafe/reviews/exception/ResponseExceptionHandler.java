package com.smartcafe.reviews.exception;

import java.util.Set;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResponseExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ReviewNotFoundException.class)
	@ResponseBody
	public ErrorResponse handleReviewNotFound(final ReviewNotFoundException ex) {
		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), "NOT_FOUND", ex.getMessage());
	}

	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	@ResponseBody
	public ErrorResponse handleMediaType(final HttpMediaTypeNotAcceptableException ex) {
		return new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "UNSUPPORTED_MEDIA_TYPE",
				"media type is supported only for XML or JSON");
	}
	
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public ErrorResponse handleHttpMessageNotReadable(final HttpMessageNotReadableException ex) {
		return new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "NOT_ACCEPTABLE",
				"unsupported XML or JSON(JSON parse error)");
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(TransactionSystemException.class)
	@ResponseBody
	public ErrorResponse handleConstraintViolation(final TransactionSystemException ex) {
		StringBuilder message = new StringBuilder();
		Throwable cause = ex.getCause();
		if (cause instanceof RollbackException) {
			RollbackException rollbackException = (RollbackException) cause;
			Throwable innerCause = rollbackException.getCause();
			if (innerCause instanceof ConstraintViolationException) {
				ConstraintViolationException constraintViolationException = (ConstraintViolationException) innerCause;
				Set<ConstraintViolation<?>> constraintViolations = constraintViolationException
						.getConstraintViolations();
				constraintViolations.stream().forEach(violation -> {
					message.append(violation.getPropertyPath() + ": " + violation.getMessageTemplate() + ", ");
				});
				return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "CONSTRAINT_VIOLATION",
						message.toString());
			}
		}
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR", "An unexpected internal server error occured");
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ErrorResponse handleException(final Exception ex) {
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR",
				"An unexpected internal server error occured");
	}

	public static class ErrorResponse {

		private final int code;
		private final String status;
		private final String message;

		public int getCode() {
			return code;
		}

		public String getStatus() {
			return status;
		}

		public String getMessage() {
			return message;
		}

		public ErrorResponse(int code, String status, String message) {
			super();
			this.code = code;
			this.status = status;
			this.message = message;
		}

	}
}