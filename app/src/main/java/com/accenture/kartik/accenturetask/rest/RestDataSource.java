package com.accenture.kartik.accenturetask.rest;

import com.accenture.kartik.accenturetask.domain.Album;

import java.util.List;
import rx.Observable;


public class RestDataSource implements AccentureRepository{

    /**
     * The Accenture api.
     */
    private final AccentureApi mAccentureApi;


    public RestDataSource(AccentureApi accentureApi) {

        mAccentureApi = accentureApi;
    }

    @Override
    public Observable<List<Album>> getAlbums() {

        return mAccentureApi.getAlbums();
    }

}