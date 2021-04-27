package com.example.sciencephile;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
        private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create());

        private static Retrofit retrofit = retrofitBuilder.build();

        private static YouTubeAPI youTubeAPI = retrofit.create(YouTubeAPI.class);

        public static YouTubeAPI getYouTubeAPI() {
            return youTubeAPI;
        }
}
