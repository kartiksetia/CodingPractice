package com.accenture.kartik.accenturetask.rest;

import com.accenture.kartik.accenturetask.domain.Album;

import java.util.List;

import rx.Observable;

public interface AccentureRepository {
    /**
     * Returns a list of album.
     *
     * @return A list of album.
     */
    Observable<List<Album>> getAlbums();
}
