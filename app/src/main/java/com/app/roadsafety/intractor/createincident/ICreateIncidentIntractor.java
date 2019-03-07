package com.app.roadsafety.intractor.createincident;

import android.content.Context;

import com.app.roadsafety.model.authentication.FacebookLoginRequest;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.cityhall.CityHallResponse;
import com.app.roadsafety.model.createIncident.CreateIncidentRequest;
import com.app.roadsafety.model.createIncident.CreateIncidentResponse;

public interface ICreateIncidentIntractor {
    interface OnFinishedListener {

        void onSuccessCreateIncidentResponse(CreateIncidentResponse response);

        void onSuccessCityHallResponse(CityHallResponse response);

        void onError(String response);

        Context getAPPContext();
    }

    public void getCityHall(OnFinishedListener listener);

    public void createIncident(String auth_token, CreateIncidentRequest createIncidentRequest, OnFinishedListener listener);


}
