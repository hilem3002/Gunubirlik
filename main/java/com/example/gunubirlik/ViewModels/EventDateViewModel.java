package com.example.gunubirlik.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gunubirlik.Models.EventDate;

public class EventDateViewModel extends ViewModel {
    private final MutableLiveData<EventDate> eventDateLive = new MutableLiveData<>();

    public void setEventDate(EventDate eventDate) {
        eventDateLive.setValue(eventDate);
    }

    public LiveData<EventDate> getEventDate() {
        return eventDateLive;
    }
}
