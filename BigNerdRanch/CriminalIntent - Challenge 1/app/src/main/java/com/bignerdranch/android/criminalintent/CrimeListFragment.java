package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private static final String TAG = "Activity";
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

    private void updateUI() {
        Log.d(TAG, "Wywołanie metody: updateUI() w CrimeListFargment");
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);

    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent, int layoutId) {

            super(inflater.inflate(layoutId, parent, false));

            Log.d(TAG, "Wywołanie metody: konstruktora CrimeHolder w CrimeListFargment");
            itemView.setOnClickListener(this); //jest sam dla siebie listenerem

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);

        }

        public void bind(Crime crime) {
            Log.d(TAG, "Wywołanie metody: bind w CrimeListFargment");
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }


        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mCrime.getTitle() + " klik!", Toast.LENGTH_SHORT).show();
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
            switch (viewType) {
                case 0:
                    return new CrimeHolder(layoutInflater, parent, R.layout.list_item_crime);
                case 1:
                    return new CrimeHolder(layoutInflater, parent, R.layout.list_item_requires_police_crime);
                default:
                    return new CrimeHolder(layoutInflater, parent, R.layout.list_item_crime);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            //jej złożoność wpływa na płynnośc animacji więc pilnuj by jej złożoność była minimalna
            Log.d(TAG, "Wywołanie metody: onbindViwHolder w CrimeAdapter w CrimeListFargment");
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemViewType(int position) {
            if (mCrimes.get(position).isRequiresPolice()) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public int getItemCount() {

            Log.d(TAG, "Wywołanie metody: getItemCount w CrimeAdapter w CrimeListFargment");
            return mCrimes.size();
        }
    }

}

