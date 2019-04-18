package com.app.roadsafety.model.createIncident;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadPicResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("public_url")
    @Expose
    private String publicUrl;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }
}
