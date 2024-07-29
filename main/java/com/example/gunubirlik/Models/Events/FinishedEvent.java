package com.example.gunubirlik.Models.Events;

public class FinishedEvent extends Event {

    private final String successRate;

    public FinishedEvent(String startTime, String endTime, String name, String description, String successRate) {
        super(startTime, endTime, name, description);
        this.successRate = successRate;
    }

    public String getSuccessRate() {
        return successRate;
    }
}
