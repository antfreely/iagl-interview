package com.iagl.avios.calculator.calculator;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CalculationParameters {
  private final String airportCodeArrival;
  private final String airportCodeDeparture;
  private final CabinCode cabinCode;
}
