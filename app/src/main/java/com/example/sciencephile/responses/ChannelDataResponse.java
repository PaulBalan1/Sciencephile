package com.example.sciencephile.responses;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class ChannelDataResponse {

    private ArrayList<JsonObject> items;

    public ArrayList<JsonObject> getItems() {
        return items;
    }
}
