package com.accenture.kartik.accenturetask.mvp.presenter;

import android.support.annotation.NonNull;

public abstract class BasePresenter<M, V> {

    protected M mModel;
    private V mView;

    public void setModel(M model) {
        resetState();
        this.mModel = model;
        if (setupDone()) {
            updateView();
        }
    }

    public M getModel() {
        return mModel;
    }

    protected void resetState() {

    }

    public void bindView(@NonNull V view) {
        this.mView = view;
//        if (setupDone()) {
//            updateView();
//        }
    }

    public void unbindView() {
        this.mView = null;
    }

    protected V view() {
       return mView;
    }

    protected abstract void updateView();

    protected boolean setupDone() {
        return view() != null && mModel != null;
    }


    public abstract void onCreate();
    public abstract void onStart();
    public abstract void onResume();
    public abstract void onPause();
    public abstract void onStop();
    public abstract void onDestroy();

}