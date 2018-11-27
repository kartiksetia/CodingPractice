package com.accenture.kartik.accenturetask.mvp.model;

import com.accenture.kartik.accenturetask.domain.Album;

import java.util.ArrayList;
import java.util.List;

public class MainModel {
    private List<Album> mAlbums;

    public MainModel(){
        mAlbums = new ArrayList<>();
    }

    public List<Album> getAlbums() {
        return mAlbums;
    }

    public void setAlbums(List<Album> albums) {
        if (albums == null){
            return;
        }

        mAlbums.clear();
        for(Album album : albums){
            mAlbums.add(album);
        }
    }


}
