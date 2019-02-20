package com.app.roadsafety.model.guidelines;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GuidelinesResponse implements Serializable {
    @SerializedName("data")
    @Expose
    private GuidelinesResponseData data;

    public GuidelinesResponseData getData() {
        return data;
    }

    public void setData(GuidelinesResponseData data) {
        this.data = data;
    }
}
