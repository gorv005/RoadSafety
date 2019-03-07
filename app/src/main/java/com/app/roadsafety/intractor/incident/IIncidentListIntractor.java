package com.app.roadsafety.intractor.incident;

import android.content.Context;

import com.app.roadsafety.model.incidents.IncidentDetailResponse;
import com.app.roadsafety.model.incidents.IncidentResponse;

public interface IIncidentListIntractor {
    interface OnFinishedListener {

        void onSuccessIncidentListResponse(IncidentResponse response);

        void onSuccessIncidentDetailsResponse(IncidentDetailResponse response);

        void onError(String response);

        Context getAPPContext();
    }


    public void getAllIncidents(String auth_token,String lat,String longi,String distance, String page, OnFinishedListener listener);

    public void getIncidentDetails(String auth_token,String id, OnFinishedListener listener);


}
