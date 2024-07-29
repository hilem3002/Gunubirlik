package com.example.gunubirlik.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gunubirlik.Models.Events.FinishedEvent;

public class FinishedEventViewModel extends ViewModel {
    private final MutableLiveData<FinishedEvent> finishedEventLive = new MutableLiveData<>();

    public void setFinishedEvent(FinishedEvent finishedEvent) {
        finishedEventLive.setValue(finishedEvent);
    }

    public LiveData<FinishedEvent> getFinishedEvent() {
        return finishedEventLive;
    }
}
