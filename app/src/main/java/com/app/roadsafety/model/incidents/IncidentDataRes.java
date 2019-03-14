package com.app.roadsafety.model.incidents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IncidentDataRes {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("attributes")
    @Expose
    private IncidentAttribute attributes;

    @SerializedName("links")
    @Expose
    private IncidentLinks links;
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

    public IncidentAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(IncidentAttribute attributes) {
        this.attributes = attributes;
    }

    public IncidentLinks getLinks() {
        return links;
    }

    public void setLinks(IncidentLinks links) {
        this.links = links;
    }
}
