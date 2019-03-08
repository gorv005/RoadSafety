package com.app.roadsafety.presenter.profile;

import com.app.roadsafety.frameworks.basemvp.IBaseView;
import com.app.roadsafety.model.profile.ProfileResponse;

public interface IProfilePresenter {


    public void getProfile(String auth_token, String id);

    void onDestroy();
    interface IProfileView extends IBaseView {

        public void onSuccessProfileResponse(ProfileResponse response);
        public void getResponseError(String response);


    }
}
