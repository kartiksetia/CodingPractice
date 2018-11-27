package com.accenture.kartik.accenturetask.rest;


import com.accenture.kartik.accenturetask.domain.Album;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import rx.Observable;

public interface AccentureApi {

    @Headers({
            "Content-Type: application/json"
    })
    @GET("/albums")
    Observable<List<Album>> getAlbums();
}

