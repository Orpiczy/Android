package com.bignerdranch.android.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class SunsetFragment extends Fragment {
    private View mSceneView;
    private View mBrightSunView;
    private View mDimSunView;
    private View mSkyView;

    //color's of sky
    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    //color's of sun
    private int mBrightSunColor;
    private int mDimSunColor;

    //current position
    private float mCurrentSunPos;


    private boolean isSunRisen;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);

//        Resources resources = getResources();
//        resources.getColor(R.color.blue_sky);

        mBlueSkyColor = ContextCompat.getColor(getContext(), R.color.blue_sky);
        mSunsetSkyColor = ContextCompat.getColor(getContext(), R.color.sunset_sky);
        mNightSkyColor = ContextCompat.getColor(getContext(), R.color.night_sky);

        mBrightSunColor = ContextCompat.getColor(getContext(), R.color.bright_sun);
        mDimSunColor = ContextCompat.getColor(getContext(), R.color.dim_sun);



        mSceneView = view;
        mBrightSunView = view.findViewById(R.id.bright_sun);
        mDimSunView = view.findViewById(R.id.dim_sun);
        mSkyView = view.findViewById(R.id.sky);

        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });

        isSunRisen = true;
        mCurrentSunPos = 351;  //wartość początkowa odczytana mSunView.getTop zwraca z jakiegoś powodu 0.0
        Log.i("Animation","Sun pos "+mCurrentSunPos);

        return view;
    }

    private void startAnimation() {
        float sunYStart = mBrightSunView.getTop();
        Log.i("Animation","2 Sun pos "+mCurrentSunPos);
        float sunYEnd = (float) (mSkyView.getBottom());

        Log.i("Animation","Is Risen = "+isSunRisen+"\n1) Y start = "+sunYStart+" Bottom Y = "+sunYEnd);
        if (isSunRisen) {
            sunYStart = mCurrentSunPos;
        } else {
            sunYEnd = mCurrentSunPos;
        }
        Log.i("Animation","2) Y start = "+sunYStart+" Bottom Y = "+sunYEnd);

//        - 0.5 * mSunView.getHeight()

        ObjectAnimator BrightSunHeightAnimator = ObjectAnimator.ofFloat(mBrightSunView, "y", sunYStart, sunYEnd).setDuration(3000);
        BrightSunHeightAnimator.setInterpolator(new DecelerateInterpolator());
        BrightSunHeightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentSunPos = (float) valueAnimator.getAnimatedValue();
            }
        });
        ObjectAnimator DimSunHeightAnimator = ObjectAnimator.ofFloat(mDimSunView, "y", sunYStart, sunYEnd).setDuration(3000);
        DimSunHeightAnimator.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor, mNightSkyColor).setDuration(2900);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        ValueAnimator sunColorAnimator = ValueAnimator.ofFloat(1, 0).setDuration(3000);
        sunColorAnimator.setInterpolator(new DecelerateInterpolator());
        sunColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBrightSunView.setAlpha((float) valueAnimator.getAnimatedValue());
            }
        });


        AnimatorSet animatorSunSet = new AnimatorSet();
        animatorSunSet
                .play(DimSunHeightAnimator)
                .with(BrightSunHeightAnimator)
                .with(sunsetSkyAnimator)
                .with(sunColorAnimator);
        if (isSunRisen) {
            isSunRisen = false;
            animatorSunSet.start();

        } else {
            isSunRisen = true;
            animatorSunSet.reverse();
        }


//        AnimatorSet animatorSunRise = new AnimatorSet();
//        animatorSunRise
//                .play(nightSkyAnimator.reverse())
//                .with(sunsetSkyAnimator)
//                .before(nightSkyAnimator);
//        animatorSunRise.start();
    }
}
