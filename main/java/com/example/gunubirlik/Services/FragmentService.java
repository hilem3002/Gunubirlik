package com.example.gunubirlik.Services;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentService {
    public static void startFragment(Fragment fragment, FragmentManager manager, int layout) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(layout, fragment);
        transaction.commit();
    }
}
