package com.app.roadsafety.intractor.terms;

import android.content.Context;

import com.app.roadsafety.model.authentication.LoginResponse;
import com.app.roadsafety.model.region.RegionUpdateRequest;
import com.app.roadsafety.model.terms.TermsResponse;

public interface ITermsIntractor {
    interface OnFinishedListener {

        void onSuccessTermsResponse(TermsResponse response);


        void onError(String response);

        Context getAPPContext();
    }

    public void termsAndServices(String auth_token, String name, OnFinishedListener listener);

}
