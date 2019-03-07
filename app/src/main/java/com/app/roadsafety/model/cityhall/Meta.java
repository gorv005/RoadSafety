package com.app.roadsafety.model.cityhall;

import com.app.roadsafety.model.guidelines.Pagination;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public Object getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
