package com.app.roadsafety.presenter.notification;

import android.content.Context;

import com.app.roadsafety.intractor.notification.INotificationIntractor;
import com.app.roadsafety.intractor.notification.NotificationIntractorImpl;
import com.app.roadsafety.model.notification.NotificationResponse;

public class NotificationPresenterImpl implements INotificationPresenter, INotificationIntractor.OnFinishedListener {


    INotificationView mView;
    Context context;
    INotificationIntractor iNotificationIntractor;

    public NotificationPresenterImpl(INotificationView mView, Context context) {
        this.mView = mView;
        this.context=context;
        iNotificationIntractor=new NotificationIntractorImpl();

    }







    @Override
    public void onSuccessNotificationResponse(NotificationResponse response) {
        if(mView!=null){
            mView.hideProgress();
            mView.onSuccessNotificationResponse(response);
        }

    }

    @Override
    public void onSuccessReadNotificationResponse(NotificationResponse response) {
        if(mView!=null){
           // mView.hideProgress();
            mView.onSuccessReadNotificationResponse(response);
        }
    }

    @Override
    public void onError(String response) {
        if(mView!=null){
            mView.hideProgress();
            mView.getResponseError(response);
        }
    }

    @Override
    public Context getAPPContext() {
        return context;
    }







    @Override
    public void getNotification(String auth_token, String page) {
        if(mView!=null) {
            mView.showProgress();
            iNotificationIntractor.getNotification(auth_token,page, this);
        }
    }

    @Override
    public void readNotification(String auth_token, String id) {
        if(mView!=null) {
           // mView.showProgress();
            iNotificationIntractor.readNotification(auth_token,id, this);
        }
    }

    @Override
    public void onDestroy() {
        try {
            mView = null;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
