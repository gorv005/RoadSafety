package com.app.roadsafety.model.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedListData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("attributes")
    @Expose
    private FeedAttribute attributes;

    /**
     * No args constructor for use in serialization
     *
     */
    public FeedListData() {
    }

    /**
     *
     * @param id
     * @param attributes
     * @param type
     */
    public FeedListData(String id, String type, FeedAttribute attributes) {
        super();
        this.id = id;
        this.type = type;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FeedAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(FeedAttribute attributes) {
        this.attributes = attributes;
    }
}
