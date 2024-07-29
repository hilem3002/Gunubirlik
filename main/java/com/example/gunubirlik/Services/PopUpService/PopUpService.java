package com.example.gunubirlik.Services.PopUpService;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TimePicker;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Locale;


public class PopUpService {
    private final Context context;
    private final ConstraintLayout layout;
    int hour, min;
    private final View view;
    private PopupWindow popupWindow;

    public PopUpService(Context context, ConstraintLayout layout, int resource) {
        this.context = context;
        this.layout = layout;
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = inflater.inflate(resource, null);
    }

    // resource is the layout that is going to execute
    public void popUpExecution() {
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        popupWindow = new PopupWindow(view, width, height, true);
        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });
    }

    public void popUpClose() {
        popupWindow.dismiss();
    }

    public void timePickerExecute(OnTimeSelect onTimeSelect) {;
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                hour = i;
                min = i1;
                String time = String.format(Locale.getDefault(), "%02d:%02d", i, i1);
                onTimeSelect.OnTimeSelection(time);
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, style, onTimeSetListener, hour, min, true);
        timePickerDialog.setTitle("seç");
        timePickerDialog.show();
    }

    public View getView() {
        return view;
    }
}
