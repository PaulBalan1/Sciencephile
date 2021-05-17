package com.example.sciencephile.ui.videosPage.playlist;

public class ThumbnailItem {
    private String thumbnailUrl;
    private String title;

    public ThumbnailItem(String thumbnailUrl, String title){
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
