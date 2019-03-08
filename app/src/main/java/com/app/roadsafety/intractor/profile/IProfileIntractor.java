package com.app.roadsafety.intractor.profile;

import android.content.Context;

import com.app.roadsafety.model.profile.ProfileResponse;

public interface IProfileIntractor {
    interface OnFinishedListener {

        void onSuccessProfileResponse(ProfileResponse response);

        void onError(String response);

        Context getAPPContext();
    }


    public void getProfile(String auth_token, String id, OnFinishedListener listener);


}
