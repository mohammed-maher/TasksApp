package com.re_coded.example.android.taskapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.re_coded.example.android.taskapp.data.TaskContract;
import com.re_coded.example.android.taskapp.data.TaskDBHelper;

import java.util.ArrayList;

/**
 * Created by Lenovo on 11/4/2017.
 */

public class TaskUtils {
    public static ArrayList<Task> extractTasks(TaskDBHelper mDbHelper) {
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase sd = mDbHelper.getReadableDatabase();
        Cursor c = sd.query(TaskContract.TaskEntry.TASK_TABLE_NAME, null, null, null, null, null, null);
        while (c.moveToNext()) {
            tasks.add(new Task(c.getInt(c.getColumnIndex(TaskContract.TaskEntry.TASK_COLUMN_ID)),
                    c.getString(c.getColumnIndex(TaskContract.TaskEntry.TASK_COLUMN_TITLE)),
                    c.getString(c.getColumnIndex(TaskContract.TaskEntry.TASK_COLUMN_DESC)),
                    c.getLong(c.getColumnIndex(TaskContract.TaskEntry.TASK_COLUMN_DATE)),
                    c.getInt(c.getColumnIndex(TaskContract.TaskEntry.TASK_COLUMN_DONE)) == 1
            ));
        }
        return tasks;
    }

    public static long insertTask(Task task, TaskDBHelper mDbHelper) {
        SQLiteDatabase sd = mDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TaskContract.TaskEntry.TASK_COLUMN_TITLE, task.getTitle());
        cv.put(TaskContract.TaskEntry.TASK_COLUMN_DATE, task.getDate());
        cv.put(TaskContract.TaskEntry.TASK_COLUMN_DESC, task.getDescription());
        cv.put(TaskContract.TaskEntry.TASK_COLUMN_DONE, task.getDone());
        return sd.insert(TaskContract.TaskEntry.TASK_TABLE_NAME, null, cv);
    }

    public static boolean updateTask(Task task, TaskDBHelper mDbHelper) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.TASK_COLUMN_TITLE, task.getTitle());
        values.put(TaskContract.TaskEntry.TASK_COLUMN_DESC, task.getDescription());
        values.put(TaskContract.TaskEntry.TASK_COLUMN_DONE, task.getDone());

        String selection =  TaskContract.TaskEntry.TASK_COLUMN_ID + " = ?";
        String[] selectionArgs = {task.getId()+""};

        int count = db.update(
                TaskContract.TaskEntry.TASK_TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count != -1;
    }

    public static void deleteTask(int id,TaskDBHelper mDbHelper){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selection =  TaskContract.TaskEntry.TASK_COLUMN_ID + " = ?";
        String[] selectionArgs = {id+""};
        db.delete(TaskContract.TaskEntry.TASK_TABLE_NAME, selection, selectionArgs);
    }

    public static AlertDialog deleteAlert(final Context context, final int id, final TaskDBHelper mDbHelper)
    {
        AlertDialog deleteItemDialog =new AlertDialog.Builder(context)
                //set message, title, and icon
                .setTitle(R.string.delete_dialouge_title)
                .setMessage(R.string.delete_dialouge_message)
                .setIcon(R.drawable.delete)

                .setPositiveButton(R.string.delete_dialouge_title, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                       deleteTask(id,mDbHelper);
                        Toast.makeText(context, R.string.task_deleted_message,Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context,MainActivity.class));
                    }

                })



                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return deleteItemDialog;

    }

}
