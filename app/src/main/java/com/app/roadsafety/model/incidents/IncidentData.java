package com.app.roadsafety.model.incidents;

import com.app.roadsafety.model.guidelines.Meta;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncidentData {
    @SerializedName("data")
    @Expose
    private List<IncidentDataRes> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<IncidentDataRes> getData() {
        return data;
    }

    public void setData(List<IncidentDataRes> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
