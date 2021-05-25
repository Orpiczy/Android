package com.bignerdranch.android.locatr;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PermissionFragment extends DialogFragment {
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    private TextView mTextView;

    public static PermissionFragment newInstance() {
        PermissionFragment fragment = new PermissionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog){
        ((LocatrActivity)getActivity()).askPermission();
        super.onCancel(dialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(getActivity());
        mAlertDialogBuilder.setTitle("Uprawnienia");
        mAlertDialogBuilder.setMessage(getResources().getString(R.string.lack_of_permission_alert));
        mAlertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    Log.i("PermissionsFragment","onClick");
                    dialogInterface.cancel();
                }
            }
        });

        return mAlertDialogBuilder.create();
    }
}
