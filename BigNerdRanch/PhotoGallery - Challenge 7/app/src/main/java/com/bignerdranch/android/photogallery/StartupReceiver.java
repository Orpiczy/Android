package com.bignerdranch.android.photogallery;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartupReceiver extends android.content.BroadcastReceiver {
    private static final String TAG = "StartupReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Otrzymano intencję rozgłoszenia: "+intent.getAction());
    }
}
