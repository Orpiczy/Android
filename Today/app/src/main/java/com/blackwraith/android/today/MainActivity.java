package com.blackwraith.android.today;

import android.util.Log;

import androidx.fragment.app.Fragment;

import java.time.LocalDateTime;
import java.util.List;

public class MainActivity extends SingleFragmentActivity implements StartUpFragment.Callbacks {
    private static final String TAG = "MainActivity";


    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "wywołanie metody : createFragment w MainActivity");
        return new StartUpFragment();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onTaskSelected(TaskToDo task) {
        // TO DO
    }

    @Override
    public void onTaskCompleted(TaskToDo task) {
        task.setCompleted(true);
        task.setCompletionDate(LocalDateTime.now());
        StartUpFragment listFragment = (StartUpFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container_act_main);
        listFragment.updateUI();
    }

    @Override
    public void onTaskDeleted(TaskToDo task) {
        int pos = -1;
        List<TaskToDo> tasks = TaskToDoList.get(this).getTasks();
        for (int i = 0; i < tasks.size();i++){
            if(tasks.get(i).getID().equals(task.getID())){
                pos = i;
                break;
            }
        }
        StartUpFragment listFragment = (StartUpFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container_act_main);
        TaskToDoList.get(this).deleteTask(task);
        listFragment.updateUI();
    }


}