package com.example.gunubirlik.Calculators;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventExecutionTimeCalculator {
    private final String startTime;
    private final String finishTime;
    private final LocalTime localTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public EventExecutionTimeCalculator(String startTime, String finishTime) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        localTime = LocalTime.now();
    }

    public int actualStartMinusStart() {
        String actualStartTime = localTime.format(formatter);
        return convertTimeToMinuteInt(actualStartTime) - convertTimeToMinuteInt(startTime);
    }

    public int finishMinusActualFinish() {
        String actualFinishTime = localTime.format(formatter);
        return convertTimeToMinuteInt(finishTime) - convertTimeToMinuteInt(actualFinishTime);
    }

    private int convertTimeToMinuteInt(String time) {
        return Integer.parseInt(time.substring(0,2))*60 + Integer.parseInt(time.substring(3,5));
    }

    public String successRate(int delayTime) {
        int totalTime = convertTimeToMinuteInt(finishTime) - convertTimeToMinuteInt(startTime);
        return "%" + String.valueOf((int) ((double) (totalTime - delayTime) / (double) totalTime * 100));
    }
}
