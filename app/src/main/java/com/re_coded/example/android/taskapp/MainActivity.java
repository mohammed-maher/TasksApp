package com.re_coded.example.android.taskapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.re_coded.example.android.taskapp.data.TaskDBHelper;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    TaskDBHelper mDbHelper;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new TaskDBHelper(this);
        listView =(ListView) findViewById(R.id.tasks_list);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.add_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,TaskDetails.class));
            }
        });

    }

    @Override
    protected void onResume() {
        setListViewItems();
        super.onResume();
    }

    public void setListViewItems(){
        TaskArrayAdapter adapter  = new TaskArrayAdapter(this,R.layout.task_list_item,TaskUtils.extractTasks(mDbHelper));
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
