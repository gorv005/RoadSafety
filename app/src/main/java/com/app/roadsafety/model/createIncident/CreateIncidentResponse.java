package com.app.roadsafety.model.createIncident;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateIncidentResponse {
    @SerializedName("data")
    @Expose
    private CreateIncidentData data;
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;

    public CreateIncidentData getData() {
        return data;
    }

    public void setData(CreateIncidentData data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
