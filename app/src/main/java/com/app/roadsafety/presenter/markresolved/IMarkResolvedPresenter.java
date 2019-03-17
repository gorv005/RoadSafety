package com.app.roadsafety.presenter.markresolved;

import com.app.roadsafety.frameworks.basemvp.IBaseView;
import com.app.roadsafety.model.markresolved.MarkResolvedRequest;
import com.app.roadsafety.model.markresolved.MarkResolvedResponse;

public interface IMarkResolvedPresenter {


    public void markResolved(String auth_token, String id, MarkResolvedRequest markResolvedRequest);

    void onDestroy();
    interface IMrkResolvedView extends IBaseView {

        void onSuccessMarkResolved(MarkResolvedResponse response);
        public void getResponseError(String response);


    }
}
