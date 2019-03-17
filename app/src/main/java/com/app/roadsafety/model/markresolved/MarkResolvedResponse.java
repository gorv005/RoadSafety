package com.app.roadsafety.model.markresolved;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarkResolvedResponse {
    @SerializedName("data")
    @Expose
    private MarkResolveData data;
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;

    public MarkResolveData getData() {
        return data;
    }

    public void setData(MarkResolveData data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
