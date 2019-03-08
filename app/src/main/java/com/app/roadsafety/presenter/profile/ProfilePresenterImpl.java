package com.app.roadsafety.presenter.profile;

import android.content.Context;

import com.app.roadsafety.intractor.profile.IProfileIntractor;
import com.app.roadsafety.intractor.profile.ProfileIntractorImpl;
import com.app.roadsafety.model.profile.ProfileResponse;

public class ProfilePresenterImpl implements IProfilePresenter, IProfileIntractor.OnFinishedListener {


    IProfileView mView;
    Context context;
    IProfileIntractor iProfileIntractor;

    public ProfilePresenterImpl(IProfileView mView, Context context) {
        this.mView = mView;
        this.context=context;
        iProfileIntractor=new ProfileIntractorImpl();

    }





    @Override
    public void onSuccessProfileResponse(ProfileResponse response) {
        if(mView!=null){
            mView.hideProgress();
            mView.onSuccessProfileResponse(response);
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
    public void getProfile(String auth_token, String id) {
        if(mView!=null) {
            mView.showProgress();
            iProfileIntractor.getProfile(auth_token,id, this);
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
