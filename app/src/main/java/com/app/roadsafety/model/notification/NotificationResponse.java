package com.app.roadsafety.model.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationResponse {

    @SerializedName("data")
    @Expose
    private NotificationDataResponse data;
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;
    public NotificationDataResponse getData() {
        return data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setData(NotificationDataResponse data) {
        this.data = data;
    }
}
