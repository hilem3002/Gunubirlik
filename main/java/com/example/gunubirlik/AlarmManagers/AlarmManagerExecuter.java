package com.example.gunubirlik.AlarmManagers;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.example.gunubirlik.AlarmManagers.Receivers.ListDatesUpdateReceiver;

import java.util.Calendar;

public class AlarmManagerExecuter {
    private final Calendar calendar = Calendar.getInstance();
    private final AlarmManager alarmManager;
    private final Context context;
    private Intent intent;
    private PendingIntent pendingIntent;

    public AlarmManagerExecuter(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarmScheduledDateEvent() {
        setPendingIntent(0, ListDatesUpdateReceiver.class);

        // setting the alarm at midnight
        setAlarmTime(0, 0);

        // alarm will be setted after each mid night
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // alarm is waking up the cpu at the exact time
        wakeUpTheCpu();
    }

    public void setAlarmEvent(Class<?> cls, int requestCode, int hour, int min) {
        setPendingIntent(requestCode, cls);

        // setting the alarm at event time
        setAlarmTime(hour, min);

        //alarm is waking up the cpu at the exact time
        wakeUpTheCpu();
    }

    @SuppressLint("ScheduleExactAlarm")
    private void wakeUpTheCpu() {
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    private void setAlarmTime(int hour, int min) {
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
    }

    private void setPendingIntent(int requestCode, Class<?> cls) {
        intent = new Intent(context, cls);
        pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }
    
}
