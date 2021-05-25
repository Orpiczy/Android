package com.bignerdranch.android.criminalintent;

import android.util.Log;

import androidx.fragment.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity {
    private static final String TAG ="Activity";
    @Override
    protected Fragment createFragment() {
        Log.d(TAG,"Wywołanie metody: createFragment w CrimeListActivity");
        return new CrimeListFragment();
    }
}
