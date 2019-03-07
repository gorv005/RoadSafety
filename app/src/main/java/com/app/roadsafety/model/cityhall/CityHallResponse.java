package com.app.roadsafety.model.cityhall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityHallResponse {
    @SerializedName("data")
    @Expose
    private CityHallResponseData data;

    public CityHallResponseData getData() {
        return data;
    }

    public void setData(CityHallResponseData data) {
        this.data = data;
    }
}
