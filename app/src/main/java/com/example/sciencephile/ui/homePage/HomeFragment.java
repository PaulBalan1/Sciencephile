package com.example.sciencephile.ui.homePage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sciencephile.R;
import com.example.sciencephile.ServiceGenerator;
import com.example.sciencephile.YouTubeAPI;
import com.example.sciencephile.responses.ChannelDataResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private String channelName;
    private String subscribers;
    private String totalViews;
    private String channelDescription;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        requestChannelData();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView channelName = root.findViewById(R.id.channelName);
        final TextView channelDescription = root.findViewById(R.id.channelDescription);
        final TextView subscriberCount = root.findViewById(R.id.subscriberCount);
        final TextView totalViews = root.findViewById(R.id.totalViews);
        homeViewModel.getChannelName().observe(getViewLifecycleOwner(), (Observer<String>) s -> channelName.setText(s));
        homeViewModel.getChannelDescription().observe(getViewLifecycleOwner(), (Observer<String>) s -> channelDescription.setText(s));
        homeViewModel.getSubscriberCount().observe(getViewLifecycleOwner(), (Observer<String>) s -> subscriberCount.setText(s));
        homeViewModel.getTotalViews().observe(getViewLifecycleOwner(), (Observer<String>) s -> totalViews.setText(s));
        LinearLayout linearLayout = root.findViewById(R.id.link);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC7BhHN8NyMMru2RUygnDXSg")));
            }
        });
        return root;
    }

    public synchronized void requestChannelData(){
        YouTubeAPI youTubeAPI = ServiceGenerator.getYouTubeAPI();
        Call<ChannelDataResponse> call = youTubeAPI.getChannelData(getString(R.string.channel_id), getString(R.string.API_KEY_DATA));
        call.enqueue(new Callback<ChannelDataResponse>(){
            @Override
            public void onResponse(Call<ChannelDataResponse> call, Response<ChannelDataResponse> response){
                if(response.code() == 200){
                    JsonObject mainItem = response.body().getItems().get(0);
                    channelName = mainItem.get("snippet").getAsJsonObject().get("title").getAsString();
                    channelDescription = mainItem.get("snippet").getAsJsonObject().get("description").getAsString();
                    subscribers = mainItem.get("statistics").getAsJsonObject().get("subscriberCount").getAsString();
                    totalViews = mainItem.get("statistics").getAsJsonObject().get("viewCount").getAsString();
                    homeViewModel.setChannelName(channelName);
                    homeViewModel.setSubscriberCount("~" + subscribers + " subscribers");
                    homeViewModel.setTotalViews(totalViews + " total views");
                    homeViewModel.setChannelDescription(channelDescription);
                }
            }

            @Override
            public void onFailure(Call<ChannelDataResponse> call, Throwable t) {
                Log.i("Retrofit", "The JSON is probably screwed :/");
            }
        });
    }
}