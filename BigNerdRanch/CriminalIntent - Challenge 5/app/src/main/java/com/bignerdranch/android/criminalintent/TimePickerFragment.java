package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment {
    private TimePicker mTimePicker;
    private static final String ARG_TIME = "time";
    public static final String EXTRA_TIME = "com.bignerdranch.anroid.ciminalintent.time";

    public static TimePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME,date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Date date = (Date) getArguments().getSerializable(ARG_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);


        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time,null);
        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_picker);
        mTimePicker.setHour(hour);
        mTimePicker.setMinute(minute);

        return new AlertDialog.Builder(getActivity()).setTitle(R.string.time_picker_title).setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int onClickHour = mTimePicker.getHour();
                        int onClickMinute = mTimePicker.getMinute();
                        Date date = new GregorianCalendar(year,month,day,onClickHour,onClickMinute).getTime();
                        sendResult(Activity.RESULT_OK,date);
                    }
                }).create();
    }

    private void sendResult(int resultCode, Date date) {
        if(getTargetFragment()!=null){
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TIME,date);

            getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
        }
    }
}
