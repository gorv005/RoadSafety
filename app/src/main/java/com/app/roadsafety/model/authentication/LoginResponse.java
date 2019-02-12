package com.app.roadsafety.model.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("data")
    @Expose
    private FacebookLoginResponseData data;

    public FacebookLoginResponseData getData() {
        return data;
    }

    public void setData(FacebookLoginResponseData data) {
        this.data = data;
    }
}
