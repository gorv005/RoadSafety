package com.app.roadsafety.model.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedResponse {
    @SerializedName("data")
    @Expose
    private FeedListResponse data;

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
}
