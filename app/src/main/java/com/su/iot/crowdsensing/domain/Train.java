package com.su.iot.crowdsensing.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Train {

    private final int id;
    private final List<TrainCoach> trainCoaches;

    public Train(int id) {
        this.id = id;
        this.trainCoaches = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public void addTrainCoach(TrainCoach trainCoach) {
        if ( trainCoaches.contains(trainCoach) ) {
            return;
        }
        trainCoaches.add(trainCoach);
        trainCoaches.sort(TrainCoach::compareTo);
    }

    public void updateCrowdedValue(int index, double crowdedValue) {
        trainCoaches.get(index).setCrowdedValue(crowdedValue);
    }

    public void removeTrainCoach(TrainCoach trainCoach) {
        trainCoaches.remove(trainCoach);
        trainCoaches.sort(TrainCoach::compareTo);
    }

    public boolean containsTrainCoach(TrainCoach trainCoach) {
        return trainCoaches.contains(trainCoach);
    }

    public List<TrainCoach> getTrainCoaches() {
        return Collections.unmodifiableList(trainCoaches);
    }

}
