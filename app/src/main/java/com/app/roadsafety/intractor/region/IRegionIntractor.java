package com.app.roadsafety.intractor.region;

import android.content.Context;

import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.guidelines.GuidelinesResponse;
import com.app.roadsafety.model.region.RegionUpdateRequest;

public interface IRegionIntractor {
    interface OnFinishedListener {

        void onSuccessRegionResponse(LoginResponse response);


        void onError(String response);

        Context getAPPContext();
    }

    public void setRegion(String auth_token, RegionUpdateRequest regionUpdateRequest, OnFinishedListener listener);

}
