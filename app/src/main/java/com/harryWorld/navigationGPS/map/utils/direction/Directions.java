package com.harryWorld.navigationGPS.map.utils.direction;

import java.util.List;

public class Directions {
    private List<Features> features;
    private String errorReturn;
    private int errorValue;

    public List<Features> getFeatures() {
        return features;
    }

    public void setFeatures(List<Features> features) {
        this.features = features;
    }

    public String getErrorReturn() {
        return errorReturn;
    }

    public void setErrorReturn(String errorReturn) {
        this.errorReturn = errorReturn;
    }

    public int getErrorValue() {
        return errorValue;
    }

    public void setErrorValue(int errorValue) {
        this.errorValue = errorValue;
    }
}
