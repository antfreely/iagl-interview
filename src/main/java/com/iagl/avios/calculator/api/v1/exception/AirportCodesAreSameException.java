package com.iagl.avios.calculator.api.v1.exception;

public class AirportCodesAreSameException extends IaglInvalidRequestException {
  public AirportCodesAreSameException() {
    super("Airport codes can't be the same");
  }
}
