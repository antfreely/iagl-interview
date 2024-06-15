package com.iagl.avios.calculator.integration.api;

import com.iagl.avios.calculator.IaglAviosCalculatorApplication;
import com.iagl.avios.calculator.calculator.AviosCalculationService;
import com.iagl.avios.calculator.calculator.CalculationParameters;
import com.iagl.avios.calculator.db.cabin.bonus.CabinBonusConfigurationNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
    classes = {
        IaglAviosCalculatorApplication.class,
        ApiTestConfiguration.class
    },
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IaglAviosCalculatorControllerV1IntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AviosCalculationService mockCalculationService;

  @Test
  public void shouldGetAviosPointsGivenValidAirportCodes() throws Exception {
    // Given
    final String queryParameters = "?airportCodeArrival=ABC&airportCodeDeparture=XYZ";
    when(mockCalculationService.calculateAviosPoints(any(CalculationParameters.class)))
        .thenReturn(500);

    // When
    final String expectedResponseBody = "{\"aviosPoints\":500}";
    mockMvc.perform(get("/v1/avios-calculator-service" + queryParameters))
        // Then
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string(expectedResponseBody));
  }

  @Test
  public void shouldGetErrorWhenProvidingEmptyStringForAirportCodes() throws Exception {
    // Given
    final String queryParameters = "?airportCodeArrival=&airportCodeDeparture=";

    // When
    final String expectedResponseBody = "{\"errorMessage\":\"Invalid query parameters: ,\"}";
    mockMvc.perform(get("/v1/avios-calculator-service" + queryParameters))
        // Then
        .andExpect(status().is4xxClientError())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string(expectedResponseBody));
  }

  @ParameterizedTest
  @CsvSource(value = {
      "ABC!X;YZ!Invalid query parameters: X;YZ",
      "A;BC!X;YZ!Invalid query parameters: A;BC,X;YZ",
      "ABC!ABC!Airport codes can't be the same"
  },
      delimiter = '!')
  public void shouldGetErrorWhenProvidingInvalidAirportCode(String arrival, String departure, String errorMessage) throws Exception {
    // Given
    final String queryParameters = String.format("?airportCodeArrival=%s&airportCodeDeparture=%s", arrival, departure);

    // When
    final String expectedResponseBody = String.format("{\"errorMessage\":\"%s\"}", errorMessage);
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

  @Test
  public void shouldGetErrorWhenCalculatorThrowsError() throws Exception {
    // Given
    final String queryParameters = "?airportCodeArrival=ABC&airportCodeDeparture=XYZ";
    when(mockCalculationService.calculateAviosPoints(any(CalculationParameters.class)))
        .thenThrow(new CabinBonusConfigurationNotFoundException("My error"));

    // When
    final String expectedResponseBody = "{\"errorMessage\":\"Issue with the server\"}";
    mockMvc.perform(get("/v1/avios-calculator-service" + queryParameters))
        // Then
        .andExpect(status().is5xxServerError())
        .andExpect(content().contentType("application/json"))
        .andExpect(content().string(expectedResponseBody));
  }
}
