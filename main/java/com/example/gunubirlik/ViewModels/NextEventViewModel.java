package com.example.gunubirlik.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gunubirlik.Models.Events.Event;

public class NextEventViewModel extends ViewModel {
    MutableLiveData<Event> nextEventLive = new MutableLiveData<>();

    public void setNextEvent(Event event) {
        nextEventLive.setValue(event);
    }

    public LiveData<Event> getNextEvent() {
        return nextEventLive;
    }
}
