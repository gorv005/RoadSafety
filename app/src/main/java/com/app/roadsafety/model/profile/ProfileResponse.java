package com.app.roadsafety.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileResponse {
    @SerializedName("data")
    @Expose
    private ProfileResponseData data;
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;

    public ProfileResponseData getData() {
        return data;
    }

    public void setData(ProfileResponseData data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
