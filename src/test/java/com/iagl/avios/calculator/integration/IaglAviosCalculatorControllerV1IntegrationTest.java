package com.iagl.avios.calculator.integration;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class IaglAviosCalculatorControllerV1IntegrationTest extends IntegrationTestBase {
  @Test
  public void shouldGetAviosPointsGivenValidAirportCodes() throws Exception {
    // Given
    final String queryParameters = "?airportCodeArrival=ABC&airportCodeDeparture=XYZ";

    // When
    final String expectedResponseBody = "{\"aviosPoints\":500}";
    mockMvc.perform(get("/v1/avios-calculator-service" + queryParameters))
        // Then
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string(expectedResponseBody));
  }

  @Test
  public void shouldGetErrorWhenProvidingInvalidAirportCode() throws Exception {
    // Given
    final String queryParameters = "?airportCodeArrival=ABC&airportCodeDeparture=X;YZ";

    // When
    final String expectedResponseBody = "{\"errorMessage\":\"Invalid query parameters: X;YZ\"}";
    mockMvc.perform(get("/v1/avios-calculator-service" + queryParameters))
        // Then
        .andExpect(status().is4xxClientError())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string(expectedResponseBody));
  }

  @Test
  public void shouldGetErrorWhenProvidingInvalidCabinCode() throws Exception {
    // Given
    final String queryParameters = "?airportCodeArrival=ABC&airportCodeDeparture=XYZ&cabinCode=MMM";

    // When
    final String expectedResponseBody = "{\"errorMessage\":\"Invalid cabin code provided: MMM\"}";
    mockMvc.perform(get("/v1/avios-calculator-service" + queryParameters))
        // Then
        .andExpect(status().is4xxClientError())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string(expectedResponseBody));
  }
}
