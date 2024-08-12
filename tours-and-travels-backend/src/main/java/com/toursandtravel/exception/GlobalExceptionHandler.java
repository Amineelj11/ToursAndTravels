package com.toursandtravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.toursandtravel.dto.CommonApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<CommonApiResponse> handleUserNotFoundException(UserNotFoundException ex) {
		String responseMessage = ex.getMessage();

		CommonApiResponse apiResponse = CommonApiResponse.builder().responseMessage(responseMessage).isSuccess(false)
				.build();
		return new ResponseEntity<CommonApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(UserSaveFailedException.class)
	public ResponseEntity<CommonApiResponse> handleUserRegistrationFailedException(UserSaveFailedException ex) {
		String responseMessage = ex.getMessage();

		CommonApiResponse apiResponse = CommonApiResponse.builder().responseMessage(responseMessage).isSuccess(false)
				.build();
		return new ResponseEntity<CommonApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(LocationSaveFailedException.class)
	public ResponseEntity<CommonApiResponse> handleLocationSaveFailedException(LocationSaveFailedException ex) {
		String responseMessage = ex.getMessage();

		CommonApiResponse apiResponse = CommonApiResponse.builder().responseMessage(responseMessage).isSuccess(false)
				.build();
		return new ResponseEntity<CommonApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(TransportSaveFailedException.class)
	public ResponseEntity<CommonApiResponse> handleTransportSaveFailedException(TransportSaveFailedException ex) {
		String responseMessage = ex.getMessage();

		CommonApiResponse apiResponse = CommonApiResponse.builder().responseMessage(responseMessage).isSuccess(false)
				.build();
		return new ResponseEntity<CommonApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(LodgingSaveFailedException.class)
	public ResponseEntity<CommonApiResponse> handleLodgingSaveFailedException(LodgingSaveFailedException ex) {
		String responseMessage = ex.getMessage();

		CommonApiResponse apiResponse = CommonApiResponse.builder().responseMessage(responseMessage).isSuccess(false)
				.build();
		return new ResponseEntity<CommonApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
