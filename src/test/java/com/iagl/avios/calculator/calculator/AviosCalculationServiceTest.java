package com.iagl.avios.calculator.calculator;

import com.iagl.avios.calculator.db.AviosPointsConfigurationQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AviosCalculationServiceTest {
  private AviosCalculationService underTest;
  private AviosPointsConfigurationQueryService aviosPointsConfigurationQueryService;
  private AviosCalculator aviosCalculator;

  @BeforeEach
  public void setup() {
    this.aviosPointsConfigurationQueryService = mock(AviosPointsConfigurationQueryService.class);
    this.aviosCalculator = mock(AviosCalculator.class);
    this.underTest = new AviosCalculationService(aviosPointsConfigurationQueryService, aviosCalculator);
  }

  @Test
  public void shouldCalculateAviosPoints() {
    // Given
    final CalculationParameters calculationParameters = CalculationParameters.builder()
        .airportCodeDeparture("ABC")
        .airportCodeArrival("XYZ")
        .cabinCode(CabinCode.M)
        .build();
    when(aviosPointsConfigurationQueryService.findPointsForRoute(eq("ABC"), eq("XYZ"))).thenReturn(500);
    when(aviosPointsConfigurationQueryService.findMultiplierForCabinCode(eq(CabinCode.M))).thenReturn(0);
    when(aviosCalculator.calculate(eq(500), eq(0))).thenReturn(500);

    // When
    final int actual = underTest.calculateAviosPoints(calculationParameters);

    //Then
    verify(aviosPointsConfigurationQueryService, times(1)).findPointsForRoute(eq("ABC"), eq("XYZ"));
    verify(aviosPointsConfigurationQueryService, times(1)).findMultiplierForCabinCode(eq(CabinCode.M));
    verify(aviosCalculator, times(1)).calculate(eq(500), eq(0));
    assertEquals(500, actual);
  }

}