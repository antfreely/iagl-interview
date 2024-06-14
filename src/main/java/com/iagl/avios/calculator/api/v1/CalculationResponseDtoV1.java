package com.iagl.avios.calculator.api.v1;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculationResponseDtoV1 {
  private final Integer aviosPoints;
}
