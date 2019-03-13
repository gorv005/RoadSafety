package com.app.roadsafety.model.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Relationships {
    @SerializedName("notifiable")
    @Expose
    private Notifiable notifiable;

    public Notifiable getNotifiable() {
        return notifiable;
    }

    public void setNotifiable(Notifiable notifiable) {
        this.notifiable = notifiable;
    }
}
