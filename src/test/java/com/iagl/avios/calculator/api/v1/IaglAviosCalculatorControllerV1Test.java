package com.iagl.avios.calculator.api.v1;

import com.iagl.avios.calculator.api.v1.exception.InvalidCabinCodeException;
import com.iagl.avios.calculator.calculator.AviosCalculationService;
import com.iagl.avios.calculator.calculator.CalculationParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
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
    assertEquals(500, actualResponse.getBody().getAviosPoints());
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
    assertEquals(500, actualResponse.getBody().getAviosPoints());
  }


  @Test
  public void shouldCatchInvalidAirportCodes() {
    // Given
    final String airportCodeArrival = "AB;C";
    final String airportCodeDeparture = "XYX";
    final String cabinCode = "MMM";

    // When
    InvalidCabinCodeException exception = assertThrows(InvalidCabinCodeException.class, () -> underTest.getAviosCalculationWithRequestBody(airportCodeArrival, airportCodeDeparture, cabinCode));

    // Then
    verify(aviosCalculationService, never()).calculateAviosPoints(any(CalculationParameters.class));
    assertEquals("Invalid cabin code provided: MMM", exception.getMessage());
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