package com.app.roadsafety.presenter.guidelines;

import com.app.roadsafety.frameworks.basemvp.IBaseView;
import com.app.roadsafety.model.authentication.FacebookLoginRequest;
import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.guidelines.GuidelinesResponse;

public interface IGuidelinesPresenter {


    public void getGuidelines(String page);

    void onDestroy();
    interface IGuidelinesView extends IBaseView {

        public void getGuidelinesResponse(GuidelinesResponse response);


        public void getResponseError(String response);


    }
}
