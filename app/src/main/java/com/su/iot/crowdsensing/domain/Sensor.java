package com.su.iot.crowdsensing.domain;

import java.io.Serializable;

/**
 * Represents a sensor with attributes sensorId, position and height.
 * The distance attribute is calculated by the calculateDistance method provided by sensor domain class.
 */
public class Sensor implements Serializable {

  private final String sensorId;
  private final double position;
  private final double height;
  private double distance;

  public Sensor(String sensorId, double position, double height) {
    this.sensorId = sensorId.toLowerCase();
    this.position = position;
    this.height = height;
    this.distance = Double.MAX_VALUE;
  }

  public String getSensorId() {
    return sensorId;
  }

  public double getPosition() {
    return position;
  }

  public double getHeight() {
    return height;
  }

  public double getDistance() {
    return distance;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Sensor) {
      Sensor sensor = (Sensor) other;
      return this.sensorId.equalsIgnoreCase(sensor.sensorId);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return sensorId.hashCode();
  }

  public void calculateDistance(double distance) {
    double calculatedDistance = Math.sqrt(
        Math.pow(distance, 2) - Math.pow(height, 2)
    );

    if (Double.isNaN(calculatedDistance)) {
      this.distance = 0.0;
    } else {
      this.distance = calculatedDistance;
    }
  }
}
