package com.app.roadsafety.model.notification;

import com.app.roadsafety.model.guidelines.Meta;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationDataResponse {
    @SerializedName("data")
    @Expose
    private List<NotificationData> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<NotificationData> getData() {
        return data;
    }

    public void setData(List<NotificationData> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
