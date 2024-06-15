package com.iagl.avios.calculator.api.v1;

import com.iagl.avios.calculator.calculator.AviosCalculationService;
import com.iagl.avios.calculator.calculator.CalculationParameters;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class that contains the API endpoint that allows a client to retrieve the number of Avios Points earned for a trip
 */
@RestController
public class IaglAviosCalculatorControllerV1 {
  private static final String NON_EMPTY = "^[A-Za-z]+$";
  private static final String ALLOW_EMPTY = "^[A-Z]*$";

  private final AviosCalculationService aviosCalculationService;

  @Autowired
  public IaglAviosCalculatorControllerV1(AviosCalculationService aviosCalculationService) {
    this.aviosCalculationService = aviosCalculationService;
  }

  @GetMapping(value = "/v1/avios-calculator-service", produces = "application/json")
  public ResponseEntity<CalculationResponseDtoV1> getAviosCalculationWithRequestBody(
      @RequestParam @Pattern(regexp = NON_EMPTY) String airportCodeArrival,
      @RequestParam @Pattern(regexp = NON_EMPTY) String airportCodeDeparture,
      @RequestParam(required = false) @Pattern(regexp = ALLOW_EMPTY) String cabinCode) {
    final CalculationParameters calculationParameters = CalculationParametersMapper.from(airportCodeArrival, airportCodeDeparture, cabinCode);
    final int aviosPoints = aviosCalculationService.calculateAviosPoints(calculationParameters);
    final CalculationResponseDtoV1 responseBody = CalculationResponseDtoV1.builder()
        .aviosPoints(aviosPoints)
        .build();

    return ResponseEntity.ok(responseBody);
  }
}
