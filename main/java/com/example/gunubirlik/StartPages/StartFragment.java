package com.example.gunubirlik.StartPages;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gunubirlik.Calculators.DateEventDateConverter;
import com.example.gunubirlik.Models.PlanList;
import com.example.gunubirlik.Models.ScheduledEventDate;
import com.example.gunubirlik.R;
import com.example.gunubirlik.Repositories.PlanListRepository;
import com.example.gunubirlik.HomePages.StableHomeActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class StartFragment extends Fragment {

    private TextInputEditText listNameEdittextToStart;
    private MaterialButton creatListButtonOnStart;
    private PlanListRepository repository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        listNameEdittextToStart = view.findViewById(R.id.listNameEdittextToStart);
        creatListButtonOnStart = view.findViewById(R.id.creatListButtonOnStart);
        repository = new PlanListRepository(getContext());

        creatListButtonOnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String listName = listNameEdittextToStart.getText().toString();
                if (listName.trim().equals("")) {
                    Toast.makeText(getActivity(), "bu alan boş bırakılamaz", Toast.LENGTH_SHORT).show();
                }
                else {
                    // setting the today and next 14 days in the start
                    int size = 15;
                    List<ScheduledEventDate> scheduledEventDates = new ArrayList<>(size);
                    ScheduledEventDate scheduledEventDate;
                    DateEventDateConverter converter = new DateEventDateConverter();
                    scheduledEventDate = new ScheduledEventDate(converter.convert());
                    scheduledEventDates.add(scheduledEventDate);
                    for (int i = 1; i<size; i++) {
                        converter.futureDate(1);
                        scheduledEventDate = new ScheduledEventDate(converter.convert());
                        scheduledEventDates.add(scheduledEventDate);
                    }
                    PlanList list = new PlanList(listName, scheduledEventDates);
                    repository.addNewPlanlistToSharedPreference(list);
                    Intent intent = new Intent(getActivity(), StableHomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}