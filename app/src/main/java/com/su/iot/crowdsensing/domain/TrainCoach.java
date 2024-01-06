package com.su.iot.crowdsensing.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a train coach with attributes coachId and coachPosition.
 */
public class TrainCoach implements Comparable<TrainCoach>, Serializable {

  private final int coachId;
  private final int coachPosition;
  private double crowdedValue;

  public TrainCoach(int coachId, int coachPosition) {
    this.coachId = coachId;
    this.coachPosition = coachPosition;
    this.crowdedValue = 0;
  }

  public int getCoachId() {
    return coachId;
  }

  public int getCoachPosition() {
    return coachPosition;
  }

  public double getCrowdedValue() {
    return crowdedValue;
  }

  public void setCrowdedValue(double crowdedValue) {
    this.crowdedValue = crowdedValue;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof TrainCoach) {
      TrainCoach other = (TrainCoach) o;
      return this.coachId == other.coachId;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(coachId);
  }


  @Override
  public int compareTo(TrainCoach other) {
    return this.coachPosition - other.coachPosition;
  }
}
