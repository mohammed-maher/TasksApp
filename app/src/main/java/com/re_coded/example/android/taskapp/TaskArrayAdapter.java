package com.re_coded.example.android.taskapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lenovo on 11/1/2017.
 */

public class TaskArrayAdapter extends ArrayAdapter<Task>{
    private int layoutResource;
    public TaskArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Task> objects) {
        super(context, resource, objects);
        this.layoutResource = resource;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TasksHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(layoutResource,parent,false);
            holder = new TasksHolder();
            holder.title = convertView.findViewById(R.id.task_title);
            holder.desc = convertView.findViewById(R.id.task_desc);
            holder.date = convertView.findViewById(R.id.task_date);
            holder.done = convertView.findViewById(R.id.task_done);
            holder.container = convertView.findViewById(R.id.container);
            holder.state = convertView.findViewById(R.id.state_text);
            convertView.setTag(holder);
        }else{
            holder = (TasksHolder) convertView.getTag();
        }

        final Task task = getItem(position);
        holder.title.setText(task.getTitle());
        holder.desc.setText(task.getDescription());
        Date date = new Date(task.getDate() *1000L);
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd");
        holder.date.setText(jdf.format(date));
        holder.done.setChecked(task.getDone());
        if(DateUtils.isToday(task.getDate() * 1000L) && task.getDone()==false){
            holder.state.setTextColor(Color.parseColor("#ffa500"));
            holder.state.setText(R.string.due_today_text);
            holder.state.setVisibility(View.VISIBLE);
        }else if(date.before(new Date()) && task.getDone()==false){
            holder.state.setTextColor(Color.parseColor("#ff0000"));
            holder.state.setText(R.string.over_due_text);
            holder.state.setVisibility(View.VISIBLE);
        }else if(task.getDone()==false) {
            holder.state.setTextColor(Color.parseColor("#98fb98"));
            long diff = date.getTime() - new Date().getTime();
            long days =TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            holder.state.setText("Due in "+days+ " days");
            holder.state.setVisibility(View.VISIBLE);
        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Intent intent = new Intent(view.getContext(),TaskDetails.class);
                        intent.putExtra("id",task.getId());
                        intent.putExtra("title",task.getTitle());
                        intent.putExtra("desc",task.getDescription());
                        intent.putExtra("done",task.getDone());
                        getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    static class TasksHolder{
        TextView title;
        TextView desc;
        TextView date;
        CheckBox done;
        TextView state;
        LinearLayout container;
    }
}
