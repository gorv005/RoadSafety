package com.app.roadsafety.frameworks.basemvp;

import android.content.Intent;


public abstract class AppBasePresenter<T extends IView> implements IPresenter<T> {
    T view;
    public void attachView(T view) {
        this.view = view;
    }

    public T getView() {
        return  view;
    }
    public void onViewStarted() {

    }

    @Override
    public void onViewCreated() {

    }

    public void detachView() {

    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }

}
