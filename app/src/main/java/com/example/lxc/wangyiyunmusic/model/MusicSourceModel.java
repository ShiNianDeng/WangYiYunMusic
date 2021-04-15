package com.example.lxc.wangyiyunmusic.model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class MusicSourceModel extends RealmObject {
    private RealmList<AlbumMusicModel> album;
    private RealmList<MusicModel> hot;

    public RealmList<AlbumMusicModel> getAlbum() {
        return album;
    }

    public void setAlbum(RealmList<AlbumMusicModel> album) {
        this.album = album;
    }

    public RealmList<MusicModel> getHot() {
        return hot;
    }

    public void setHot(RealmList<MusicModel> hot) {
        this.hot = hot;
    }
}
