package com.app.roadsafety.model.incidents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncidentResponse {
    @SerializedName("data")
    @Expose
    private IncidentData data;
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;

    public IncidentData getData() {
        return data;
    }

    public void setData(IncidentData data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
