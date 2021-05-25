package com.bignerdranch.android.sunset;

import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class SunsetActivity extends com.bignerdranch.android.draganddraw.SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return SunsetFragment.newInstance();
    }

}