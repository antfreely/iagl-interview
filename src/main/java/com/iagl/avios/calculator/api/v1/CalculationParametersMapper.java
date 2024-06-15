package com.iagl.avios.calculator.api.v1;

import com.iagl.avios.calculator.api.v1.exception.AirportCodesAreSameException;
import com.iagl.avios.calculator.api.v1.exception.InvalidCabinCodeException;
import com.iagl.avios.calculator.calculator.CabinCode;
import com.iagl.avios.calculator.calculator.CalculationParameters;

public class CalculationParametersMapper {
  public static CalculationParameters from(String airportCodeArrival, String airportCodeDeparture, String cabinCode) {
    validateAirportCodes(airportCodeArrival, airportCodeDeparture);
    return CalculationParameters.builder()
        .airportCodeArrival(airportCodeArrival)
        .airportCodeDeparture(airportCodeDeparture)
        .cabinCode(getCabinCode(cabinCode))
        .build();
  }

  private static void validateAirportCodes(String airportCodeArrival, String airportCodeDeparture) {
    if (airportCodeArrival.equals(airportCodeDeparture)) {
      throw new AirportCodesAreSameException();
    }
  }

  private static CabinCode getCabinCode(String cabinCode) {
    try {
      return cabinCode == null || cabinCode.isEmpty() ? CabinCode.M : CabinCode.valueOf(cabinCode);
    } catch (IllegalArgumentException ex) {
      throw new InvalidCabinCodeException("Invalid cabin code provided: " + cabinCode);
    }
  }
}