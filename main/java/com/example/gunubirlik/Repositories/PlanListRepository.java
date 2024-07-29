package com.example.gunubirlik.Repositories;

import android.content.Context;

import com.example.gunubirlik.Models.PlanList;
import com.example.gunubirlik.Services.SharedPreferenceService;

import java.util.List;

public class PlanListRepository {
    private final SharedPreferenceService preferenceService;

    public PlanListRepository(Context context) {
        preferenceService = new SharedPreferenceService(context);
    }

    public PlanList getCurrentPlanList() {
        return preferenceService.getCurrentPlanList();
    }

    public void updateCurrentPlanList(PlanList planList) {
        preferenceService.updateCurrentPlanlist(planList);
    }

    public boolean isSharedPreferenceEmpty() {
        return preferenceService.isSharedPreferenceEmpty();
    }

    public void setSharedPreference(List<PlanList> listOfPlanlists) {
        preferenceService.setSharedPreference(listOfPlanlists);
    }

    public void addNewPlanlistToSharedPreference(PlanList list) {
        preferenceService.addNewPlanlistToSharedPreference(list);
    }
}
