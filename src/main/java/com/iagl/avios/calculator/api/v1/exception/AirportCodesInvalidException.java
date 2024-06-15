package com.iagl.avios.calculator.api.v1.exception;

public class AirportCodesInvalidException extends RuntimeException {
  public AirportCodesInvalidException() {
    super("Airport codes can't be the same");
  }
}
