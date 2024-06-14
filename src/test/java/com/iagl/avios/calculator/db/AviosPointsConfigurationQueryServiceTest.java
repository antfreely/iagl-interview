package com.iagl.avios.calculator.db;

import com.iagl.avios.calculator.calculator.CabinCode;
import com.iagl.avios.calculator.db.cabin.bonus.CabinBonus;
import com.iagl.avios.calculator.db.cabin.bonus.CabinBonusDao;
import com.iagl.avios.calculator.db.route.RouteDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AviosPointsConfigurationQueryServiceTest {
  private AviosPointsConfigurationQueryService underTest;
  private int defaultValue;
  private CabinBonusDao cabinBonusDao;
  private RouteDao routeDao;

  @BeforeEach
  public void setup() {
    this.defaultValue = 500;
    this.cabinBonusDao = mock(CabinBonusDao.class);
    this.routeDao = mock(RouteDao.class);
    this.underTest = new AviosPointsConfigurationQueryService(defaultValue, cabinBonusDao, routeDao);
  }

  @Test
  public void shouldUseDefaultPoints() {
    // Given
    final String airportCodeDeparture = "ABC";
    final String airportCodeArrival = "XYZ";
    when(routeDao.findPointsForRoute(eq("ABC"), eq("XYZ")))
        .thenReturn(List.of());

    // When
    final int actual = underTest.findPointsForRoute(airportCodeDeparture, airportCodeArrival);

    // Then
    assertEquals(defaultValue, actual);
  }

  @Test
  public void shouldUseQueriedPoints() {
    // Given
    final String airportCodeDeparture = "ABC";
    final String airportCodeArrival = "XYZ";
    when(routeDao.findPointsForRoute(eq("ABC"), eq("XYZ")))
        .thenReturn(List.of(750));

    // When
    final int actual = underTest.findPointsForRoute(airportCodeDeparture, airportCodeArrival);

    // Then
    verify(routeDao, times(1)).findPointsForRoute(eq("ABC"), eq("XYZ"));
    assertEquals(750, actual);
  }

  @Test
  public void shouldFindQueriedMultiplier() {
    // Given
    final CabinCode cabinCode = CabinCode.F;
    final CabinBonus cabinBonus = new CabinBonus();
    cabinBonus.setBonus(20);
    when(cabinBonusDao.findById(eq(cabinCode)))
        .thenReturn(Optional.of(cabinBonus));

    // When
    final int actual = underTest.findMultiplierForCabinCode(cabinCode);

    // Then
    verify(cabinBonusDao, times(1)).findById(eq(cabinCode));
    assertEquals(20, actual);
  }
}