package com.su.iot.crowdsensing.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlatformLocation implements Serializable {

  private final Platform platform;
  private final Map<String, Sensor> sensorsById;
  private final List<Sensor> sensorsInOrder;
  private double relativePosition;

  public PlatformLocation(Platform platform) {
    this.platform = platform;

    this.sensorsById = new HashMap<>();
    this.sensorsInOrder = new ArrayList<>(platform.getSensors().size());

    platform.getSensors().forEach(sensor -> {
      sensorsById.put(sensor.getSensorId(), sensor);
      sensorsInOrder.add(sensor);
    });

    sensorsInOrder.sort(
        (first, second) -> (int) (first.getPosition() * 100 - second.getPosition() * 100));
  }
}
