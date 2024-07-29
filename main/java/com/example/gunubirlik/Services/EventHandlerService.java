package com.example.gunubirlik.Services;

import com.example.gunubirlik.Models.Events.Event;
import com.example.gunubirlik.Models.PlanList;
import com.example.gunubirlik.Models.ScheduledEventDate;
import com.example.gunubirlik.ViewModels.ListDatesViewModel;
import com.example.gunubirlik.ViewModels.NextEventViewModel;
import com.example.gunubirlik.ViewModels.PlanListViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

// used to pass data from broadcast receiver
public class EventHandlerService {
    ListDatesViewModel listDatesViewModel;
    NextEventViewModel nextEventViewModel;
    PlanListViewModel planListViewModel;

    public EventHandlerService(PlanListViewModel planListViewModel) {
        this.planListViewModel = planListViewModel;
        register();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(List<ScheduledEventDate> listDates) {
        listDatesViewModel.setListDates(listDates);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(Event event) {
        nextEventViewModel.setNextEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(PlanList planList) {
        planListViewModel.setPlanList(planList);
    }

    public void unregister() {
        EventBus.getDefault().unregister(this);
    }

    private void register() {
        EventBus.getDefault().register(this);
    }

    public void setListDatesViewModel(ListDatesViewModel listDatesViewModel) {
        this.listDatesViewModel = listDatesViewModel;
    }

    public void setNextEventViewModel(NextEventViewModel nextEventViewModel) {
        this.nextEventViewModel = nextEventViewModel;
    }
}
