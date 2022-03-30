package com.harryWorld.navigationGPS.map.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("countrySubdivision")
    @Expose
    String region;
    @SerializedName("municipality")
    @Expose
    String county;
    @SerializedName("country")
    @Expose
    String country;
    @SerializedName("countryCodeISO3")
    @Expose
    String country_code;
    @SerializedName("freeformAddress")
    @Expose
    String label;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
