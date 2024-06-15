package com.iagl.avios.calculator.calculator;

import org.springframework.stereotype.Service;

@Service
public class AviosCalculator {
  public int calculate(int routePoints, int multiplier) {
    return routePoints + (multiplier * routePoints / 100);
  }
}
