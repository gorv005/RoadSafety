package com.app.roadsafety.model.terms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TermsData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("attributes")
    @Expose
    private TermsAttribute attributes;

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

    public TermsAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(TermsAttribute attributes) {
        this.attributes = attributes;
    }
}
