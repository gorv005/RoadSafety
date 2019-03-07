package com.app.roadsafety.model.createIncident;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportAbuseAttribute {
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("reportable_type")
    @Expose
    private String reportableType;
    @SerializedName("reportable_id")
    @Expose
    private Integer reportableId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReportableType() {
        return reportableType;
    }

    public void setReportableType(String reportableType) {
        this.reportableType = reportableType;
    }

    public Integer getReportableId() {
        return reportableId;
    }

    public void setReportableId(Integer reportableId) {
        this.reportableId = reportableId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
