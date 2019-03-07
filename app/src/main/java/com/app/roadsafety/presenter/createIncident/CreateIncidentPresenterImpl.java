package com.app.roadsafety.presenter.createIncident;

import android.content.Context;

import com.app.roadsafety.intractor.authentication.AuthenticationIntractorImpl;
import com.app.roadsafety.intractor.authentication.IAuthenticationIntractor;
import com.app.roadsafety.intractor.createincident.CreateIncidentntractorImpl;
import com.app.roadsafety.intractor.createincident.ICreateIncidentIntractor;
import com.app.roadsafety.model.authentication.FacebookLoginRequest;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.cityhall.CityHallResponse;
import com.app.roadsafety.model.createIncident.CreateIncidentRequest;
import com.app.roadsafety.model.createIncident.CreateIncidentResponse;
import com.app.roadsafety.presenter.authentication.IAuthenticationPresenter;

public class CreateIncidentPresenterImpl implements ICreateIncidentPresenter, ICreateIncidentIntractor.OnFinishedListener {


    ICreateIncidentView mView;
    Context context;
    ICreateIncidentIntractor iCreateIncidentIntractor;

    public CreateIncidentPresenterImpl(ICreateIncidentView mView, Context context) {
        this.mView = mView;
        this.context=context;
        iCreateIncidentIntractor=new CreateIncidentntractorImpl();

    }


    @Override
    public void onSuccessCreateIncidentResponse(CreateIncidentResponse response) {
        if(mView!=null){
            mView.hideProgress();
            mView.onSuccessCreateIncidentResponse(response);
        }
    }

    @Override
    public void onSuccessCityHallResponse(CityHallResponse response) {
        if(mView!=null){
            mView.hideProgress();
            mView.onSuccessCityHallResponse(response);
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
    public void getCityHall() {
        if(mView!=null) {
            mView.showProgress();
            iCreateIncidentIntractor.getCityHall(this);
        }
    }

    @Override
    public void createIncident(String auth_token, CreateIncidentRequest createIncidentRequest) {
        if(mView!=null) {
            mView.showProgress();
            iCreateIncidentIntractor.createIncident(auth_token,createIncidentRequest,this);
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
