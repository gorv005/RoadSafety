package com.app.roadsafety.intractor.terms;

import com.app.roadsafety.frameworks.retrofit.ResponseResolver;
import com.app.roadsafety.frameworks.retrofit.RestError;
import com.app.roadsafety.frameworks.retrofit.WebServicesWrapper;
import com.app.roadsafety.intractor.region.IRegionIntractor;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.region.RegionUpdateRequest;
import com.app.roadsafety.model.terms.TermsResponse;
import com.google.gson.Gson;

import retrofit2.Response;

public class TermsIntractorImpl implements ITermsIntractor {



    @Override
    public void termsAndServices(String auth_token, String name, final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().termsAndServices(new ResponseResolver<TermsResponse>() {
                @Override
                public void onSuccess(TermsResponse loginResponse, Response response) {
                    listener.onSuccessTermsResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            TermsResponse response = gson.fromJson(msg, TermsResponse.class);
                            listener.onSuccessTermsResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, auth_token,name);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
