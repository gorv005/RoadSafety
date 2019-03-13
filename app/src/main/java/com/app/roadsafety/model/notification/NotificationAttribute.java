package com.app.roadsafety.model.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationAttribute {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("notified_by_user_name")
    @Expose
    private String notifiedByUserName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getNotifiedByUserName() {
        return notifiedByUserName;
    }

    public void setNotifiedByUserName(String notifiedByUserName) {
        this.notifiedByUserName = notifiedByUserName;
    }
}
