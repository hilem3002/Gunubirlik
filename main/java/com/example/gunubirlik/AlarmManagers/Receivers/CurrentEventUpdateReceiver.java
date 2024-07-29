package com.example.gunubirlik.AlarmManagers.Receivers;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.gunubirlik.Calculators.EventExecutionTimeCalculator;
import com.example.gunubirlik.Models.Events.CurrentEvent;
import com.example.gunubirlik.Models.Events.FinishedEvent;
import com.example.gunubirlik.Models.PlanList;
import com.example.gunubirlik.Repositories.PlanListRepository;

import org.greenrobot.eventbus.EventBus;

public class CurrentEventUpdateReceiver extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        PlanListRepository repository = new PlanListRepository(context);

        // getting the current event and setting to finished event
        PlanList planList = repository.getCurrentPlanList();
        CurrentEvent currentEvent = planList.getCurrentEvent();

        // calculating the success rate
        EventExecutionTimeCalculator calculator = new EventExecutionTimeCalculator(currentEvent.getStartTime(), currentEvent.getEndTime());
        String rate;
        if (!currentEvent.isStarted() && !currentEvent.isFinished()) {
            rate = "0";
        }
        else {
            rate = calculator.successRate(currentEvent.getLateTime());
        }

        currentEvent.setStarted(true);
        currentEvent.setFinished(true);
        FinishedEvent finishedEvent = new FinishedEvent(
                currentEvent.getStartTime(),
                currentEvent.getEndTime(),
                currentEvent.getName(),
                currentEvent.getDescription(),
                rate);
        planList.setLastEvent(finishedEvent);

        // saving the data
        repository.updateCurrentPlanList(planList);

        // passing data to viewnodel
        EventBus.getDefault().post(planList);
    }
}
