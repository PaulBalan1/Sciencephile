package com.example.sciencephile.ui.videosPage.playlists;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sciencephile.R;
import com.example.sciencephile.ServiceGenerator;
import com.example.sciencephile.YouTubeAPI;
import com.example.sciencephile.responses.ChannelDataResponse;
import com.google.gson.JsonObject;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPageActivity extends AppCompatActivity {

    private TextView videoTitle, description;
    private YouTubePlayerView playerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_page);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        videoTitle = findViewById(R.id.title);
        description = findViewById(R.id.description);
        playerView = findViewById(R.id.player);
        playerView.getYouTubePlayerWhenReady(youTubePlayer -> {
            youTubePlayer.loadVideo(getIntent().getStringExtra("videoID"), 0);
        });
        requestVideoData(getIntent().getStringExtra("videoID"), this);
    }

    private void requestVideoData(String videoID, VideoPageActivity activity){
        YouTubeAPI youTubeAPI = ServiceGenerator.getYouTubeAPI();
        Call<ChannelDataResponse> getVideoTitles = youTubeAPI.getVideoInfo(videoID, getString(R.string.API_KEY_DATA));
        getVideoTitles.enqueue(new Callback<ChannelDataResponse>() {

            @Override
            public void onResponse(Call<ChannelDataResponse> call, Response<ChannelDataResponse> response) {
                if(response.code() == 200){
                    JsonObject video = response.body().getItems().get(0);
                    String title = video.get("snippet").getAsJsonObject().get("title").getAsString();
                    String descriptionText = video.get("snippet").getAsJsonObject().get("description").getAsString();
                    description.setText(descriptionText);
                    getSupportActionBar().setTitle(title);
                }
            }

            @Override
            public void onFailure(Call<ChannelDataResponse> call, Throwable t) {
                Log.i("Retrofit", "The JSON for videos is probably screwed :/");
            }
        });
    }
}
