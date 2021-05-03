package com.example.sciencephile.ui.videosPage.playlists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PlaylistViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> videoIDs;
    private MutableLiveData<ArrayList<String>> videoTitles;
    private MutableLiveData<ArrayList<ThumbnailItem>> previews;

    public PlaylistViewModel(){
        videoIDs = new MutableLiveData<>();
        videoTitles = new MutableLiveData<>();
        previews = new MutableLiveData<>();
    }

    public LiveData<ArrayList<String>> getVideoIdList() {
        return videoIDs;
    }

    public void setVideoIDs(ArrayList<String> videoIDs) {
        this.videoIDs.setValue(videoIDs);
    }

    public MutableLiveData<ArrayList<String>> getVideoTitles() {
        return videoTitles;
    }

    public void setVideoTitles(ArrayList<String> videoTitles) {
        this.videoTitles.setValue(videoTitles);
    }

    public MutableLiveData<ArrayList<ThumbnailItem>> getPreviews() {
        return previews;
    }

    public void setPreviews(ArrayList<ThumbnailItem> previews) {
        this.previews.setValue(previews);
    }
}
