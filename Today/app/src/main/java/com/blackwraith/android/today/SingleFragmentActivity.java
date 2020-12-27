package com.blackwraith.android.today;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    private static final String TAG = "Activity";
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"Wywołanie metody: Oncreate w SingleFragmentActivity");
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_act_main);

        if(fragment==null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container_act_main,fragment).commit();
        }
    }

    @LayoutRes
    protected int getLayoutResId(){
        return R.layout.activity_main;
    }
}
