package com.android.bignerdranch.android.geoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collection;

public class CheatActivity extends AppCompatActivity {

    private TextView mAnswerTextView;
    private TextView mCheatsLeftTextView;
    private Button mShowAnswerButton;
    private static final String EXTRA_ANSWER_IS_TRUE = "com.android.bignerdranch.android.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.android.bignerdranch.android.answer_shown";
    private static final String EXTRA_USED_CHEATS = "\"com.android.bignerdranch.android.used_cheats";
    private static final String TAG = "CheatActivity";
    private static final String SHOWN_INDEX = "shown_index";
    private static final int AVAILABLE_CHEATS = 3;
    private boolean mAnswerIsTrue;
    private int mCheatsLeft;
    private boolean mAnswerShown=false;
    public static Intent newIntent(Context packageContext, boolean answerIsTrue, Collection<Integer> answers){
        int occurrences=0;
        Intent intent=new Intent(packageContext,CheatActivity.class);
        for(Integer ans:answers){
            if(ans==-2||ans==-1){
                occurrences++;
            }
        }
        intent.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        intent.putExtra(EXTRA_USED_CHEATS,occurrences);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        mCheatsLeft= AVAILABLE_CHEATS - getIntent().getIntExtra(EXTRA_USED_CHEATS,0);

        mAnswerTextView=(TextView) findViewById(R.id.answer_text_view);

        mShowAnswerButton=(Button) findViewById(R.id.show_answer_button);

        if(savedInstanceState!=null){
            mAnswerShown=savedInstanceState.getBoolean(SHOWN_INDEX);
            if(mAnswerShown){
                setAnswerShownResult(true);
                if(mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
            }
        }

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheatsLeft>0) {
                    if (mAnswerIsTrue) {
                        mAnswerTextView.setText(R.string.true_button);
                    } else {
                        mAnswerTextView.setText(R.string.false_button);
                    }
                    mAnswerShown=true;
                    setAnswerShownResult(true);

                }else {
                    mAnswerTextView.setText(R.string.all_cheats_used);
                    mAnswerShown=false;
                    setAnswerShownResult(false);
                }

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, radius/2);
                    anim.addListener(new AnimatorListenerAdapter() {
                                         @Override
                                         public void onAnimationEnd(Animator animation) {
                                             super.onAnimationEnd(animation);
                                             mShowAnswerButton.setVisibility(View.INVISIBLE);
                                         }
                                     }
                    );
                    anim.start();
                }else {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        mCheatsLeftTextView = (TextView) findViewById(R.id.cheat_number_text_view);
        mCheatsLeftTextView.setText(String.format("Dostępne %d podpowiedzi",mCheatsLeft));

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(SHOWN_INDEX,mAnswerShown);
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK,data);
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"Wywołanie metody: onStart(Bundle)");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Wywołanie metody: onResume(Bundle)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"Wywołanie metody: onPause(Bundle)");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"Wywołanie metody: onStop(Bundle)");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Wywołanie metody: onDestroy(Bundle)");
    }

}