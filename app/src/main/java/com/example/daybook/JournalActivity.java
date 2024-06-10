package com.example.daybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class JournalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_journal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.journal), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            ImageButton mood = findViewById(R.id.imageMood);
            ImageButton toDo = findViewById(R.id.imageToDo);
            ImageButton calendar = findViewById(R.id.imageCalendar);
            ImageButton settings = findViewById(R.id.imageSettings);

            mood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JournalActivity.this, MoodActivity.class);
                    startActivity(intent);
                }
            });
            toDo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JournalActivity.this, ToDoActivity.class);
                    startActivity(intent);
                }
            });
            calendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JournalActivity.this, CalendarActivity.class);
                    startActivity(intent);
                }
            });
            settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JournalActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
            });
            return insets;
        });
    }
}