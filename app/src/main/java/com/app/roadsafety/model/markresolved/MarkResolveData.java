package com.app.roadsafety.model.markresolved;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarkResolveData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("attributes")
    @Expose
    private MarkResolvedAttribute attributes;

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

    public MarkResolvedAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(MarkResolvedAttribute attributes) {
        this.attributes = attributes;
    }
}
