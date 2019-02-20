package com.app.roadsafety.intractor.region;

import com.app.roadsafety.frameworks.retrofit.ResponseResolver;
import com.app.roadsafety.frameworks.retrofit.RestError;
import com.app.roadsafety.frameworks.retrofit.WebServicesWrapper;
import com.app.roadsafety.intractor.guidelines.IGuidelinesIntractor;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.guidelines.GuidelinesResponse;
import com.app.roadsafety.model.region.RegionUpdateRequest;
import com.google.gson.Gson;

import retrofit2.Response;

public class RegionIntractorImpl implements IRegionIntractor {


    @Override
    public void setRegion(String auth_token, RegionUpdateRequest regionUpdateRequest,final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().setRegion(new ResponseResolver<LoginResponse>() {
                @Override
                public void onSuccess(LoginResponse loginResponse, Response response) {
                    listener.onSuccessRegionResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            LoginResponse response = gson.fromJson(msg, LoginResponse.class);
                            listener.onSuccessRegionResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, auth_token,regionUpdateRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
