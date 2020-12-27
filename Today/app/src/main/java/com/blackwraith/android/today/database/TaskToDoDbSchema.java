package com.blackwraith.android.today.database;

public class TaskToDoDbSchema {
    public static final class TaskToDoTable {
        public static final String NAME = "Tasks";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String CATEGORY = "category";
            public static final String IS_COMPLETED = "is_completed";
            public static final String CREATION_DATE = "creation_date";
            public static final String DEADLINE_DATE = "deadline_date";
            public static final String COMPLETION_DATE = "completion_date";
        }
    }
}
