package com.app.roadsafety.intractor.guidelines;

import com.app.roadsafety.frameworks.retrofit.ResponseResolver;
import com.app.roadsafety.frameworks.retrofit.RestError;
import com.app.roadsafety.frameworks.retrofit.WebServicesWrapper;
import com.app.roadsafety.intractor.authentication.IAuthenticationIntractor;
import com.app.roadsafety.model.authentication.FacebookLoginRequest;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.guidelines.GuidelinesResponse;
import com.google.gson.Gson;

import retrofit2.Response;

public class GuidelinesIntractorImpl implements IGuidelinesIntractor {

    @Override
    public void getGuidelines(String pageNo, final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().getGuidelines(new ResponseResolver<GuidelinesResponse>() {
                @Override
                public void onSuccess(GuidelinesResponse loginResponse, Response response) {
                    listener.onSuccessGuidelinesResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            GuidelinesResponse response = gson.fromJson(msg, GuidelinesResponse.class);
                            listener.onSuccessGuidelinesResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                            listener.onError("");
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, pageNo);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onError("");
        }
    }
}
