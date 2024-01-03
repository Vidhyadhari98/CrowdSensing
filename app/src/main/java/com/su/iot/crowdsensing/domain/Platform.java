package com.su.iot.crowdsensing.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a platform in a train station with attributes name, length and collection of sensors.
 */
public class Platform implements Serializable {

  private final String name;
  private final double length;
  private final Set<Sensor> sensors = new HashSet<>();

  public Platform(String name, double length) {
    this.name = name;
    this.length = length;
  }

  public void addSensor(Sensor sensor) {
    sensors.add(sensor);
  }

  public Set<Sensor> getSensors() {
    return Collections.unmodifiableSet(sensors);
  }

  public String getName() {
    return name;
  }

  public double getLength() {
    return length;
  }
}