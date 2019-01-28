package com.app.roadsafety.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Guidelines implements Serializable {
    @SerializedName("uptext")
    @Expose
    private String uptext;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("lowertexts")
    @Expose
    private String lowertexts;
    @SerializedName("colorCode")
    @Expose
    private String colorCode;
    public Guidelines(String uptext, String image, String lowertexts,String colorCode) {
        this.uptext = uptext;
        this.image = image;
        this.lowertexts = lowertexts;
        this.colorCode=colorCode;
    }

    public String getUptext() {
        return uptext;
    }

    public void setUptext(String uptext) {
        this.uptext = uptext;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLowertexts() {
        return lowertexts;
    }

    public void setLowertexts(String lowertexts) {
        this.lowertexts = lowertexts;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
