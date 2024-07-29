package com.example.gunubirlik.Models;

import java.time.LocalDate;
import java.util.HashMap;

public class EventDate {
    private final String dayName;
    private final String dayNum;
    private final String month;

    public EventDate(String dayName, String dayNum, String month) {
        this.dayName = dayName;
        this.dayNum = dayNum;
        this.month = month;
    }

    public String getDayName() {
        return dayName;
    }

    public String getDayNum() {
        return dayNum;
    }

    public String getMonth() {
        return month;
    }


}
