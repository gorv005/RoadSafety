package com.app.roadsafety.presenter.guidelines;

import android.content.Context;

import com.app.roadsafety.intractor.authentication.AuthenticationIntractorImpl;
import com.app.roadsafety.intractor.authentication.IAuthenticationIntractor;
import com.app.roadsafety.intractor.guidelines.GuidelinesIntractorImpl;
import com.app.roadsafety.intractor.guidelines.IGuidelinesIntractor;
import com.app.roadsafety.model.authentication.FacebookLoginRequest;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.guidelines.GuidelinesResponse;
import com.app.roadsafety.presenter.authentication.IAuthenticationPresenter;

public class GuidelinesPresenterImpl implements IGuidelinesPresenter, IGuidelinesIntractor.OnFinishedListener {


    IGuidelinesView mView;
    Context context;
    IGuidelinesIntractor iGuidelinesIntractor;

    public GuidelinesPresenterImpl(IGuidelinesView mView, Context context) {
        this.mView = mView;
        this.context=context;
        iGuidelinesIntractor=new GuidelinesIntractorImpl();

    }

    @Override
    public void onSuccessGuidelinesResponse(GuidelinesResponse response) {
        if(mView!=null){
            mView.hideProgress();
            mView.getGuidelinesResponse(response);
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
    public void getGuidelines(String page) {
        if(mView!=null) {
            mView.showProgress();
            iGuidelinesIntractor.getGuidelines(page, this);
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
