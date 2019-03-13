package com.app.roadsafety.model.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notifiable {
    @SerializedName("data")
    @Expose
    private NotifiableData data;

    public NotifiableData getData() {
        return data;
    }

    public void setData(NotifiableData data) {
        this.data = data;
    }
}
