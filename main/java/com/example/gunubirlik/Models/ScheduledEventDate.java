package com.example.gunubirlik.Models;

import com.example.gunubirlik.Models.Events.Event;

import java.util.List;

public class ScheduledEventDate {
    private List<Event> events;
    private EventDate date;

    public ScheduledEventDate(List<Event> events, EventDate date) {
        this.events = events;
        this.date = date;
    }

    public ScheduledEventDate(EventDate date) {
        this.date = date;
    }

    public List<Event> getEvents() {
        return events;
    }

    public EventDate getDate() {
        return date;
    }

    public void setDate(EventDate date) {
        this.date = date;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
