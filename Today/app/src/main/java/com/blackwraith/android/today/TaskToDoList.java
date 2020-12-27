package com.blackwraith.android.today;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blackwraith.android.today.database.TaskToDoBaseHelper;
import com.blackwraith.android.today.database.TaskToDoCursorWrapper;
import com.blackwraith.android.today.database.TaskToDoDbSchema.TaskToDoTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskToDoList {
    private static TaskToDoList sTaskList;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private TaskToDoList(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TaskToDoBaseHelper(mContext).getWritableDatabase();
    }

    public static TaskToDoList get(Context context) {
        if (sTaskList == null) {
            sTaskList = new TaskToDoList(context);
        }
        return sTaskList;
    }

    private static ContentValues getContentValues(TaskToDo taskToDo) {
        ContentValues values = new ContentValues();
        values.put(TaskToDoTable.Cols.UUID, taskToDo.getID().toString());
        values.put(TaskToDoTable.Cols.TITLE, taskToDo.getTitle());
        values.put(TaskToDoTable.Cols.DESCRIPTION, taskToDo.getDescription());
        values.put(TaskToDoTable.Cols.CATEGORY, taskToDo.getCategory());
        values.put(TaskToDoTable.Cols.IS_COMPLETED, taskToDo.getCompleted() ? 1 : 0);
        values.put(TaskToDoTable.Cols.CREATION_DATE, taskToDo.getCreationDate().toString());
        values.put(TaskToDoTable.Cols.DEADLINE_DATE, taskToDo.getDeadlineDate().toString());
        values.put(TaskToDoTable.Cols.COMPLETION_DATE, taskToDo.getCompletionDate().toString());
        return values;
    }

    public List<TaskToDo> getTasks() {
        List<TaskToDo> tasks = new ArrayList<>();

        try (TaskToDoCursorWrapper cursor = queryTasks(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTaskToDo());
                cursor.moveToNext();
            }
        }
        return tasks;
    }

    public TaskToDo getTask(UUID id) {
        try (TaskToDoCursorWrapper cursor = queryTasks(TaskToDoTable.Cols.UUID + " = ?"
                , new String[]{id.toString()})) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                return cursor.getTaskToDo();
            } else {
                return null;
            }

        }
    }

    public void addTasks(TaskToDo taskToDo) {
        ContentValues values = getContentValues(taskToDo);
        mDatabase.insert(TaskToDoTable.NAME, null, values);
    }

    public void deleteCrime(TaskToDo taskToDo) {
        mDatabase.delete(TaskToDoTable.NAME, TaskToDoTable.Cols.UUID + " = ?",
                new String[]{taskToDo.getID().toString()});

    }

    public void updateTask(TaskToDo taskToDo) {
        String uuidString = taskToDo.getID().toString();
        ContentValues values = getContentValues(taskToDo);
        mDatabase.update(TaskToDoTable.NAME, values, TaskToDoTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    private TaskToDoCursorWrapper queryTasks(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(TaskToDoTable.NAME, null, whereClause, whereArgs, null, null, null);
        return new TaskToDoCursorWrapper(cursor);
    }


}
