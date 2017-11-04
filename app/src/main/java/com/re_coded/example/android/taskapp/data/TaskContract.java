package com.re_coded.example.android.taskapp.data;

import android.provider.BaseColumns;

/**
 * Created by Lenovo on 11/1/2017.
 */

public final class TaskContract {
    public final static class TaskEntry implements BaseColumns{
        public final static String TASK_TABLE_NAME="tasks";
        public final static String TASK_COLUMN_ID="_id";
        public final static String TASK_COLUMN_TITLE="title";
        public final static String TASK_COLUMN_DESC="description";
        public final static String TASK_COLUMN_DATE="date";
        public final static String TASK_COLUMN_DONE="done";

    }
}
