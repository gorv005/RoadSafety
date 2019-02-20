package com.app.roadsafety.presenter.authentication;

import android.content.Context;

import com.app.roadsafety.intractor.authentication.AuthenticationIntractorImpl;
import com.app.roadsafety.intractor.authentication.IAuthenticationIntractor;
import com.app.roadsafety.model.authentication.FacebookLoginRequest;
import com.app.roadsafety.model.authentication.LoginResponse;

public class AuthenticationPresenterImpl implements IAuthenticationPresenter, IAuthenticationIntractor.OnFinishedListener {


    IAuthenticationView mView;
    Context context;
    IAuthenticationIntractor iAuthnicationIntractor;

    public AuthenticationPresenterImpl(IAuthenticationView mView, Context context) {
        this.mView = mView;
        this.context=context;
        iAuthnicationIntractor=new AuthenticationIntractorImpl();

    }
    @Override
    public void onSuccessFacebookResponse(LoginResponse loginResponse) {
        if(mView!=null){
            mView.hideProgress();
            mView.getFacebookLoginResponse(loginResponse);
        }
    }

    @Override
    public void onSuccessGuestUserResponse(LoginResponse loginResponse) {
        if(mView!=null){
            mView.hideProgress();
            mView.getGuestUserResponse(loginResponse);
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
    public void guestLogin() {
        if(mView!=null) {
            mView.showProgress();
            iAuthnicationIntractor.guestLogin(this);
        }
    }

    @Override
    public void facebookLogin(FacebookLoginRequest facebookLoginRequest) {
        if(mView!=null) {
            mView.showProgress();
            iAuthnicationIntractor.facebookLogin(facebookLoginRequest, this);
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
