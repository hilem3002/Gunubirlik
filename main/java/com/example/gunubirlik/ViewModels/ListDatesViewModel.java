package com.example.gunubirlik.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gunubirlik.Models.ScheduledEventDate;



import java.util.List;

public class ListDatesViewModel extends ViewModel {
    private final MutableLiveData<List<ScheduledEventDate>> listDatesLive = new MutableLiveData<>();

    public void setListDates(List<ScheduledEventDate> listDate) {
        listDatesLive.setValue(listDate);
    }

    public LiveData<List<ScheduledEventDate>> getListDate() {
        return listDatesLive;
    }


}
