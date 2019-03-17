package com.app.roadsafety.intractor.markresolved;

import android.content.Context;

import com.app.roadsafety.model.markresolved.MarkResolvedRequest;
import com.app.roadsafety.model.markresolved.MarkResolvedResponse;

public interface IMarkResolvedIntractor {
    interface OnFinishedListener {

        void onSuccessMarkResolved(MarkResolvedResponse response);

        void onError(String response);

        Context getAPPContext();
    }


    public void markResolved(String auth_token, String id, MarkResolvedRequest markResolvedRequest,  OnFinishedListener listener);


}
