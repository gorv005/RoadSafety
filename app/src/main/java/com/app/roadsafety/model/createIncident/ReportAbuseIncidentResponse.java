package com.app.roadsafety.model.createIncident;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportAbuseIncidentResponse {
    @SerializedName("data")
    @Expose
    private ReportAbuseData data;
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;

    public ReportAbuseData getData() {
        return data;
    }

    public void setData(ReportAbuseData data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
