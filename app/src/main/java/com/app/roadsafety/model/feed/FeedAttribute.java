package com.app.roadsafety.model.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedAttribute {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("published_at")
    @Expose
    private String publishedAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public FeedAttribute() {
    }

    /**
     *
     * @param publishedAt
     * @param title
     * @param imageUrl
     * @param description
     * @param url
     */
    public FeedAttribute(String title, String description, String url, String imageUrl, String publishedAt) {
        super();
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
