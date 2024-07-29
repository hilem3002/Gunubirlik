package com.example.gunubirlik.Models.Events;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.gunubirlik.Calculators.EventExecutionTimeCalculator;
import com.example.gunubirlik.R;
import com.google.android.material.button.MaterialButton;

public class CurrentEvent extends Event {
    private boolean isStarted;
    private boolean isFinished;
    private int lateTime = 0;
    private EventExecutionTimeCalculator calculator;

    public CurrentEvent(String startTime, String endTime, String name, String description, boolean isStarted, boolean isFinished) {
        super(startTime, endTime, name, description);
        this.isStarted = isStarted;
        this.isFinished = isFinished;
    }

    public void clickedAsStarted(Context context, MaterialButton button) {
        if (!isStarted) {
            this.isStarted = true;
            // calculating the how much time is clicking action delay
            calculator = new EventExecutionTimeCalculator(getStartTime(), getEndTime());
            lateTime = calculator.actualStartMinusStart();
        }
        button.setText("Bitirdim olarak işaretle");
        button.setBackgroundColor(ContextCompat.getColor(context, R.color.attentionColor));
        button.setTextColor(Color.WHITE);
    }

    public FinishedEvent clicedAsFinished(Context context, MaterialButton button) {
        this.isFinished = true;

        // calculating the how much time is clicking action delay
        calculator = new EventExecutionTimeCalculator(getStartTime(), getEndTime());
        lateTime += calculator.finishMinusActualFinish();
        String rate = calculator.successRate(lateTime);

        button.setVisibility(View.GONE);
        button.setText("Başladım olarak işaretle");
        button.setBackgroundColor(ContextCompat.getColor(context, R.color.buttonColorGreen));

        return new FinishedEvent(getStartTime(), getEndTime(), getName(), getDescription(), rate);
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public int getLateTime() {
        return lateTime;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

}
