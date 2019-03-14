package com.app.roadsafety.model.incidents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncidentLinks {
    @SerializedName("admin_resolved_images")
    @Expose
    private List<String> adminResolvedImages = null;

    public List<String> getAdminResolvedImages() {
        return adminResolvedImages;
    }

    public void setAdminResolvedImages(List<String> adminResolvedImages) {
        this.adminResolvedImages = adminResolvedImages;
    }

}
