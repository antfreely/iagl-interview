package com.iagl.avios.calculator.api.v1;

import com.iagl.avios.calculator.calculator.AviosCalculationService;
import com.iagl.avios.calculator.calculator.CalculationParameters;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IaglAviosCalculatorControllerV1 {
  private final AviosCalculationService aviosCalculationService;

  @Autowired
  public IaglAviosCalculatorControllerV1(AviosCalculationService aviosCalculationService) {
    this.aviosCalculationService = aviosCalculationService;
  }

  @GetMapping(value = "/v1/avios-calculator-service", produces = "application/json")
  public ResponseEntity<CalculationResponseDtoV1> getAviosCalculationWithRequestBody(
      @RequestParam @Pattern(regexp = "^[A-Za-z]*$") String airportCodeArrival,
      @RequestParam @Pattern(regexp = "^[A-Za-z]*$") String airportCodeDeparture,
      @RequestParam(required = false) @Pattern(regexp = "^[A-Z]*$") String cabinCode) {
    final CalculationParameters calculationParameters = CalculationParametersMapper.from(airportCodeArrival, airportCodeDeparture, cabinCode);
    final int aviosPoints = aviosCalculationService.calculateAviosPoints(calculationParameters);
    final CalculationResponseDtoV1 responseBody = CalculationResponseDtoV1.builder()
        .aviosPoints(aviosPoints)
        .build();

    return ResponseEntity.ok(responseBody);
  }
}
