package com.app.roadsafety.model.markresolved;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarkResolvedRequest {
    @SerializedName("resolved_images")
    @Expose
    private List<String> resolvedImages = null;
    @SerializedName("resolved_text")
    @Expose
    private String resolvedText;

    public List<String> getResolvedImages() {
        return resolvedImages;
    }

    public void setResolvedImages(List<String> resolvedImages) {
        this.resolvedImages = resolvedImages;
    }

    public String getResolvedText() {
        return resolvedText;
    }

    public void setResolvedText(String resolvedText) {
        this.resolvedText = resolvedText;
    }
}
