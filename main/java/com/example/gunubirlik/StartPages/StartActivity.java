package com.example.gunubirlik.StartPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gunubirlik.Models.PlanList;
import com.example.gunubirlik.R;
import com.example.gunubirlik.Repositories.PlanListRepository;
import com.example.gunubirlik.Services.FragmentService;
import com.example.gunubirlik.HomePages.StableHomeActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    private MaterialButton creatingListToStartButton;
    private List<PlanList> listOfLists = new ArrayList<>();
    private PlanListRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        repository = new PlanListRepository(this);
        if (repository.isSharedPreferenceEmpty()) {
            repository.setSharedPreference(listOfLists);

            creatingListToStartButton = findViewById(R.id.creatingListToStartButton);
            creatingListToStartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StartFragment fragment = new StartFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentService.startFragment(fragment, manager, R.id.startLayout);
                }
            });
        }
        else {
            Intent intent = new Intent(StartActivity.this, StableHomeActivity.class);
            startActivity(intent);
        }

    }
}