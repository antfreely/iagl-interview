package com.iagl.avios.calculator.api.v1.exception;

public class IaglInvalidRequestException extends RuntimeException {
  public IaglInvalidRequestException(String message) {
    super(message);
  }
}
