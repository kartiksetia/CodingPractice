package com.accenture.kartik.accenturetask.usecase;

import rx.Observable;


public abstract class UseCase<T> {

    public abstract Observable<T> buildObservable();

    public Observable<T> execute() {
        return buildObservable();
    }
}
