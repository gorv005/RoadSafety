package com.app.roadsafety.model.incidents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class IncidentDetailAttribute implements Serializable {
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("latitude")
    @Expose
    private Float latitude;
    @SerializedName("longitude")
    @Expose
    private Float longitude;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("resolved_images")
    @Expose
    private List<Object> resolvedImages = null;
    @SerializedName("is_resolved")
    @Expose
    private Boolean isResolved;
    @SerializedName("resolved_text")
    @Expose
    private Object resolvedText;
    @SerializedName("resolved_marked_by")
    @Expose
    private Object resolvedMarkedBy;
    @SerializedName("city_hall")
    @Expose
    private CityHall cityHall;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Object> getResolvedImages() {
        return resolvedImages;
    }

    public void setResolvedImages(List<Object> resolvedImages) {
        this.resolvedImages = resolvedImages;
    }

    public Boolean getResolved() {
        return isResolved;
    }

    public void setResolved(Boolean isResolved) {
        this.isResolved = isResolved;
    }

    public Object getResolvedText() {
        return resolvedText;
    }

    public void setResolvedText(Object resolvedText) {
        this.resolvedText = resolvedText;
    }

    public Object getResolvedMarkedBy() {
        return resolvedMarkedBy;
    }

    public void setResolvedMarkedBy(Object resolvedMarkedBy) {
        this.resolvedMarkedBy = resolvedMarkedBy;
    }

    public CityHall getCityHall() {
        return cityHall;
    }

    public void setCityHall(CityHall cityHall) {
        this.cityHall = cityHall;
    }
}
