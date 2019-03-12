package com.app.roadsafety.intractor.incident;

import com.app.roadsafety.frameworks.retrofit.ResponseResolver;
import com.app.roadsafety.frameworks.retrofit.RestError;
import com.app.roadsafety.frameworks.retrofit.WebServicesWrapper;
import com.app.roadsafety.model.incidents.IncidentDetailResponse;
import com.app.roadsafety.model.incidents.IncidentResponse;
import com.google.gson.Gson;

import retrofit2.Response;

public class CreateIncidentListImpl implements IIncidentListIntractor {

    @Override
    public void getAllIncidents(String auth_token, String lat, String longi, String distance, String incident_type ,String page, final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().getAllIncidents(new ResponseResolver<IncidentResponse>() {
                @Override
                public void onSuccess(IncidentResponse loginResponse, Response response) {
                    listener.onSuccessIncidentListResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            IncidentResponse response = gson.fromJson(msg, IncidentResponse.class);
                            listener.onSuccessIncidentListResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, auth_token,lat,longi,distance,incident_type,page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getIncidentDetails(String auth_token, String id, final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().getIncidentDetails(new ResponseResolver<IncidentDetailResponse>() {
                @Override
                public void onSuccess(IncidentDetailResponse loginResponse, Response response) {
                    listener.onSuccessIncidentDetailsResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            IncidentDetailResponse response = gson.fromJson(msg, IncidentDetailResponse.class);
                            listener.onSuccessIncidentDetailsResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, auth_token,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
