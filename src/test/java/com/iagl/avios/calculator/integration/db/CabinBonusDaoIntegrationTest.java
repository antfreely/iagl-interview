package com.iagl.avios.calculator.integration.db;

import com.iagl.avios.calculator.calculator.CabinCode;
import com.iagl.avios.calculator.db.cabin.bonus.CabinBonus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CabinBonusDaoIntegrationTest extends DbIntegrationTestBase {
  @ParameterizedTest
  @CsvSource({"M, 0", "W, 20", "J, 50", "F, 100"})
  public void shouldFindCabinBonusById(CabinCode cabinCode, int expectedMultiplier) {
    // Given
    cabinBonusDao.save(getCabinBonus("World Traveller", CabinCode.M, 0));
    cabinBonusDao.save(getCabinBonus("World Traveller Plus", CabinCode.W, 20));
    cabinBonusDao.save(getCabinBonus("Club World", CabinCode.J, 50));
    cabinBonusDao.save(getCabinBonus("First", CabinCode.F, 100));

    // When
    final Optional<CabinBonus> actual = cabinBonusDao.findById(cabinCode);

    // Then
    assertTrue(actual.isPresent());
    assertEquals(expectedMultiplier, actual.get().getBonus());
  }

  private CabinBonus getCabinBonus(String cabinName, CabinCode cabinCode, int multiplier) {
    final CabinBonus cabinBonus = new CabinBonus();
    cabinBonus.setCabinName(cabinName);
    cabinBonus.setCabinCode(cabinCode);
    cabinBonus.setBonus(multiplier);
    return cabinBonus;
  }
}
