package com.example.gunubirlik.Services;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gunubirlik.Models.PlanList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.List;

public class SharedPreferenceService {
    private final SharedPreferences sp;
    private final Gson gson;
    private final SharedPreferences.Editor editor;
    private String json;

    public SharedPreferenceService(Context context) {
        this.sp = context.getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        this.gson = new Gson();
        this.editor = sp.edit();
    }

    public void setSharedPreference(List<PlanList> listOfLists) {
        this.json = gson.toJson(listOfLists);
        this.editor.putString("listOfLists", this.json);
        this.editor.apply();
    }

    public String getSharedPreferenceJson() {
        return sp.getString("listOfLists", "empty");
    }

    public boolean isSharedPreferenceEmpty() {
        return getSharedPreferenceJson().equals("empty");
    }

    public List<PlanList> getSharedPreferenceList() {
        this.json = getSharedPreferenceJson();
        Type type = new TypeToken<List<PlanList>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void addNewPlanlistToSharedPreference(PlanList list) {
        List<PlanList> listOfLists = getSharedPreferenceList();
        listOfLists.add(0, list);
        setSharedPreference(listOfLists);
    }

    public void updateCurrentPlanlist(PlanList list) {
        List<PlanList> listOfLists = getSharedPreferenceList();
        listOfLists.remove(0);
        listOfLists.add(0, list);
        setSharedPreference(listOfLists);
    }

    // selected plan list always in 0. index
    public PlanList getCurrentPlanList() {
        return getSharedPreferenceList().get(0);
    }

}
