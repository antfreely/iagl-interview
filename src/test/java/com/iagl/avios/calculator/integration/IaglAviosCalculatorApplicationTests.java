package com.iagl.avios.calculator.integration;

import com.iagl.avios.calculator.db.cabin.bonus.CabinBonus;
import com.iagl.avios.calculator.db.cabin.bonus.CabinBonusDao;
import com.iagl.avios.calculator.db.route.Route;
import com.iagl.avios.calculator.db.route.RouteDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class IaglAviosCalculatorApplicationTests {
  @Autowired
  private RouteDao routeDao;

  @Autowired
  private CabinBonusDao cabinBonus;

  @Test
  public void shouldLoadContextAndLoadInitialData() {
    // Given

    // When
    final List<Route> allRoutes = routeDao.findAll();
    final List<CabinBonus> allCabinBonus = cabinBonus.findAll();

    // Then
    assertEquals(4, allCabinBonus.size());
    assertEquals(4, allRoutes.size());
  }
}
