package com.app.roadsafety.model.createIncident;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateIncidentData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("attributes")
    @Expose
    private CreateIncidentAttribute attributes;

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

    public CreateIncidentAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(CreateIncidentAttribute attributes) {
        this.attributes = attributes;
    }

}
