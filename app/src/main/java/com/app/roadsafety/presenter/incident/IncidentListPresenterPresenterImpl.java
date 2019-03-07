package com.app.roadsafety.presenter.incident;

import android.content.Context;

import com.app.roadsafety.intractor.incident.CreateIncidentListImpl;
import com.app.roadsafety.intractor.incident.IIncidentListIntractor;
import com.app.roadsafety.model.incidents.IncidentDetailResponse;
import com.app.roadsafety.model.incidents.IncidentResponse;

public class IncidentListPresenterPresenterImpl implements IIncidentListPresenter, IIncidentListIntractor.OnFinishedListener {


    IIncidentView mView;
    Context context;
    IIncidentListIntractor iIncidentListIntractor;

    public IncidentListPresenterPresenterImpl(IIncidentView mView, Context context) {
        this.mView = mView;
        this.context=context;
        iIncidentListIntractor=new CreateIncidentListImpl();

    }

    @Override
    public void onSuccessIncidentListResponse(IncidentResponse response) {
        if(mView!=null){
            mView.hideProgress();
            mView.onSuccessIncidentListResponse(response);
        }
    }

    @Override
    public void onSuccessIncidentDetailsResponse(IncidentDetailResponse response) {
        if(mView!=null){
            mView.hideProgress();
            mView.onSuccessIncidentDetailsResponse(response);
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
    public void getAllIncidents(String auth_token, String lat, String longi, String distance, String page) {
        if(mView!=null) {
            mView.showProgress();
            iIncidentListIntractor.getAllIncidents(auth_token,lat,longi,distance,page, this);
        }
    }

    @Override
    public void getIncidentDetails(String auth_token, String id) {
        if(mView!=null) {
            mView.showProgress();
            iIncidentListIntractor.getIncidentDetails(auth_token,id, this);
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
