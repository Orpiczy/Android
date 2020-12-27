package com.blackwraith.android.today;

import android.util.Log;

import androidx.fragment.app.Fragment;

public class MainActivity extends SingleFragmentActivity implements StartUpFragment.Callbacks  {
    private static final  String TAG = "MainActivity";


    @Override
    protected Fragment createFragment() {
        Log.d(TAG,"wywołanie metody : createFragment w MainActivity");
        return new StartUpFragment();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onTaskSelected(TaskToDo task) {
        // TO DO
    }

    @Override
    public void onTaskCompleted(TaskToDo task) {
        // TO DO
    }
}