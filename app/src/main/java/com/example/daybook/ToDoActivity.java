package com.example.daybook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daybook.fortodolist.AddNewTask;
import com.example.daybook.fortodolist.DialogCloseListener;
import com.example.daybook.fortodolist.ToDoAdapter;
import com.example.daybook.fortodolist.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class ToDoActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView tasks;
    private ToDoAdapter tasksAdapter;
    private List<ToDoModel> taskList;
    private FloatingActionButton fab;
    private ToDoDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_to_do);

        taskList = new ArrayList<>();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            ImageButton journal = findViewById(R.id.imageEntry);
            ImageButton mood = findViewById(R.id.imageMood);
            ImageButton calendar = findViewById(R.id.imageCalendar);
            ImageButton credits = findViewById(R.id.imageCredits);

            db = new ToDoDatabaseHelper(this);
            db.openDatabase();

            tasks = findViewById(R.id.taskView);
            fab = findViewById(R.id.addTask);

            tasks.setLayoutManager(new LinearLayoutManager(this));
            tasksAdapter = new ToDoAdapter(db, this);
            tasks.setAdapter(tasksAdapter);

            taskList = db.getALlTasks();
            Collections.reverse(taskList);
            tasksAdapter.setTasks(taskList);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
                }
            });

            journal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ToDoActivity.this, JournalActivity.class);
                    startActivity(intent);
                }
            });
            mood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ToDoActivity.this, MoodActivity.class);
                    startActivity(intent);
                }
            });
            calendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ToDoActivity.this, CalendarActivity.class);
                    startActivity(intent);
                }
            });
            credits.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ToDoActivity.this, CreditsActivity.class);
                    startActivity(intent);
                }
            });
            return insets;
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getALlTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}