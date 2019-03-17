package com.app.roadsafety.presenter.markresolved;

import android.content.Context;

import com.app.roadsafety.intractor.markresolved.IMarkResolvedIntractor;
import com.app.roadsafety.intractor.markresolved.MarkResolvedIntractorImpl;
import com.app.roadsafety.model.markresolved.MarkResolvedRequest;
import com.app.roadsafety.model.markresolved.MarkResolvedResponse;

public class MarkResolvedPresenterImpl implements IMarkResolvedPresenter, IMarkResolvedIntractor.OnFinishedListener {


    IMrkResolvedView mView;
    Context context;
    IMarkResolvedIntractor iMarkResolvedIntractor;

    public MarkResolvedPresenterImpl(IMrkResolvedView mView, Context context) {
        this.mView = mView;
        this.context=context;
        iMarkResolvedIntractor=new MarkResolvedIntractorImpl();

    }







    @Override
    public void onSuccessMarkResolved(MarkResolvedResponse response) {
        if(mView!=null){
            mView.hideProgress();
            mView.onSuccessMarkResolved(response);
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
    public void markResolved(String auth_token, String id, MarkResolvedRequest markResolvedRequest) {
        if(mView!=null) {
            mView.showProgress();
            iMarkResolvedIntractor.markResolved(auth_token,id,markResolvedRequest, this);
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
