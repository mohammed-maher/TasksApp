package com.re_coded.example.android.taskapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.re_coded.example.android.taskapp.data.TaskDBHelper;

import java.util.Calendar;

public class TaskDetails extends AppCompatActivity {
    TaskDBHelper mDbHelper;
    Boolean save = true;
    long date=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        mDbHelper = new TaskDBHelper(this);
        final EditText titleBox = (EditText) findViewById(R.id.title_edit_text);
        final EditText descBox = (EditText) findViewById(R.id.desc_edit_text);
        final CheckBox doneBox = (CheckBox) findViewById(R.id.done_check_box);
        Button saveButton = (Button) findViewById(R.id.save_button);
        Button deleteButton = (Button) findViewById(R.id.delete_button);

        ImageButton pickDate = (ImageButton) findViewById(R.id.pick_date);

        if (getIntent().hasExtra("title") && getIntent().hasExtra("desc") && getIntent().hasExtra("done") && getIntent().hasExtra("id")) {
            save = false;
            titleBox.setText(getIntent().getStringExtra("title"));
            descBox.setText(getIntent().getStringExtra("desc"));
            doneBox.setChecked(getIntent().getBooleanExtra("done", false));
            doneBox.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;
                if (save) {
                    Task task = new Task(0, titleBox.getText().toString(), descBox.getText().toString(), date, false);
                    TaskUtils.insertTask(task, mDbHelper);
                    message = getString(R.string.saved_task_message);
                }else{
                    Task task = new Task(getIntent().getIntExtra("id",0), titleBox.getText().toString(),descBox.getText().toString(),0,doneBox.isChecked());
                    TaskUtils.updateTask(task,mDbHelper);
                    message = getString(R.string.task_updated_message);
                }

                backWithToast(message);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = TaskUtils.deleteAlert(view.getContext(),getIntent().getIntExtra("id",0),mDbHelper);
                dialog.show();
            }
        });

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(TaskDetails.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                               date =  componentTimeToTimestamp(year,monthOfYear,dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }
    public void backWithToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        startActivity(new Intent(TaskDetails.this,MainActivity.class));
    }

    public int componentTimeToTimestamp(int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000L);
    }



}
