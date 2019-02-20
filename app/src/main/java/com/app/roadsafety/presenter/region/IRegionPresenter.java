package com.app.roadsafety.presenter.region;

import com.app.roadsafety.frameworks.basemvp.IBaseView;
import com.app.roadsafety.intractor.region.IRegionIntractor;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.guidelines.GuidelinesResponse;
import com.app.roadsafety.model.region.RegionUpdateRequest;

public interface IRegionPresenter {


    public void setRegion(String auth_token, RegionUpdateRequest regionUpdateRequest);

    void onDestroy();
    interface IRegionView extends IBaseView {

        void onSuccessRegionResponse(LoginResponse response);

        public void getResponseError(String response);


    }
}
