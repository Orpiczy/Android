package com.android.bignerdranch.android.geoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private static final String KEY_INDEX = "index";
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;

    private TextView mQuestionTextView;

    private int mCurrentInd = 0;
    private Question[] mQuestionBank = {new Question(R.string.question_australi, true),
            new Question(R.string.question_oceans, true), new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false), new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private Map<Integer, Integer> answers = new HashMap<>();

    private void updateQuestion() {
        mQuestionTextView.setText(mQuestionBank[mCurrentInd].getTextResId());
    }

    private void checkAnswer(boolean userChoice) {
        int mesId = 0;
        if (!answers.containsKey(mCurrentInd)) {
            answers.put(mCurrentInd, (userChoice==mQuestionBank[mCurrentInd].isAnswerTrue())?1:0);
        } else {
            mesId = R.string.answered_toast;
        }
        if (mesId == 0) {
            if (userChoice == mQuestionBank[mCurrentInd].isAnswerTrue()) {
                mesId = R.string.correct_toast;
            } else {
                mesId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, mesId, Toast.LENGTH_SHORT).show();
        if(answers.size()==mQuestionBank.length){
            float rightAnswers=answers.values().stream().mapToInt(Integer::intValue).sum();
            String score= String.format("%.2f", rightAnswers/ mQuestionBank.length*100)+ " %";
            mesId=R.string.score_toast;
            Toast.makeText(this,mesId,Toast.LENGTH_SHORT).show();
            Toast.makeText(this,score,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentInd = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        Log.d(TAG, "Wywołanie metody: onCreate(Bundle)");
        setContentView(R.layout.activity_main);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentInd = (mCurrentInd + 1) % mQuestionBank.length;
                updateQuestion();
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
                updateQuestion();
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentInd != 0) {
                    mCurrentInd = (mCurrentInd - 1) % mQuestionBank.length;
                } else {
                    mCurrentInd = mQuestionBank.length - 1;
                }
                updateQuestion();
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