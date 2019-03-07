package com.app.roadsafety.presenter.incident;

import com.app.roadsafety.frameworks.basemvp.IBaseView;
import com.app.roadsafety.model.incidents.IncidentDetailResponse;
import com.app.roadsafety.model.incidents.IncidentResponse;

public interface IIncidentListPresenter {


    public void getAllIncidents(String auth_token,String lat,String longi,String distance, String page);

    public void getIncidentDetails(String auth_token,String id);

    void onDestroy();
    interface IIncidentView extends IBaseView {

        void onSuccessIncidentListResponse(IncidentResponse response);

        void onSuccessIncidentDetailsResponse(IncidentDetailResponse response);


        public void getResponseError(String response);


    }
}
