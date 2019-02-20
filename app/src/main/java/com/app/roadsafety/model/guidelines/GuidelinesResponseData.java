package com.app.roadsafety.model.guidelines;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GuidelinesResponseData implements Serializable {
    @SerializedName("data")
    @Expose
    private List<GuidelinesResponseDataList> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<GuidelinesResponseDataList> getData() {
        return data;
    }

    public void setData(List<GuidelinesResponseDataList> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
