package com.iagl.avios.calculator.integration.api;

import com.iagl.avios.calculator.calculator.AviosCalculationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class ApiTestConfiguration {
  @Bean
  public AviosCalculationService aviosCalculationService() {
    return mock(AviosCalculationService.class);
  }
}
