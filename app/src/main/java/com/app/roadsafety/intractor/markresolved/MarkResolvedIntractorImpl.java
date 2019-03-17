package com.app.roadsafety.intractor.markresolved;

import com.app.roadsafety.frameworks.retrofit.ResponseResolver;
import com.app.roadsafety.frameworks.retrofit.RestError;
import com.app.roadsafety.frameworks.retrofit.WebServicesWrapper;
import com.app.roadsafety.model.markresolved.MarkResolvedRequest;
import com.app.roadsafety.model.markresolved.MarkResolvedResponse;
import com.google.gson.Gson;

import retrofit2.Response;

public class MarkResolvedIntractorImpl implements IMarkResolvedIntractor {


    @Override
    public void markResolved(String auth_token, String id, MarkResolvedRequest markResolvedRequest,final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().markResolved(new ResponseResolver<MarkResolvedResponse>() {
                @Override
                public void onSuccess(MarkResolvedResponse loginResponse, Response response) {
                    listener.onSuccessMarkResolved(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            MarkResolvedResponse response = gson.fromJson(msg, MarkResolvedResponse.class);
                            listener.onSuccessMarkResolved(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, auth_token,id,markResolvedRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
