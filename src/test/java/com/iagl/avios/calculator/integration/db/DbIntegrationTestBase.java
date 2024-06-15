package com.iagl.avios.calculator.integration.db;

import com.iagl.avios.calculator.db.cabin.bonus.CabinBonusDao;
import com.iagl.avios.calculator.db.route.RouteDao;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class DbIntegrationTestBase {
  @Autowired
  protected RouteDao routeDao;

  @Autowired
  protected CabinBonusDao cabinBonusDao;

  @BeforeEach
  public void setup() {
    routeDao.deleteAll();
    cabinBonusDao.deleteAll();
  }
}
