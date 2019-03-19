package com.app.roadsafety.intractor.notification;

import android.content.Context;

import com.app.roadsafety.model.notification.NotificationResponse;

public interface INotificationIntractor {
    interface OnFinishedListener {

        void onSuccessNotificationResponse(NotificationResponse response);

        void onSuccessReadNotificationResponse(NotificationResponse response);

        void onError(String response);

        Context getAPPContext();
    }


    public void getNotification(String auth_token, String page, OnFinishedListener listener);

    public void readNotification(String auth_token, String id, OnFinishedListener listener);

}
