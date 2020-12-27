package com.blackwraith.android.today.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.blackwraith.android.today.TaskToDo;
import com.blackwraith.android.today.database.TaskToDoDbSchema.TaskToDoTable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.UUID;

public class TaskToDoCursorWrapper extends CursorWrapper {

    public TaskToDoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public TaskToDo getTaskToDo() {
        String uuidString = getString(getColumnIndex(TaskToDoTable.Cols.UUID));
        String title = getString(getColumnIndex(TaskToDoTable.Cols.TITLE));
        String description = getString(getColumnIndex(TaskToDoTable.Cols.DESCRIPTION));
        int category = getInt(getColumnIndex(TaskToDoTable.Cols.CATEGORY));
        int isCompleted = getInt(getColumnIndex(TaskToDoTable.Cols.IS_COMPLETED));
        long creationDate = getLong(getColumnIndex(TaskToDoTable.Cols.CREATION_DATE));
        long deadlineDate = getLong(getColumnIndex(TaskToDoTable.Cols.DEADLINE_DATE));
        long completionDate = getLong(getColumnIndex(TaskToDoTable.Cols.COMPLETION_DATE));

        TaskToDo taskToDo = new TaskToDo(UUID.fromString(uuidString));
        taskToDo.setTitle(title);
        taskToDo.setDescription(description);
        taskToDo.setCategory(category);
        taskToDo.setCompleted(isCompleted == 1); // rzutowanie na boolean
        taskToDo.setCreationDate( LocalDateTime.ofInstant(Instant.ofEpochMilli(creationDate), TimeZone.getDefault().toZoneId()));
        taskToDo.setDeadlineDate( LocalDateTime.ofInstant(Instant.ofEpochMilli(deadlineDate), TimeZone.getDefault().toZoneId()));
        taskToDo.setCompletionDate( LocalDateTime.ofInstant(Instant.ofEpochMilli(completionDate), TimeZone.getDefault().toZoneId()));

        return taskToDo;
    }
}