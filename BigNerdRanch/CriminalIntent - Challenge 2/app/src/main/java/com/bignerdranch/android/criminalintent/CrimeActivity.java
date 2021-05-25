package com.bignerdranch.android.criminalintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

public class CrimeActivity extends SingleFragmentActivity {
    private static final String TAG = "Activity";
    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "Wywołanie metody: createFragment w CrimeActivity");
        return new CrimeFragment();
    }

}