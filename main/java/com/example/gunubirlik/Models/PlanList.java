package com.example.gunubirlik.Models;

import com.example.gunubirlik.Models.Events.CurrentEvent;
import com.example.gunubirlik.Models.Events.Event;
import com.example.gunubirlik.Models.Events.FinishedEvent;

import java.util.List;

public class PlanList {
    private final String listName;
    private List<ScheduledEventDate> listDates;
    private FinishedEvent lastEvent;
    private CurrentEvent currentEvent;
    private Event nextEvent;

    public PlanList(String listName, List<ScheduledEventDate> listDates) {
        this.listName = listName;
        this.listDates = listDates;
    }

    public String getListName() {
        return listName;
    }

    public List<ScheduledEventDate> getListDates() {
        return listDates;
    }

    public FinishedEvent getFinishedEvent() {
        return lastEvent;
    }

    public CurrentEvent getCurrentEvent() {
        return currentEvent;
    }

    public Event getNextEvent() {
        return nextEvent;
    }

    public void setListDates(List<ScheduledEventDate> listDates) {
        this.listDates = listDates;
    }

    public void setLastEvent(FinishedEvent lastEvent) {
        this.lastEvent = lastEvent;
    }

    public void setCurrentEvent(CurrentEvent currentEvent) {
        this.currentEvent = currentEvent;
    }

    public void setNextEvent(Event nextEvent) {
        this.nextEvent = nextEvent;
    }
}
