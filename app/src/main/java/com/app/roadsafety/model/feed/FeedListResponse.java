package com.app.roadsafety.model.feed;

import com.app.roadsafety.model.guidelines.Meta;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedListResponse {
    @SerializedName("data")
    @Expose
    private List<FeedListData> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     * No args constructor for use in serialization
     *
     */
    public FeedListResponse() {
    }

    /**
     *
     * @param data
     * @param meta
     */
    public FeedListResponse(List<FeedListData> data, Meta meta) {
        super();
        this.data = data;
        this.meta = meta;
    }

    public List<FeedListData> getData() {
        return data;
    }

    public void setData(List<FeedListData> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
