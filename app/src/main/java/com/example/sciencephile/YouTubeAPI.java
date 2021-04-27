package com.example.sciencephile;

import com.example.sciencephile.responses.ChannelDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeAPI {

    @GET("youtube/v3/channels?part=snippet&part=statistics")
    Call<ChannelDataResponse> getChannelData(@Query("id") String id, @Query("key") String key);
}
