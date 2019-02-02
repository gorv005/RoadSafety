package com.app.roadsafety.model.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Feed implements Serializable {
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("desc")
    @Expose
    private String desc;

    public Feed(String image, String heading, String desc) {
        this.image = image;
        this.heading = heading;
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
