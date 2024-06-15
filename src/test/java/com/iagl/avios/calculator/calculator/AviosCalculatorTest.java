package com.iagl.avios.calculator.calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AviosCalculatorTest {
  @ParameterizedTest
  @CsvSource({
      "500, 10, 550", // simple
      "500, 0, 500", // handle 0%
      "500, 200, 1500", // handle more than double
      "10, 13, 11" // handle rounding down
  })
  public void shouldCalculateAsExpected(int routePoints, int multiplier, int expected) {
    // Given
    AviosCalculator underTest = new AviosCalculator();

    // When
    int actual = underTest.calculate(routePoints, multiplier);

    // Then
    assertEquals(expected, actual);
  }
}