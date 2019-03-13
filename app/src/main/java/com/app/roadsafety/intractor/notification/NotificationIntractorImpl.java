package com.app.roadsafety.intractor.notification;

import com.app.roadsafety.frameworks.retrofit.ResponseResolver;
import com.app.roadsafety.frameworks.retrofit.RestError;
import com.app.roadsafety.frameworks.retrofit.WebServicesWrapper;
import com.app.roadsafety.model.notification.NotificationResponse;
import com.google.gson.Gson;

import retrofit2.Response;

public class NotificationIntractorImpl implements INotificationIntractor {


    @Override
    public void getNotification(String auth_token, String page, final OnFinishedListener listener) {
        try {
            WebServicesWrapper.getInstance().getNotification(new ResponseResolver<NotificationResponse>() {
                @Override
                public void onSuccess(NotificationResponse loginResponse, Response response) {
                    listener.onSuccessNotificationResponse(loginResponse);
                }

                @Override
                public void onFailure(RestError error, String msg) {
                    if (error == null || error.getError() == null) {
                        try {
                            Gson gson = new Gson();
                            NotificationResponse response = gson.fromJson(msg, NotificationResponse.class);
                            listener.onSuccessNotificationResponse(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onError(error.getError());
                    }
                }
            }, auth_token,page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
