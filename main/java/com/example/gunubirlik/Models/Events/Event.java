package com.example.gunubirlik.Models.Events;

public class Event {
    private String startTime;
    private String endTime;
    private String name;
    private String description;

    public Event(String startTime, String endTime, String name, String description) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.description = description;
    }

    public Event() {}

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

}
