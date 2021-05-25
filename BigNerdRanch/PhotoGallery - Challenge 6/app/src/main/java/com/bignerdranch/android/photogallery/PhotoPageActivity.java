package com.bignerdranch.android.photogallery;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;


public class PhotoPageActivity extends SingleFragmentActivity implements PhotoPageFragment.onDataPass {
    WebView mWebView;
    private static String TAG = "PhotoPageActivity";

    public static Intent newIntent(Context context, Uri photoPageUri) {
        Intent i = new Intent(context, PhotoPageActivity.class);
        i.setData(photoPageUri);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return PhotoPageFragment.newInstance(getIntent().getData());
    }

    @Override
    public void onBackPressed() {
//        solution 1
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof PhotoPageFragment) {
            if (((PhotoPageFragment)fragment).canGoBack()){
                ((PhotoPageFragment)fragment).goBack();
            } else {
                super.onBackPressed();
            }
        }
    }

//        solution 2
//        if(mWebView.canGoBack()){
//            Log.i(TAG,"I can go back so I'm going back");
//            mWebView.goBack();
//        }else {
//            Log.i(TAG, "There is no way back so -> welcome back main activity");
//            super.onBackPressed();
//        }
//   }

    @Override
    public void onDataPass(View view) {
        //it works
        mWebView = (WebView) view;
    }
}
