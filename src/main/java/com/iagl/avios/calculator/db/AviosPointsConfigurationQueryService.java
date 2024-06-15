package com.iagl.avios.calculator.db;

import com.iagl.avios.calculator.calculator.CabinCode;
import com.iagl.avios.calculator.db.cabin.bonus.CabinBonus;
import com.iagl.avios.calculator.db.cabin.bonus.CabinBonusConfigurationNotFoundException;
import com.iagl.avios.calculator.db.cabin.bonus.CabinBonusDao;
import com.iagl.avios.calculator.db.route.RouteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Class in charge of querying for the configuration of routes and cabin bonuses
 */
@Service
public class AviosPointsConfigurationQueryService {
  private final int defaultRoutePoints;
  private final CabinBonusDao cabinBonusDao;
  private final RouteDao routeDao;

  @Autowired
  public AviosPointsConfigurationQueryService(
      @Value("${iagl.route.points.default}") int defaultRoutePoints,
      CabinBonusDao cabinBonusDao,
      RouteDao routeDao) {
    this.defaultRoutePoints = defaultRoutePoints;
    this.cabinBonusDao = cabinBonusDao;
    this.routeDao = routeDao;
  }

  public int findPointsForRoute(String airportCodeDeparture, String airportCodeArrival) {
    return routeDao.findPointsForRoute(airportCodeDeparture, airportCodeArrival).stream()
        .findFirst()
        .orElse(defaultRoutePoints);
  }

  public int findMultiplierForCabinCode(CabinCode cabinCode) {
    final CabinBonus cabinBonus = cabinBonusDao.findById(cabinCode)
        .orElseThrow(() -> new CabinBonusConfigurationNotFoundException("Issue fetching bonus details for cabin code: " + cabinCode));
    return cabinBonus.getBonus();
  }
}
