package com.app.roadsafety.presenter.region;

import android.content.Context;

import com.app.roadsafety.intractor.guidelines.GuidelinesIntractorImpl;
import com.app.roadsafety.intractor.guidelines.IGuidelinesIntractor;
import com.app.roadsafety.intractor.region.IRegionIntractor;
import com.app.roadsafety.intractor.region.RegionIntractorImpl;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.guidelines.GuidelinesResponse;
import com.app.roadsafety.model.region.RegionUpdateRequest;
import com.app.roadsafety.presenter.guidelines.IGuidelinesPresenter;

public class RegionPresenterImpl implements IRegionPresenter, IRegionIntractor.OnFinishedListener {


    IRegionView mView;
    Context context;
    IRegionIntractor iRegionIntractor;

    public RegionPresenterImpl(IRegionView mView, Context context) {
        this.mView = mView;
        this.context=context;
        iRegionIntractor=new RegionIntractorImpl();

    }


    @Override
    public void onSuccessRegionResponse(LoginResponse response) {
        if(mView!=null){
            mView.hideProgress();
            mView.onSuccessRegionResponse(response);
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
    public void setRegion(String auth_token, RegionUpdateRequest regionUpdateRequest) {
        if(mView!=null) {
            mView.showProgress();
            iRegionIntractor.setRegion(auth_token, regionUpdateRequest,this);
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
