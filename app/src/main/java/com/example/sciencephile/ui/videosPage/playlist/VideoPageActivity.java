package com.example.sciencephile.ui.videosPage.playlist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sciencephile.FilePersistance;
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

    private TextView videoTitle, description, testText;
    private YouTubePlayerView playerView;
    private Toolbar toolbar;
    private String videoURL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_page);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        videoTitle = findViewById(R.id.title);
        description = findViewById(R.id.description);
        playerView = findViewById(R.id.player);
        playerView.getYouTubePlayerWhenReady(youTubePlayer -> {
            youTubePlayer.loadVideo(getIntent().getStringExtra("videoID"), 0);
        });
        requestVideoData(getIntent().getStringExtra("videoID"), this);
        Button share = findViewById(R.id.share);
        share.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Share video");
            i.putExtra(Intent.EXTRA_TEXT, "Check out this awesome Sciencephile video!" + "\n" + videoURL);
            startActivity(Intent.createChooser(i, "Share video"));
        });

        Button watchOnYT = findViewById(R.id.watchOnYT);
        watchOnYT.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoURL))));
    }

    private void requestVideoData(String videoID, VideoPageActivity activity){
        YouTubeAPI youTubeAPI = ServiceGenerator.getYouTubeAPI();
        Call<ChannelDataResponse> getVideoTitles = youTubeAPI.getVideoInfo(videoID, getString(R.string.API_KEY_DATA));
        getVideoTitles.enqueue(new Callback<ChannelDataResponse>() {

            @Override
            public void onResponse(Call<ChannelDataResponse> call, Response<ChannelDataResponse> response) {
                if(response.code() == 200){
                    JsonObject video = response.body().getItems().get(0);
                    videoURL = "https://www.youtube.com/watch?v=" + videoID;
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

    private void addToFavorites(String videoID){
        String favoriteVideoIDs = FilePersistance.readFromFile(this);
        favoriteVideoIDs += (videoID + ",");
        FilePersistance.writeToFile(favoriteVideoIDs, this);
    }

    private void removeFromFavorites(String videoID){
        String favoriteVideoIDs = FilePersistance.readFromFile(this);

        String videoIds[] = favoriteVideoIDs.split(",");

        for (String id: videoIds) {
            if(id.equals(videoID)){
                id="";
                break;
            }
        }

        for(int i=0; i<videoIds.length; i++){
            if(videoIds[i].equals(videoID)){
                videoIds[i]="";
                break;
            }
        }


        String newVideoIDList = "";
        for (String id: videoIds) {
            if(id!=""){
                newVideoIDList += (id + ",");
            }
        }
        FilePersistance.writeToFile(newVideoIDList, this);
    }

    private boolean checkIfSaved(String videoID){
        String favoriteVideoIDs = FilePersistance.readFromFile(this);
        String videoIds[] = favoriteVideoIDs.split(",");
        for (String id: videoIds) {
            if(id.equals(videoID)){
                return true;
            }
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.video_toolbar, menu);
        MenuItem heart = menu.findItem(R.id.favorites);
        if(checkIfSaved(getIntent().getStringExtra("videoID"))){
            heart.setIcon(R.drawable.ic_baseline_favorite_24);
        }else heart.setIcon(R.drawable.empty_heart);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorites:
                if(!checkIfSaved(getIntent().getStringExtra("videoID"))){
                    addToFavorites(getIntent().getStringExtra("videoID"));
                    item.setIcon(R.drawable.ic_baseline_favorite_24);
                    Toast.makeText(this, "Added video to favorites", Toast.LENGTH_LONG).show();
                }
                else{
                  removeFromFavorites(getIntent().getStringExtra("videoID"));
                  item.setIcon(R.drawable.empty_heart);
                  Toast.makeText(this, "Removed video from favorites", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
