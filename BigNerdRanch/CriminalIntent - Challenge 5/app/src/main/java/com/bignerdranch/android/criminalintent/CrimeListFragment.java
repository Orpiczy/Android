package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

public class CrimeListFragment extends Fragment {
    private static final String TAG = "Activity";
    private static final int REQUEST_CODE_CRIME = 1;
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Wywołanie metody: OncreateView w CrimeListFargment");

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //potrzebny do prawidlowego dzialania RecyclerView
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        Log.d(TAG, "Wywołanie metody: updateUI() w CrimeListFargment");
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            Log.d(TAG, "Wywołanie metody: konstruktora CrimeHolder w CrimeListFargment");
            itemView.setOnClickListener(this); //jest sam dla siebie listenerem
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);

        }

        public void bind(Crime crime) {
            Log.d(TAG, "Wywołanie metody: bind w CrimeListFargment");
            mCrime = crime;
            SimpleDateFormat properDateFormat = new SimpleDateFormat("EEE, d MMMM yyyy, HH:mm:ss");
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(properDateFormat.format(mCrime.getDate()));
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.INVISIBLE);
        }


        @Override
        public void onClick(View view) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getID());
            startActivityForResult(intent, REQUEST_CODE_CRIME);
        }
    }


    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;


        public CrimeAdapter(List<Crime> crimes) {
            Log.d(TAG, "Wywołanie metody: konstruktor CrimeAdapter w CrimeListFargment");
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d(TAG, "Wywołanie metody: onCreateViwHolder w CrimeAdapter w CrimeListFargment");
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            //jej złożoność wpływa na płynnośc animacji więc pilnuj by jej złożoność była minimalna
            Log.d(TAG, "Wywołanie metody: onbindViwHolder w CrimeAdapter w CrimeListFargment");
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {

            Log.d(TAG, "Wywołanie metody: getItemCount w CrimeAdapter w CrimeListFargment");
            return mCrimes.size();
        }
    }

}

