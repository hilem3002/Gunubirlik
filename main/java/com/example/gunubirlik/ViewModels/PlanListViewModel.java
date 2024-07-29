package com.example.gunubirlik.ViewModels;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.gunubirlik.Models.PlanList;
import com.example.gunubirlik.Repositories.PlanListRepository;

public class PlanListViewModel extends ViewModel {
    private final MutableLiveData<PlanList> planListLive = new MutableLiveData<>();
    private final PlanListRepository planListRepository;

    public PlanListViewModel(Context context) {
        planListRepository = new PlanListRepository(context);
    }

    public void setPlanList() {
        planListLive.setValue(planListRepository.getCurrentPlanList());
    }

    public void setPlanList(PlanList planList) {
        planListLive.setValue(planList);
    }

    public LiveData<PlanList> getPlanList() {
        return planListLive;
    }

    public void savePlanList() {
        planListRepository.updateCurrentPlanList(planListLive.getValue());
    }

}
