package com.su.iot.crowdsensing.domain;

import java.util.Objects;

public class TrainCoach implements Comparable<TrainCoach> {

    private final int id;
    private final int position;
    private double crowdedValue;
    private Train train;

    public TrainCoach(int id, int position) {
        this.id = id;
        this.position = position;
        this.crowdedValue = 0;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    @Override
    public boolean equals(Object o) {
        if ( o instanceof TrainCoach ) {
            TrainCoach other = (TrainCoach) o;
            return this.id == other.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Train getTrain() {
        return this.train;
    }

    public int getId() {
        return this.id;
    }

    public int getPosition() {
        return this.position;
    }

    public double getCrowdedValue() {
        return this.crowdedValue;
    }

    public void setCrowdedValue(double crowdedValue) {
        this.crowdedValue = crowdedValue;
    }


    @Override
    public int compareTo(TrainCoach other) {
        return this.position - other.position;
    }
}
