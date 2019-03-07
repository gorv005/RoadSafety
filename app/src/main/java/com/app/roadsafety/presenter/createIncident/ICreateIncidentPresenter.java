package com.app.roadsafety.presenter.createIncident;

import com.app.roadsafety.frameworks.basemvp.IBaseView;
import com.app.roadsafety.model.authentication.FacebookLoginRequest;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.cityhall.CityHallResponse;
import com.app.roadsafety.model.createIncident.CreateIncidentRequest;
import com.app.roadsafety.model.createIncident.CreateIncidentResponse;

public interface ICreateIncidentPresenter {

    public void getCityHall();

    public void createIncident(String auth_token, CreateIncidentRequest createIncidentRequest);


    void onDestroy();
    interface ICreateIncidentView extends IBaseView {

        void onSuccessCreateIncidentResponse(CreateIncidentResponse response);

        void onSuccessCityHallResponse(CityHallResponse response);

        public void getResponseError(String response);


    }
}
