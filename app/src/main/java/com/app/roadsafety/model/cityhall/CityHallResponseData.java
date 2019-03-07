package com.app.roadsafety.model.cityhall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityHallResponseData {
    @SerializedName("data")
    @Expose
    private List<CityHallData> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<CityHallData> getData() {
        return data;
    }

    public void setData(List<CityHallData> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
