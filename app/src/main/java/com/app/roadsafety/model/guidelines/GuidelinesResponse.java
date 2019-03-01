package com.app.roadsafety.model.guidelines;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GuidelinesResponse implements Serializable {
    @SerializedName("data")
    @Expose
    private GuidelinesResponseData data;
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;
    public GuidelinesResponseData getData() {
        return data;
    }

    public void setData(GuidelinesResponseData data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
