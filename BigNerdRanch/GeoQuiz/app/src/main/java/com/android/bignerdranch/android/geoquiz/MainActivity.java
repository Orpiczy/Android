package com.android.bignerdranch.android.geoquiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "key_index";
    private static final String ISCHEATER_INDEX = "isCheater_index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private Map<Integer, Integer> answers = new HashMap<>();
    private boolean mIsCheater;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mCheatButton;

    private TextView mQuestionTextView;

    private int mCurrentInd = 0;
    private Question[] mQuestionBank = {new Question(R.string.question_australi, true),
            new Question(R.string.question_oceans, true), new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false), new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            } else {
                mIsCheater = CheatActivity.wasAnswerShown(data);
                if (!answers.containsKey(mCurrentInd)) {
                    answers.put(mCurrentInd, -2);
                }
            }
        }
    }

    private void updateQustion() {
        mQuestionTextView.setText(mQuestionBank[mCurrentInd].getTextResId());
    }

    private void checkAnswer(boolean userChoice) {
        int mesId = 0;
        if (answers.containsKey(mCurrentInd)) {
            mesId = R.string.answered_toast;
            if (answers.get(mCurrentInd) == -2) {
                mesId = R.string.judgement_toast;
                answers.put(mCurrentInd,-1);
            }
        } else {

            if (userChoice == mQuestionBank[mCurrentInd].isAnswerTrue()) {
                mesId = R.string.correct_toast;
                answers.put(mCurrentInd, 1);
            } else {
                mesId = R.string.incorrect_toast;
                answers.put(mCurrentInd, 0);
            }
        }
        Toast.makeText(this, mesId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentInd = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(ISCHEATER_INDEX, true);
        }
        Log.d(TAG, "Wywołanie metody: onCreate(Bundle)");
        setContentView(R.layout.activity_main);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQustion();

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentInd = (mCurrentInd + 1) % mQuestionBank.length;
                updateQustion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentInd = (mCurrentInd + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQustion();
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsCheater = false;
                if (mCurrentInd != 0) {
                    mCurrentInd = (mCurrentInd - 1) % mQuestionBank.length;
                } else {
                    mCurrentInd = mQuestionBank.length - 1;
                }
                updateQustion();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = mQuestionBank[mCurrentInd].isAnswerTrue();
                startActivityForResult(CheatActivity.newIntent(MainActivity.this, answerIsTrue), REQUEST_CODE_CHEAT);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Wywołanie metody: onStart(Bundle)");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Wywołanie metody: onResume(Bundle)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Wywołanie metody: onPause(Bundle)");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(KEY_INDEX, "onSaveInstance");
        savedInstanceState.putInt(KEY_INDEX, mCurrentInd);
        savedInstanceState.putBoolean(ISCHEATER_INDEX, mIsCheater);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Wywołanie metody: onStop(Bundle)");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Wywołanie metody: onDestroy(Bundle)");
    }


}