package com.iagl.avios.calculator.integration;

import com.iagl.avios.calculator.IaglAviosCalculatorApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
    classes = {
        IaglAviosCalculatorApplication.class,
        IntegrationTestConfiguration.class
    },
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IntegrationTestBase {
  @Autowired
  protected MockMvc mockMvc;
}
