package com.app.roadsafety.presenter.createIncident;

import com.app.roadsafety.frameworks.basemvp.IBaseView;
import com.app.roadsafety.model.cityhall.CityHallResponse;
import com.app.roadsafety.model.createIncident.CreateIncidentRequest;
import com.app.roadsafety.model.createIncident.CreateIncidentResponse;
import com.app.roadsafety.model.createIncident.ReportAbuseIncidentRequest;
import com.app.roadsafety.model.createIncident.ReportAbuseIncidentResponse;

import okhttp3.ResponseBody;

public interface ICreateIncidentPresenter {

    public void getCityHall(String auth_token,String page);

    public void createIncident(String auth_token, CreateIncidentRequest createIncidentRequest);
    public void updateIncident(String auth_token,String id, CreateIncidentRequest createIncidentRequest);
    public void reportAbuseIncident(String auth_token, String id, ReportAbuseIncidentRequest reportAbuseIncidentRequest);
    public void deleteIncident(String auth_token,String id);


    void onDestroy();
    interface ICreateIncidentView extends IBaseView {

        void onSuccessCreateIncidentResponse(CreateIncidentResponse response);

        void onSuccessCityHallResponse(CityHallResponse response);

        public void getResponseError(String response);

        void onSuccessUpdateIncidentResponse(CreateIncidentResponse response);

        void onSuccessReportAbuseIncidentResponse(ReportAbuseIncidentResponse response);

        void onSuccessDeleteIncidentResponse(ResponseBody response);

    }
}
