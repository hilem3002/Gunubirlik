package com.example.gunubirlik.AlarmManagers.Receivers;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.gunubirlik.Models.Events.CurrentEvent;
import com.example.gunubirlik.Models.Events.Event;
import com.example.gunubirlik.Models.PlanList;
import com.example.gunubirlik.Repositories.PlanListRepository;
import com.example.gunubirlik.Services.NotificationService;

import org.greenrobot.eventbus.EventBus;

public class NextEventUpdateReceiver extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        PlanListRepository repository = new PlanListRepository(context);

        // setting the next event to current event and then clears the next event, finished event
        PlanList planList = repository.getCurrentPlanList();
        Event event = planList.getNextEvent();
        CurrentEvent currentEvent = new CurrentEvent(event.getStartTime(),
                event.getEndTime(),
                event.getName(),
                event.getDescription(),
                false,
                false);
        planList.setCurrentEvent(currentEvent);
        planList.setNextEvent(null);
        planList.setLastEvent(null);

        // saving the planlist
        repository.updateCurrentPlanList(planList);

        // sending the notification
        NotificationService notificationService = new NotificationService(context);
        notificationService.sendNotification("Başlayan Etkinlik", currentEvent.getName());

        // passing the data to viewmodel
        EventBus.getDefault().post(planList);
    }
}
