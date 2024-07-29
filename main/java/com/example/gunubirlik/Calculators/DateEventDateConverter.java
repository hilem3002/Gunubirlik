package com.example.gunubirlik.Calculators;

import com.example.gunubirlik.Models.EventDate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateEventDateConverter {
    private final String[] dayNames = {"Pzt", "Sal", "Çar", "Per", "Cum", "Cmt", "Paz"};
    private final String[] monthNames = {
            "Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"
    };
    private LocalDate localDate;

    public DateEventDateConverter() {
        localDate = LocalDate.now();
    }

    public void futureDate(int plusDays) {
        this.localDate = localDate.plusDays(plusDays);
    }

    public EventDate convert() {
        return new EventDate(
                dayNames[localDate.getDayOfWeek().getValue() - 1],
                String.valueOf(localDate.getDayOfMonth()),
                monthNames[localDate.getMonthValue() - 1]);
    }

    public static String minus15Min(String time) {
        // converting string to local date time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(time, formatter);
        return localTime.minusMinutes(15).toString();
    }

    public static int[] stringToIntTime(String time) {
        String hour = time.substring(0,2);
        String min = time.substring(3,5);
        if (hour.charAt(0) == '0') {
            hour = String.valueOf(hour.charAt(1));
        }
        if (min.charAt(0) == '0') {
            min = String.valueOf(min.charAt(1));
        }
        return new int[]{Integer.parseInt(hour), Integer.parseInt(min)};
    }

}