package com.re_coded.example.android.taskapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo on 11/1/2017.
 */

public class TaskDBHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TaskContract.TaskEntry.TASK_TABLE_NAME + " (" +
                    TaskContract.TaskEntry.TASK_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    TaskContract.TaskEntry.TASK_COLUMN_TITLE + " TEXT," +
                    TaskContract.TaskEntry.TASK_COLUMN_DATE + " INTEGER," +
                    TaskContract.TaskEntry.TASK_COLUMN_DONE + " INTEGER," +
                    TaskContract.TaskEntry.TASK_COLUMN_DESC + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TASK_TABLE_NAME;
    private final static String DATABASE_NAME="tasks.db ";
    private final static int DATABASE_VERSION=1;
    public TaskDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

}
