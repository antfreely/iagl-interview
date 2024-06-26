package com.iagl.avios.calculator.api.v1.exception;

import com.iagl.avios.calculator.db.cabin.bonus.CabinBonusConfigurationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class IaglAviosCalculatorExceptionHandler {
  @ExceptionHandler(IaglInvalidRequestException.class)
  public ResponseEntity<ErrorMessageDtoV1> handleInvalidCabinCode(IaglInvalidRequestException ex) {
    final ErrorMessageDtoV1 responseBody = ErrorMessageDtoV1.builder()
        .errorMessage(ex.getMessage())
        .build();
    return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<ErrorMessageDtoV1> handleInvalidConstraint(HandlerMethodValidationException ex) {
    final ErrorMessageDtoV1 responseBody = ErrorMessageDtoV1.builder()
        .errorMessage(getMessageFromInvalidArguments(ex.getAllValidationResults()))
        .build();
    return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CabinBonusConfigurationNotFoundException.class)
  public ResponseEntity<ErrorMessageDtoV1> handleCabinBonusNotFound(CabinBonusConfigurationNotFoundException ex) {
    final ErrorMessageDtoV1 responseBody = ErrorMessageDtoV1.builder()
        .errorMessage("Issue with the server")
        .build();
    return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private String getMessageFromInvalidArguments(List<ParameterValidationResult> validationResults) {
    return "Invalid query parameters: " + validationResults.stream()
        .map(ParameterValidationResult::getArgument)
        .filter(Objects::nonNull)
        .map(Object::toString)
        .collect(Collectors.joining(","));
  }
}
