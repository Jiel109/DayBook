package com.example.daybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MoodActivity extends AppCompatActivity {
    private TextView mood;
    private ImageView imageMood;
    private Spinner spinnerMood;
    private MoodDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mood);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mood), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            ImageButton journal = findViewById(R.id.imageEntry);
            ImageButton toDo = findViewById(R.id.imageToDo);
            ImageButton calendar = findViewById(R.id.imageCalendar);
            ImageButton credits = findViewById(R.id.imageCredits);
            mood = findViewById(R.id.textViewMoodDisplay);
            imageMood = findViewById(R.id.imageViewMood);
            spinnerMood = findViewById(R.id.spinnerMood);
            Button confirm = findViewById(R.id.buttonConfirm);
            db = new MoodDatabaseHelper(this);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.moodOption,
                    android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMood.setAdapter(adapter);

            String latestMood = db.getMood();
            if (latestMood != null) {
                mood.setText(latestMood);
                updateMoodImage(latestMood);
            }

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectMood = spinnerMood.getSelectedItem().toString();
                    if (selectMood.equals("------")) {
                        Toast.makeText(MoodActivity.this, "Please select a mood.", Toast.LENGTH_SHORT).show();
                    } else {
                        mood.setText(selectMood);
                        updateMoodImage(selectMood);
                        saveMood(selectMood);
                    }
                }
            });

            journal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoodActivity.this, JournalActivity.class);
                    startActivity(intent);
                }
            });
            toDo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoodActivity.this, ToDoActivity.class);
                    startActivity(intent);
                }
            });
            calendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoodActivity.this, CalendarActivity.class);
                    startActivity(intent);
                }
            });
            credits.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoodActivity.this, CreditsActivity.class);
                    startActivity(intent);
                }
            });

            return insets;
        });
    }

    private void updateMoodImage(String moodOption) {
        int imageRes = R.drawable.transparent;
        switch (moodOption) {
            case "Excited":
                imageRes = R.drawable.excited;
                break;
            case "Happy":
                imageRes = R.drawable.happy;
                break;
            case "Bored":
                imageRes = R.drawable.bored;
                break;
            case "Sad":
                imageRes = R.drawable.sad;
                break;
            case "Angry":
                imageRes = R.drawable.angry;
                break;
            default:
                break;
        }
        imageMood.setImageResource(imageRes);
    }

    private void saveMood(String mood) {
        db.addMood(mood);
        Toast.makeText(this, "Mood saved", Toast.LENGTH_SHORT).show();
    }
}