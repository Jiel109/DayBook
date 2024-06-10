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


public class ToDoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_to_do);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            ImageButton journal = findViewById(R.id.imageEntry);
            ImageButton mood = findViewById(R.id.imageMood);
            ImageButton calendar = findViewById(R.id.imageCalendar);
            ImageButton settings = findViewById(R.id.imageSettings);

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
            settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ToDoActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
            });
            return insets;
        });
    }
}