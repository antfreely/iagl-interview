package com.iagl.avios.calculator.db.cabin.bonus;

import com.iagl.avios.calculator.calculator.CabinCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CabinBonusDao extends JpaRepository<CabinBonus, CabinCode> {
}
