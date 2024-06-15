package com.iagl.avios.calculator.api.v1;

import com.iagl.avios.calculator.api.v1.exception.AirportCodesAreSameException;
import com.iagl.avios.calculator.api.v1.exception.InvalidCabinCodeException;
import com.iagl.avios.calculator.calculator.AviosCalculationService;
import com.iagl.avios.calculator.calculator.CalculationParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class IaglAviosCalculatorControllerV1Test {
  private AviosCalculationService aviosCalculationService;
  private IaglAviosCalculatorControllerV1 underTest;

  @BeforeEach
  public void setup() {
    aviosCalculationService = mock(AviosCalculationService.class);
    underTest = new IaglAviosCalculatorControllerV1(aviosCalculationService);
  }

  @Test
  public void shouldCallCalculatorServiceGivenValidAirportCodes() {
    // Given
    final String airportCodeArrival = "ABC";
    final String airportCodeDeparture = "XYX";
    final String cabinCode = null;
    when(aviosCalculationService.calculateAviosPoints(any(CalculationParameters.class)))
        .thenReturn(500);

    // When
    final ResponseEntity<CalculationResponseDtoV1> actualResponse = underTest.getAviosCalculationWithRequestBody(airportCodeArrival, airportCodeDeparture, cabinCode);

    // Then
    verify(aviosCalculationService, times(1)).calculateAviosPoints(any(CalculationParameters.class));
    assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    assertEquals(500, Objects.requireNonNull(actualResponse.getBody()).getAviosPoints());
  }

  @Test
  public void shouldCallCalculatorServiceGivenValidAirportCodesAndCabinCode() {
    // Given
    final String airportCodeArrival = "ABC";
    final String airportCodeDeparture = "XYX";
    final String cabinCode = "M";
    when(aviosCalculationService.calculateAviosPoints(any(CalculationParameters.class)))
        .thenReturn(500);

    // When
    final ResponseEntity<CalculationResponseDtoV1> actualResponse = underTest.getAviosCalculationWithRequestBody(airportCodeArrival, airportCodeDeparture, cabinCode);

    // Then
    verify(aviosCalculationService, times(1)).calculateAviosPoints(any(CalculationParameters.class));
    assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    assertEquals(500, Objects.requireNonNull(actualResponse.getBody()).getAviosPoints());
  }

  @Test
  public void shouldCatchAirportCodesThatAreTheSame() {
    // Given
    final String airportCodeArrival = "ABC";
    final String airportCodeDeparture = "ABC";
    final String cabinCode = "M";

    // When
    AirportCodesAreSameException exception = assertThrows(AirportCodesAreSameException.class, () -> underTest.getAviosCalculationWithRequestBody(airportCodeArrival, airportCodeDeparture, cabinCode));

    // Then
    verify(aviosCalculationService, never()).calculateAviosPoints(any(CalculationParameters.class));
    assertEquals("Airport codes can't be the same", exception.getMessage());
  }

  @Test
  public void shouldCatchInvalidCabinCode() {
    // Given
    final String airportCodeArrival = "ABC";
    final String airportCodeDeparture = "XYX";
    final String cabinCode = ";";

    // When
    InvalidCabinCodeException exception = assertThrows(InvalidCabinCodeException.class, () -> underTest.getAviosCalculationWithRequestBody(airportCodeArrival, airportCodeDeparture, cabinCode));

    // Then
    verify(aviosCalculationService, never()).calculateAviosPoints(any(CalculationParameters.class));
    assertEquals("Invalid cabin code provided: ;", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionThrownByCalculatorService() {
    // Given
    final String airportCodeArrival = "ABC";
    final String airportCodeDeparture = "XYX";
    final String cabinCode = null;
    when(aviosCalculationService.calculateAviosPoints(any(CalculationParameters.class)))
        .thenThrow(new RuntimeException("My Exception"));

    // When
    RuntimeException exception = assertThrows(RuntimeException.class, () -> underTest.getAviosCalculationWithRequestBody(airportCodeArrival, airportCodeDeparture, cabinCode));

    // Then
    assertEquals("My Exception", exception.getMessage());
  }
}