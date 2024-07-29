package com.example.gunubirlik.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.gunubirlik.Models.ScheduledEventDate;

public class ScheduledEventDateViewModel extends ViewModel {
    private final MutableLiveData<ScheduledEventDate> schelucedEventDateLive = new MutableLiveData<>();

    public void setSchelucedEventDate(ScheduledEventDate scheduledEventDate) {
        schelucedEventDateLive.setValue(scheduledEventDate);
    }

    public LiveData<ScheduledEventDate> getSchelucedEventDate() {
        return schelucedEventDateLive;
    }


}
