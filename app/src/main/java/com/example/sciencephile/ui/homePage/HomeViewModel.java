package com.example.sciencephile.ui.homePage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> channelName;
    private MutableLiveData<String> subscriberCount;
    private MutableLiveData<String> totalViews;

    public HomeViewModel() {
        channelName = new MutableLiveData<>();
        subscriberCount = new MutableLiveData<>();
        totalViews = new MutableLiveData<>();
    }

    public LiveData<String> getChannelName() {
        return channelName;
    }

    public LiveData<String> getSubscriberCount() {
        return subscriberCount;
    }

    public LiveData<String> getTotalViews() {
        return totalViews;
    }

    public void setChannelName(String text) {
        this.channelName.setValue(text);
    }

    public void setSubscriberCount(String text) {
        this.subscriberCount.setValue(text);
    }

    public void setTotalViews(String text) {
        this.totalViews.setValue(text);
    }
}