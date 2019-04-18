package com.app.roadsafety.presenter.createIncident;

import android.content.Context;

import com.app.roadsafety.intractor.createincident.CreateIncidentntractorImpl;
import com.app.roadsafety.intractor.createincident.ICreateIncidentIntractor;
import com.app.roadsafety.model.cityhall.CityHallResponse;
import com.app.roadsafety.model.createIncident.CreateIncidentRequest;
import com.app.roadsafety.model.createIncident.CreateIncidentResponse;
import com.app.roadsafety.model.createIncident.ReportAbuseIncidentRequest;
import com.app.roadsafety.model.createIncident.ReportAbuseIncidentResponse;
import com.app.roadsafety.model.createIncident.UploadPicResponse;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

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
    public void onSuccessUpdateIncidentResponse(CreateIncidentResponse response) {
        if(mView!=null){
            mView.hideProgress();
            mView.onSuccessUpdateIncidentResponse(response);
        }
    }

    @Override
    public void onSuccessDeleteIncidentResponse(ResponseBody response) {
        if(mView!=null){
            mView.hideProgress();
            mView.onSuccessDeleteIncidentResponse(response);
        }
    }

    @Override
    public void onSuccessReportAbuseIncidentResponse(ReportAbuseIncidentResponse response) {
        if(mView!=null){
            mView.hideProgress();
            mView.onSuccessReportAbuseIncidentResponse(response);
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
    public void onSuccessUploadPic(UploadPicResponse Response) {
        if(mView!=null){
            mView.hideProgress();
            mView.onSuccessUploadPic(Response);
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
    public void getCityHall(String auth_token,String page) {
        if(mView!=null) {
            mView.showProgress();
            iCreateIncidentIntractor.getCityHall(auth_token,page,this);
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
    public void updateIncident(String auth_token, String id, CreateIncidentRequest createIncidentRequest) {
        if(mView!=null) {
            mView.showProgress();
            iCreateIncidentIntractor.updateIncident(auth_token,id,createIncidentRequest,this);
        }
    }

    @Override
    public void reportAbuseIncident(String auth_token, String id, ReportAbuseIncidentRequest reportAbuseIncidentRequest) {
        if(mView!=null) {
            mView.showProgress();
            iCreateIncidentIntractor.reportAbuseIncident(auth_token,id,reportAbuseIncidentRequest,this);
        }
    }

    @Override
    public void deleteIncident(String auth_token, String id) {
        if(mView!=null) {
            mView.showProgress();
            iCreateIncidentIntractor.deleteIncident(auth_token,id,this);
        }
    }

    @Override
    public void uploadPic(String token, MultipartBody.Part part) {
        if(mView!=null) {
            mView.showProgress();
            iCreateIncidentIntractor.uploadPic(token,part,this);
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
