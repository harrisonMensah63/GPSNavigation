package com.harryWorld.weatherGPS.map.retrofit.pictures;

import com.google.gson.annotations.SerializedName;

public class Link {
    @SerializedName("thumb")
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
