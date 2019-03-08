package com.app.roadsafety.intractor.profile;

import com.app.roadsafety.frameworks.retrofit.ResponseResolver;
import com.app.roadsafety.frameworks.retrofit.RestError;
import com.app.roadsafety.frameworks.retrofit.WebServicesWrapper;
import com.app.roadsafety.model.profile.ProfileResponse;
import com.google.gson.Gson;

import retrofit2.Response;

public class ProfileIntractorImpl implements IProfileIntractor {

    @Override
    public void getProfile(String auth_token, String id, final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().getProfile(new ResponseResolver<ProfileResponse>() {
                @Override
                public void onSuccess(ProfileResponse loginResponse, Response response) {
                    listener.onSuccessProfileResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            ProfileResponse response = gson.fromJson(msg, ProfileResponse.class);
                            listener.onSuccessProfileResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, auth_token,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
