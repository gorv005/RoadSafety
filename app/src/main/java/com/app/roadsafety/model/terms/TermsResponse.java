package com.app.roadsafety.model.terms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TermsResponse {
    @SerializedName("data")
    @Expose
    private TermsData data;

    public TermsData getData() {
        return data;
    }

    public void setData(TermsData data) {
        this.data = data;
    }

}
