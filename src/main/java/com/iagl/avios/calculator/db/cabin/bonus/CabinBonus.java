package com.iagl.avios.calculator.db.cabin.bonus;

import com.iagl.avios.calculator.calculator.CabinCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cabin_bonus")
@Getter
@Setter
public class CabinBonus {
  @Id
  @Column(name = "cabin_code", unique = true)
  @Enumerated(EnumType.STRING)
  private CabinCode cabinCode;

  @Column(name = "cabin_name")
  private String cabinName;

  @Column(name = "bonus")
  private Integer bonus;
}
