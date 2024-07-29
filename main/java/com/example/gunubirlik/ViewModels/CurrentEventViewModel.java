package com.example.gunubirlik.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gunubirlik.Models.Events.CurrentEvent;

public class CurrentEventViewModel extends ViewModel {
    private final MutableLiveData<CurrentEvent> currentEventLive = new MutableLiveData<>();

    public void setCurrentEvent(CurrentEvent currentEvent) {
        currentEventLive.setValue(currentEvent);
    }

    public LiveData<CurrentEvent> getCurrentEvent() {
        return currentEventLive;
    }

}
