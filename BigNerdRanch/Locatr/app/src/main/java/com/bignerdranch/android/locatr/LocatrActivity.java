package com.bignerdranch.android.locatr;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class LocatrActivity extends SingleFragmentActivity {
    private static final int REQUEST_ERROR = 0;
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    @Override
    protected Fragment createFragment() {
        return LocatrFragment.newInstance();
    }

    public void askPermission() {
        requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);
        Log.i("Communication","ErrorCode = "+errorCode);
        if (errorCode != ConnectionResult.SUCCESS){
            Dialog errorDialog = apiAvailability.getErrorDialog(this, errorCode, REQUEST_ERROR, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    finish();
                }
            });
            errorDialog.show();
        }
    }
}