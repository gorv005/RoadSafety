package com.app.roadsafety.presenter.terms;

import android.content.Context;

import com.app.roadsafety.intractor.region.IRegionIntractor;
import com.app.roadsafety.intractor.region.RegionIntractorImpl;
import com.app.roadsafety.intractor.terms.ITermsIntractor;
import com.app.roadsafety.intractor.terms.TermsIntractorImpl;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.region.RegionUpdateRequest;
import com.app.roadsafety.model.terms.TermsResponse;
import com.app.roadsafety.presenter.region.IRegionPresenter;

public class TermsPresenterImpl implements ITermsPresenter, ITermsIntractor.OnFinishedListener {


    ITermsView mView;
    Context context;
    ITermsIntractor iTermsIntractor;

    public TermsPresenterImpl(ITermsView mView, Context context) {
        this.mView = mView;
        this.context=context;
        iTermsIntractor=new TermsIntractorImpl();

    }



    @Override
    public void onSuccessTermsResponse(TermsResponse response) {
        if(mView!=null){
            mView.hideProgress();
            mView.onSuccessTermsResponse(response);
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
    public void termsAndServices(String auth_token, String name) {
        if(mView!=null) {
            mView.showProgress();
            iTermsIntractor.termsAndServices(auth_token, name,this);
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
