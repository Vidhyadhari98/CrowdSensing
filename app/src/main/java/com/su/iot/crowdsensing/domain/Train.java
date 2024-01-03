package com.su.iot.crowdsensing.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a train with attributes trainId and collection of train coaches sorted by their coach position.
 * Value of crowded-ness can be updated for a specific coach.
 */
public class Train implements Serializable {

  private final int trainId;
  private final List<TrainCoach> trainCoaches = new ArrayList<>();

  public Train(int trainId) {
    this.trainId = trainId;
  }

  public void addTrainCoach(TrainCoach trainCoach) {
    if (containsTrainCoach(trainCoach)) {
      return;
    }
    trainCoaches.add(trainCoach);
    trainCoaches.sort(TrainCoach::compareTo);
  }

  public void updateCrowdedValue(int index, double crowdedValue) {
    trainCoaches.get(index).setCrowdedValue(crowdedValue);
  }

  public boolean containsTrainCoach(TrainCoach trainCoach) {
    return trainCoaches.contains(trainCoach);
  }

  public List<TrainCoach> getTrainCoaches() {
    return Collections.unmodifiableList(trainCoaches);
  }

  public int getTrainId() {
    return trainId;
  }
}
