package com.app.roadsafety.intractor.createincident;

import android.content.Context;

import com.app.roadsafety.model.cityhall.CityHallResponse;
import com.app.roadsafety.model.createIncident.CreateIncidentRequest;
import com.app.roadsafety.model.createIncident.CreateIncidentResponse;
import com.app.roadsafety.model.createIncident.ReportAbuseIncidentRequest;
import com.app.roadsafety.model.createIncident.ReportAbuseIncidentResponse;

public interface ICreateIncidentIntractor {
    interface OnFinishedListener {

        void onSuccessCreateIncidentResponse(CreateIncidentResponse response);

        void onSuccessUpdateIncidentResponse(CreateIncidentResponse response);

        void onSuccessDeleteIncidentResponse(CreateIncidentResponse response);

        void onSuccessReportAbuseIncidentResponse(ReportAbuseIncidentResponse response);

        void onSuccessCityHallResponse(CityHallResponse response);

        void onError(String response);

        Context getAPPContext();
    }

    public void getCityHall(OnFinishedListener listener);

    public void createIncident(String auth_token, CreateIncidentRequest createIncidentRequest, OnFinishedListener listener);

    public void updateIncident(String auth_token,String id, CreateIncidentRequest createIncidentRequest, OnFinishedListener listener);

    public void deleteIncident(String auth_token,String id, OnFinishedListener listener);

    public void reportAbuseIncident(String auth_token, String id, ReportAbuseIncidentRequest reportAbuseIncidentRequest, OnFinishedListener listener);

}
