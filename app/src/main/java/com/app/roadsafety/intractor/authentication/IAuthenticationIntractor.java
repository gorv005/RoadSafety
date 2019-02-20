package com.app.roadsafety.intractor.authentication;

import android.content.Context;

import com.app.roadsafety.model.authentication.FacebookLoginRequest;
import com.app.roadsafety.model.authentication.LoginResponse;

public interface IAuthenticationIntractor {
    interface OnFinishedListener {

        void onSuccessFacebookResponse(LoginResponse loginResponse);

        void onSuccessGuestUserResponse(LoginResponse loginResponse);

        void onError(String response);

        Context getAPPContext();
    }

    public void facebookLogin(FacebookLoginRequest loginRequest, OnFinishedListener listener);

    public void guestLogin( OnFinishedListener listener);

}
