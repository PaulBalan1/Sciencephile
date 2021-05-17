package com.example.sciencephile.ui.favoritesPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sciencephile.FilePersistance;
import com.example.sciencephile.R;
import com.example.sciencephile.ServiceGenerator;
import com.example.sciencephile.YouTubeAPI;
import com.example.sciencephile.responses.ChannelDataResponse;
import com.example.sciencephile.ui.videosPage.playlist.Adapter;
import com.example.sciencephile.ui.videosPage.playlist.RecyclerItemClickListener;
import com.example.sciencephile.ui.videosPage.playlist.ThumbnailItem;
import com.example.sciencephile.ui.videosPage.playlist.VideoPageActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<ThumbnailItem> previews;
    private ArrayList<String> videoIDs, videoTitles;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoIDs = new ArrayList<>();
        videoTitles = new ArrayList<>();
        previews = new ArrayList<>();
        loadFavoriteVideos();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        final TextView textView = root.findViewById(R.id.section_label2);

        recyclerView = root.findViewById(R.id.recyclerView2);
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


        favoritesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private synchronized void loadFavoriteVideos(){
        YouTubeAPI youTubeAPI = ServiceGenerator.getYouTubeAPI();
        String[] videoIds = FilePersistance.readFromFile(getContext()).split(",");
        for(String videoID: videoIds){
            Call<ChannelDataResponse> getVideos = youTubeAPI.getVideoInfo(videoID, getString(R.string.API_KEY_DATA));

            getVideos.enqueue(new Callback<ChannelDataResponse>() {

                @Override
                public void onResponse(Call<ChannelDataResponse> call, Response<ChannelDataResponse> response) {
                    if(response.code() == 200){
                        //JsonObject video = response.body().getItems().get(0);
                        //String title = video.get("snippet").getAsJsonObject().get("title").getAsString();
                        //String url = video.get("snippet").getAsJsonObject().get("thumbnails").getAsJsonObject().get("default").getAsJsonObject().get("url").getAsString();
                        videoIDs.add(videoID);
                        //videoTitles.add(title);
                       //previews.add(new ThumbnailItem(url, title));
                    }
                }

                @Override
                public void onFailure(Call<ChannelDataResponse> call, Throwable t) {
                    Log.i("Retrofit", "The JSON for videos is probably screwed :/");
                }
            });
        }
    }
}