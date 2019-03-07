package com.app.roadsafety.model.cityhall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityHallData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("attributes")
    @Expose
    private CityAttributes attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CityAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(CityAttributes attributes) {
        this.attributes = attributes;
    }
}
