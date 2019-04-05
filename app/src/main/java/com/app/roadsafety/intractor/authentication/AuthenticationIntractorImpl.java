package com.app.roadsafety.intractor.authentication;

import com.app.roadsafety.frameworks.retrofit.ResponseResolver;
import com.app.roadsafety.frameworks.retrofit.RestError;
import com.app.roadsafety.frameworks.retrofit.WebServicesWrapper;
import com.app.roadsafety.model.authentication.FacebookLoginRequest;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.google.gson.Gson;

import retrofit2.Response;

public class AuthenticationIntractorImpl implements IAuthenticationIntractor {
    @Override
    public void facebookLogin(FacebookLoginRequest loginRequest,final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().facebookLogin(new ResponseResolver<LoginResponse>() {
                @Override
                public void onSuccess(LoginResponse loginResponse, Response response) {
                    listener.onSuccessFacebookResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            LoginResponse response = gson.fromJson(msg, LoginResponse.class);
                            listener.onSuccessFacebookResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                            listener.onError("");
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onError("");
        }

    }

    @Override
    public void connectGuestUserWithFacebook(String auth_token, FacebookLoginRequest loginRequest, final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().connectGuestUserWithFacebook(new ResponseResolver<LoginResponse>() {
                @Override
                public void onSuccess(LoginResponse loginResponse, Response response) {
                    listener.onSuccessFacebookResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            LoginResponse response = gson.fromJson(msg, LoginResponse.class);
                            listener.onSuccessFacebookResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                            listener.onError("");
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, auth_token,loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onError("");
        }

    }

    @Override
    public void guestLogin(final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().guestLogin(new ResponseResolver<LoginResponse>() {
                @Override
                public void onSuccess(LoginResponse loginResponse, Response response) {
                    listener.onSuccessGuestUserResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            LoginResponse response = gson.fromJson(msg, LoginResponse.class);
                            listener.onSuccessGuestUserResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                            listener.onError("");
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            listener.onError("");
        }

    }
}
