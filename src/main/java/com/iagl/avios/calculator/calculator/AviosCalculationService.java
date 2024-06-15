package com.iagl.avios.calculator.calculator;

import com.iagl.avios.calculator.db.AviosPointsConfigurationQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Main class for querying figures to then use for calculating the Avios points earned.
 */
@Service
public class AviosCalculationService {
  private final AviosPointsConfigurationQueryService aviosPointsConfigurationQueryService;
  private final AviosCalculator aviosCalculator;

  @Autowired
  public AviosCalculationService(AviosPointsConfigurationQueryService aviosPointsConfigurationQueryService, AviosCalculator aviosCalculator) {
    this.aviosPointsConfigurationQueryService = aviosPointsConfigurationQueryService;
    this.aviosCalculator = aviosCalculator;
  }

  public int calculateAviosPoints(CalculationParameters calculationParameters) {
    final int routePoints = aviosPointsConfigurationQueryService.findPointsForRoute(calculationParameters.getAirportCodeDeparture(), calculationParameters.getAirportCodeArrival());
    final int multiplier = aviosPointsConfigurationQueryService.findMultiplierForCabinCode(calculationParameters.getCabinCode());

    return aviosCalculator.calculate(routePoints, multiplier);
  }
}
