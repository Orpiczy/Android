package com.bignerdranch.android.criminalintent;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import java.util.Date;
import java.util.UUID;


public class CrimeFragment extends Fragment {
    private static final String TAG = "Activity";
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String UPDATED_CRIME_ID = "up_crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mDeleteButton;
    private CheckBox mSolvedCheckBox;
    private Button mTimeButton;

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }

    private void setCrimeUUID(UUID crime_id) {
        Intent data = new Intent();
        data.putExtra(UPDATED_CRIME_ID, crime_id);
        getActivity().setResult(Activity.RESULT_OK, data);
    }

    public static UUID crimeID(Intent data) {
        return (UUID) data.getSerializableExtra(UPDATED_CRIME_ID);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Wywołanie metody: onCreate w CrimeFragment");
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        setCrimeUUID(crimeId);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Wywołanie metody: onCreateView w CrimeFragment");
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //
            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mDeleteButton = (Button) v.findViewById(R.id.delete_crime);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    CrimeLab crimeLab = CrimeLab.get(getActivity());
                    crimeLab.deleteCrime(mCrime);
                    getActivity().finish();
            }
        });
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
    }


    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_crime_button:
                CrimeLab crimeLab = CrimeLab.get(getActivity());
                crimeLab.deleteCrime(mCrime);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
