package com.blackwraith.android.today.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.blackwraith.android.today.database.TaskToDoDbSchema.TaskToDoTable;

public class TaskToDoBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "TaskToDoBase.db";

    public TaskToDoBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //stworzenie bazy
        sqLiteDatabase.execSQL("create table " + TaskToDoTable.NAME+ "(" +
                "_id integer primary key autoincrement, " +
                TaskToDoTable.Cols.UUID + "," +
                TaskToDoTable.Cols.TITLE + "," +
                TaskToDoTable.Cols.DESCRIPTION + "," +
                TaskToDoTable.Cols.CATEGORY + "," +
                TaskToDoTable.Cols.IS_COMPLETED + "," +
                TaskToDoTable.Cols.CREATION_DATE + "," +
                TaskToDoTable.Cols.DEADLINE_DATE + "," +
                TaskToDoTable.Cols.COMPLETION_DATE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
