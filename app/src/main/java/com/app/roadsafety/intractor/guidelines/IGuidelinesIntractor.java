package com.app.roadsafety.intractor.guidelines;

import android.content.Context;

import com.app.roadsafety.model.authentication.FacebookLoginRequest;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.guidelines.GuidelinesResponse;

public interface IGuidelinesIntractor {
    interface OnFinishedListener {

        void onSuccessGuidelinesResponse(GuidelinesResponse response);


        void onError(String response);

        Context getAPPContext();
    }

    public void getGuidelines(String pageNo, OnFinishedListener listener);

}
