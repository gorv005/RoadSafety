package com.app.roadsafety.model.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Feed implements Serializable {
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("longi")
    @Expose
    private double longi;
    public Feed(String image, String heading, String desc) {
        this.image = image;
        this.heading = heading;
        this.desc = desc;
    }
    public Feed(String image, String heading, String desc,double lat, double longi) {
        this.image = image;
        this.heading = heading;
        this.desc = desc;
        this.lat = lat;
        this.longi = longi;
    }
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
