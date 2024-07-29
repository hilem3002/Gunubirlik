package com.example.gunubirlik.AlarmManagers.Receivers;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.gunubirlik.Calculators.DateEventDateConverter;
import com.example.gunubirlik.Models.PlanList;
import com.example.gunubirlik.Models.ScheduledEventDate;
import com.example.gunubirlik.Repositories.PlanListRepository;

import org.greenrobot.eventbus.EventBus;

public class ListDatesUpdateReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        PlanListRepository repository = new PlanListRepository(context);

        // removing the last day from list
        PlanList planList = repository.getCurrentPlanList();
        planList.getListDates().remove(0);

        // adding new scheluced event date to end of the list
        DateEventDateConverter converter = new DateEventDateConverter();
        converter.futureDate(14);
        planList.getListDates().add(new ScheduledEventDate(converter.convert()));

        // saving the updated list
        repository.updateCurrentPlanList(planList);

        // passing the data to view model
        EventBus.getDefault().post(planList.getListDates());
    }
}
