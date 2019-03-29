package com.app.roadsafety.presenter.terms;

import com.app.roadsafety.frameworks.basemvp.IBaseView;
import com.app.roadsafety.model.terms.TermsResponse;

public interface ITermsPresenter {


    public void termsAndServices(String auth_token, String name);

    void onDestroy();
    interface ITermsView extends IBaseView {

        void onSuccessTermsResponse(TermsResponse response);

        public void getResponseError(String response);


    }
}
