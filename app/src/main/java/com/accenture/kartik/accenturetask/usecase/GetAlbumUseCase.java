package com.accenture.kartik.accenturetask.usecase;


import com.accenture.kartik.accenturetask.domain.Album;
import com.accenture.kartik.accenturetask.rest.AccentureRepository;

import java.util.List;

import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

public class GetAlbumUseCase extends UseCase<List<Album>> {

    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;
    private final AccentureRepository mRepository;

    public GetAlbumUseCase(AccentureRepository repository,
                                 @Named("ui_thread") Scheduler uiThread,
                                 @Named("executor_thread") Scheduler executorThread) {

        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    @Override
    public Observable<List<Album>> buildObservable() {
        return mRepository.getAlbums()
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
