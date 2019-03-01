package com.app.roadsafety.presenter.feed;

import android.content.Context;

import com.app.roadsafety.intractor.feed.FeedListIntractorImpl;
import com.app.roadsafety.intractor.feed.IFeedListIntractor;
import com.app.roadsafety.intractor.guidelines.GuidelinesIntractorImpl;
import com.app.roadsafety.intractor.guidelines.IGuidelinesIntractor;
import com.app.roadsafety.model.feed.FeedResponse;
import com.app.roadsafety.model.guidelines.GuidelinesResponse;
import com.app.roadsafety.presenter.guidelines.IGuidelinesPresenter;

public class FeedListPresenterImpl implements IFeedListPresenter, IFeedListIntractor.OnFinishedListener {


    IFeedListView mView;
    Context context;
    IFeedListIntractor iFeedListIntractor;

    public FeedListPresenterImpl(IFeedListView mView, Context context) {
        this.mView = mView;
        this.context=context;
        iFeedListIntractor=new FeedListIntractorImpl();

    }



    @Override
    public void onSuccessFeedListResponse(FeedResponse response) {
        if(mView!=null){
            mView.hideProgress();
            mView.onSuccessFeedListResponse(response);
        }
    }

    @Override
    public void onError(String response) {
        if(mView!=null){
            mView.hideProgress();
            mView.getResponseError(response);
        }
    }

    @Override
    public Context getAPPContext() {
        return context;
    }




    @Override
    public void getFeedList(String auth_token,String pageNo, String country) {
        if(mView!=null) {
            mView.showProgress();
            iFeedListIntractor.getFeedList(auth_token,pageNo,country, this);
        }
    }

    @Override
    public void onDestroy() {
        try {
            mView = null;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
