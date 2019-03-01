package com.app.roadsafety.model.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedResponse {
    @SerializedName("data")
    @Expose
    private FeedListResponse data;
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;
    /**
     * No args constructor for use in serialization
     *
     */
    public FeedResponse() {
    }

    /**
     *
     * @param data
     */
    public FeedResponse(FeedListResponse data) {
        super();
        this.data = data;
    }

    public FeedListResponse getData() {
        return data;
    }

    public void setData(FeedListResponse data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
