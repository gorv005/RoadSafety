package com.app.roadsafety.model.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {
    @SerializedName("data")
    @Expose
    private FacebookLoginResponseData data;
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;
    public FacebookLoginResponseData getData() {
        return data;
    }

    public void setData(FacebookLoginResponseData data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
