package com.harryWorld.navigationGPS.map.utils.direction;

import java.util.List;

public class Steps {
    private Double duration;
    private Double distance;
    private String instruction;
    private String name;
    private List<Integer> way_points;

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getWay_points() {
        return way_points;
    }

    public void setWay_points(List<Integer> way_points) {
        this.way_points = way_points;
    }
}
