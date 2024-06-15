package com.iagl.avios.calculator.api.v1;

import com.iagl.avios.calculator.api.v1.exception.InvalidCabinCodeException;
import com.iagl.avios.calculator.calculator.CabinCode;
import com.iagl.avios.calculator.calculator.CalculationParameters;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculationParametersMapperTest {

  @ParameterizedTest
  @ValueSource(strings = {"M"})
  @NullAndEmptySource
  public void shouldMapValidAirportCodesAndDefaultCabinCode(String cabinCode) {
    // Given
    final String arrival = "ABC";
    final String departure = "XYZ";

    // When
    final CalculationParameters actual = CalculationParametersMapper.from(arrival, departure, cabinCode);

    // Then
    final CalculationParameters expected = CalculationParameters.builder()
        .airportCodeArrival(arrival)
        .airportCodeDeparture(departure)
        .cabinCode(CabinCode.M)
        .build();
    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @ParameterizedTest
  @ValueSource(strings = {"W", "J", "F"})
  public void shouldMapValidAirportCodesAndCabinCodes(String cabinCode) {
    // Given
    final String arrival = "ABC";
    final String departure = "XYZ";

    // When
    final CalculationParameters actual = CalculationParametersMapper.from(arrival, departure, cabinCode);

    // Then
    final CalculationParameters expected = CalculationParameters.builder()
        .airportCodeArrival(arrival)
        .airportCodeDeparture(departure)
        .cabinCode(CabinCode.valueOf(cabinCode))
        .build();
    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @ParameterizedTest
  @ValueSource(strings = {"ZZZ", ":", "123"})
  public void shouldThrowExceptionForInvalidCabinCode(String cabinCode) {
    // Given
    final String arrival = "ABC";
    final String departure = "XYZ";

    // When
    InvalidCabinCodeException exception = assertThrows(InvalidCabinCodeException.class, () -> CalculationParametersMapper.from(arrival, departure, cabinCode));

    // Then
    assertEquals("Invalid cabin code provided: " + cabinCode, exception.getMessage());
  }

}