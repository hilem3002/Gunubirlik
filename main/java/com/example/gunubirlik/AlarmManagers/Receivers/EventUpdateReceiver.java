package com.example.gunubirlik.AlarmManagers.Receivers;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.gunubirlik.Models.Events.Event;
import com.example.gunubirlik.Models.PlanList;
import com.example.gunubirlik.Repositories.PlanListRepository;
import com.example.gunubirlik.Services.NotificationService;

import org.greenrobot.eventbus.EventBus;

public class EventUpdateReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        PlanListRepository repository = new PlanListRepository(context);

        // removing from the list and setting to next event
        PlanList planList = repository.getCurrentPlanList();
        Event event = planList.getListDates().get(0).getEvents().remove(0);
        planList.setNextEvent(event);

        // saving the updated list and next event
        repository.updateCurrentPlanList(planList);

        // sending the notification
        NotificationService notificationService = new NotificationService(context);
        notificationService.sendNotification("Yaklaşan Etkinlik", event.getName());

        // passing the data to view model
        EventBus.getDefault().post(planList.getNextEvent());
        EventBus.getDefault().post(planList.getListDates());
    }
}
