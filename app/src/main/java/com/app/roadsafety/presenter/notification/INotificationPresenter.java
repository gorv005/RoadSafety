package com.app.roadsafety.presenter.notification;

import com.app.roadsafety.frameworks.basemvp.IBaseView;
import com.app.roadsafety.intractor.notification.INotificationIntractor;
import com.app.roadsafety.model.notification.NotificationResponse;

public interface INotificationPresenter {


    public void getNotification(String auth_token, String page);
    public void readNotification(String auth_token, String id);

    void onDestroy();
    interface INotificationView extends IBaseView {
        void onSuccessReadNotificationResponse(NotificationResponse response);


        void onSuccessNotificationResponse(NotificationResponse response);
        public void getResponseError(String response);


    }
}
