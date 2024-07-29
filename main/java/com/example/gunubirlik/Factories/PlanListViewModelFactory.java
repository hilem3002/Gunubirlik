package com.example.gunubirlik.Factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.gunubirlik.ViewModels.PlanListViewModel;

public class PlanListViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public PlanListViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PlanListViewModel.class)) {
            return (T) new PlanListViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
