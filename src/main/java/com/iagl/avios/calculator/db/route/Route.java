package com.iagl.avios.calculator.db.route;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "route")
@Getter
@Setter
public class Route {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "route_id")
  private int routeId;

  @Column(name = "arrival_code_departure")
  private String airportCodeDeparture;

  @Column(name = "arrival_code_arrival")
  private String airportCodeArrival;

  @Column(name = "avios_earned")
  private Integer aviosEarned;
}
