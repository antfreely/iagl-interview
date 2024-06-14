package com.iagl.avios.calculator.api.v1.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorMessageDtoV1 {
  private final String errorMessage;
}
