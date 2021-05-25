package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks {
    private static final String TAG = "Activity";

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "Wywołanie metody: createFragment w CrimeListActivity");
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_master_detail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getID());
            startActivity(intent);
        } else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getID());
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, newDetail).commit();
        }
    }

    @Override
    public void onCrimeDeleted(Crime crime) {
        int pos = -1;
        List<Crime> crimes = CrimeLab.get(this).getCrimes();
        for (int i = 0; i < crimes.size(); i++) {
            if (crime.getID().equals(crimes.get(i).getID())) {
                pos = i;
                break;
            }

        }
        if (findViewById(R.id.detail_fragment_container) != null && pos != -1) {
            if (pos >= 1) {
                Fragment newDetail = CrimeFragment.newInstance(CrimeLab.get(this).getCrimes().get(pos - 1).getID());
                getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, newDetail).commit();
            } else {
                getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.detail_fragment_container)).commit();
            }
        }
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        CrimeLab.get(this).deleteCrime(crime);
        listFragment.updateUI();

    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
