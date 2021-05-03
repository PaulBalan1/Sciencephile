package com.example.sciencephile.ui.videosPage.playlists;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sciencephile.R;
import com.example.sciencephile.ServiceGenerator;
import com.example.sciencephile.YouTubeAPI;
import com.example.sciencephile.responses.ChannelDataResponse;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<String> videoIDs, videoTitles;
    private PlaylistViewModel playlistViewModel;
    private ArrayList<ThumbnailItem> previews;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static PlaylistFragment newInstance(int index) {
        PlaylistFragment fragment = new PlaylistFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoIDs = new ArrayList<>();
        videoTitles = new ArrayList<>();
        previews = new ArrayList<>();
        playlistViewModel = new ViewModelProvider(this).get(PlaylistViewModel.class);
        requestPlaylistItems();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.playlist_page, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(root.getContext());
        adapter = new Adapter(previews, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), VideoPageActivity.class);
                        intent.putExtra("videoID", videoIDs.get(position));
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // MAYBE MARK AS FAVORITE -------------------------------------------------
                    }
                })
        );

        playlistViewModel.getPreviews().observe(getViewLifecycleOwner(), s -> recyclerView.setAdapter(adapter));
        return root;
    }

    public synchronized void requestPlaylistItems(){
        YouTubeAPI youTubeAPI = ServiceGenerator.getYouTubeAPI();
        int playlistNum = getArguments().getInt(ARG_SECTION_NUMBER);
        int playlistId = R.string.playlist_astronomy;
        switch (playlistNum){
            case 0:
                playlistId = R.string.playlist_astronomy;
                break;
            case 1:
                playlistId = R.string.playlist_physics;
                break;
            case 2:
                playlistId = R.string.playlist_biology;
                break;
        }
        Call<ChannelDataResponse> getVideoIDs = youTubeAPI.getPlaylistIDs(getString(playlistId), getString(R.string.API_KEY_DATA));
        getVideoIDs.enqueue(new Callback<ChannelDataResponse>() {

            @Override
            public void onResponse(Call<ChannelDataResponse> call, Response<ChannelDataResponse> response) {
                if(response.code() == 200){
                    ArrayList<JsonObject> items = response.body().getItems();
                    for (JsonObject item: items) {
                        String videoID = item.get("snippet").getAsJsonObject().get("resourceId").getAsJsonObject().get("videoId").getAsString();

                        Call<ChannelDataResponse> getVideoTitles = youTubeAPI.getVideoInfo(videoID, getString(R.string.API_KEY_DATA));
                        getVideoTitles.enqueue(new Callback<ChannelDataResponse>() {

                            @Override
                            public void onResponse(Call<ChannelDataResponse> call, Response<ChannelDataResponse> response) {
                                if(response.code() == 200){
                                    JsonObject video = response.body().getItems().get(0);
                                    String title = video.get("snippet").getAsJsonObject().get("title").getAsString();
                                    String url = video.get("snippet").getAsJsonObject().get("thumbnails").getAsJsonObject().get("default").getAsJsonObject().get("url").getAsString();
                                    videoIDs.add(videoID);
                                    videoTitles.add(title);
                                    previews.add(new ThumbnailItem(url, title));
                                }
                            }

                            @Override
                            public void onFailure(Call<ChannelDataResponse> call, Throwable t) {
                                Log.i("Retrofit", "The JSON for videos is probably screwed :/");
                            }
                        });

                    }
                    playlistViewModel.setVideoTitles(videoTitles);
                    playlistViewModel.setVideoIDs(videoIDs);
                    playlistViewModel.setPreviews(previews);
                }
            }

            @Override
            public void onFailure(Call<ChannelDataResponse> call, Throwable t) {
                Log.i("Retrofit", "The JSON Astronomy is probably screwed :/");
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
