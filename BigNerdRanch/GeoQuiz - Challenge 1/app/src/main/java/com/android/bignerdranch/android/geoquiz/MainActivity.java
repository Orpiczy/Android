package com.android.bignerdranch.android.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private Button mTrueButton;
private Button mFalseButton;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrueButton=(Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Toast correctAnswToast = Toast.makeText(MainActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT);
                        correctAnswToast.setGravity(Gravity.TOP,0,220);
                        correctAnswToast.show();
                }
        });
        mFalseButton= (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Toast wrongAnswToast = Toast.makeText(MainActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT);
                        wrongAnswToast.setGravity(Gravity.TOP,0,220);
                        wrongAnswToast.show();
                }
        });
        }
        }