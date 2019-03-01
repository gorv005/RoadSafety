package com.app.roadsafety.intractor.feed;

import com.app.roadsafety.frameworks.retrofit.ResponseResolver;
import com.app.roadsafety.frameworks.retrofit.RestError;
import com.app.roadsafety.frameworks.retrofit.WebServicesWrapper;
import com.app.roadsafety.intractor.guidelines.IGuidelinesIntractor;
import com.app.roadsafety.model.feed.FeedResponse;
import com.app.roadsafety.model.guidelines.GuidelinesResponse;
import com.google.gson.Gson;

import retrofit2.Response;

public class FeedListIntractorImpl implements IFeedListIntractor {

    @Override
    public void getFeedList(String auth_token,String pageNo, String country, final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().getFeedList(new ResponseResolver<FeedResponse>() {
                @Override
                public void onSuccess(FeedResponse loginResponse, Response response) {
                    listener.onSuccessFeedListResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            FeedResponse response = gson.fromJson(msg, FeedResponse.class);
                            listener.onSuccessFeedListResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, auth_token,pageNo,country);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
