package com.app.roadsafety.intractor.feed;

import android.content.Context;

import com.app.roadsafety.model.feed.FeedResponse;
import com.app.roadsafety.model.guidelines.GuidelinesResponse;

public interface IFeedListIntractor {
    interface OnFinishedListener {

        void onSuccessFeedListResponse(FeedResponse response);


        void onError(String response);

        Context getAPPContext();
    }

    public void getFeedList(String auth_token,String pageNo,String country, OnFinishedListener listener);

}
