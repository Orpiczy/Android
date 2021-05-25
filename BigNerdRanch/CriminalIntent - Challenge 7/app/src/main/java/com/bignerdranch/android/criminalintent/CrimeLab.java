package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getID().equals(id)) {
                return crime;
            }
        }
        return null;
    }

    public void deleteCrime(final Crime crime){
        mCrimes.removeIf(n -> (n.getID().equals(crime.getID())));
    }

    public void addCrime(Crime crime){
        mCrimes.add(crime);
    }
}