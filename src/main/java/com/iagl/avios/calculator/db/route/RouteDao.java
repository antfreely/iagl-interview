package com.iagl.avios.calculator.db.route;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteDao extends JpaRepository<Route, Integer> {
  @Query("SELECT r.aviosEarned FROM Route r WHERE r.airportCodeDeparture IN (:arrival, :departure) AND r.airportCodeArrival IN (:arrival, :departure)")
  List<Integer> findPointsForRoute(@Param("departure") String airportCodeDeparture, @Param("arrival") String airportCodeArrival);
}
