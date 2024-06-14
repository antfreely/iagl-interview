package com.iagl.avios.calculator.integration.db;

import com.iagl.avios.calculator.db.route.Route;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RouteDaoIntegrationTest extends DbIntegrationTestBase {

  @ParameterizedTest
  @CsvSource({"ABC, XYZ, 650", "XYZ, ABC, 650"})
  public void shouldQueryRouteTableAndGetPointsRegardlessOfDirectionOfRoute(String departure, String arrival, int aviosEarned) {
    // Given
    routeDao.save(getRoute("ABC", "XYZ", 650));
    routeDao.save(getRoute("ABC", "VW", 750));
    routeDao.save(getRoute("VW", "XYZ", 800));

    // When
    final List<Integer> pointsForRoute = routeDao.findPointsForRoute(departure, arrival);

    // Then
    assertEquals(1, pointsForRoute.size());
    assertEquals(List.of(aviosEarned), pointsForRoute);
  }

  @Test
  public void shouldReturnEmptyListWhenRouteIsNotListed() {
    // Given
    routeDao.save(getRoute("ABC", "XYZ", 650));
    routeDao.save(getRoute("ABC", "VW", 750));
    routeDao.save(getRoute("VW", "XYZ", 800));

    // When
    final List<Integer> pointsForRoute = routeDao.findPointsForRoute("OPQ", "RST");

    // Then
    assertEquals(0, pointsForRoute.size());
  }

  private Route getRoute(String departure, String arrival, int aviosEarned) {
    Route route = new Route();
    route.setAirportCodeDeparture(departure);
    route.setAirportCodeArrival(arrival);
    route.setAviosEarned(aviosEarned);
    return route;
  }
}