package com.example.daybook.fortodolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.daybook.R;
import com.example.daybook.ToDoActivity;
import com.example.daybook.ToDoDatabaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> todoList;
    private ToDoActivity activity;
    private ToDoDatabaseHelper db;

    public ToDoAdapter(ToDoDatabaseHelper db, ToDoActivity activity) {
        this.activity = activity;
        this.db= db;

    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);

        return new ViewHolder(itemView);

    }

    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        db.openDatabase();

        ToDoModel item = todoList.get(position);
        holder.task.setText(item.getTasks());
        holder.task.setOnCheckedChangeListener(null);
        holder.task.setChecked(toBoolean(item.getStatus()));

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });

        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int status = isChecked ? 1 : 0;
                item.setStatus(status);
                db.updateStatus(item.getId(), status);
            }
        });
    }

    public int getItemCount() {
        return todoList.size();
    }
    public boolean toBoolean(int n) {
        return n!=0;
    }

    public void setTasks(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return activity;
    }

    public void deleteItem(int position) {
        ToDoModel item = todoList.get(position);
        db.deleteTask(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        ImageButton deleteButton;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
            deleteButton = view.findViewById(R.id.deleteButton);
        }
    }
}