package com.app.roadsafety.presenter.feed;

import com.app.roadsafety.frameworks.basemvp.IBaseView;
import com.app.roadsafety.model.feed.FeedResponse;
import com.app.roadsafety.model.guidelines.GuidelinesResponse;

public interface IFeedListPresenter {


    public void getFeedList(String pageNo,String country);

    void onDestroy();
    interface IFeedListView extends IBaseView {

        public void onSuccessFeedListResponse(FeedResponse response);


        public void getResponseError(String response);


    }
}
