package com.harryWorld.weatherGPS.map.retrofit.pictures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results {
    @SerializedName("urls")
    @Expose
    private Link link;

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}
