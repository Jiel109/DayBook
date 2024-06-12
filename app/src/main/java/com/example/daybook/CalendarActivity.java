package com.example.daybook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class CalendarActivity extends AppCompatActivity {

    private CalendarDatabaseHelper db;
    private EditText text;
    private TextView textView;
    private CalendarView calendar;
    private String selectDate;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calendar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            text = findViewById(R.id.editTextEvent);
            textView = findViewById(R.id.textViewEvent);
            calendar = findViewById(R.id.calendarView);
            ImageButton journal = findViewById(R.id.imageEntry);
            ImageButton mood = findViewById(R.id.imageMood);
            ImageButton toDo = findViewById(R.id.imageToDo);
            ImageButton settings = findViewById(R.id.imageSettings);

            calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    selectDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);
                    ReadEvent(view);
                }
            });
            try {
                db = new CalendarDatabaseHelper(this, "CalendarDatabase", null, 1);
                sqLiteDatabase = db.getWritableDatabase();
                sqLiteDatabase.execSQL("CREATE TABLE EventCalendar(Date TEXT, Event TEXT)");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            journal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CalendarActivity.this, JournalActivity.class);
                    startActivity(intent);
                }
            });
            mood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CalendarActivity.this, MoodActivity.class);
                    startActivity(intent);
                }
            });
            toDo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CalendarActivity.this, ToDoActivity.class);
                    startActivity(intent);
                }
            });
            settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CalendarActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
            });
            return insets;
        });
    }

    public void AddEvent(View view) {
        String eventText = text.getText().toString().trim();
        if (eventText.isEmpty()) {
            Toast.makeText(this, "Event cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues cv = new ContentValues();
        cv.put("Date", selectDate);
        cv.put("Event", eventText);
        int updateRows = sqLiteDatabase.update("EventCalendar", cv, "Date=?", new String[]{selectDate});
        if (updateRows == 0) {
            sqLiteDatabase.insert("EventCalendar", null, cv);
        }
        Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
        text.setText("");
        ReadEvent(view);
    }

    public void ClearEvent(View view) {
        int deletedRows = sqLiteDatabase.delete("EventCalendar", "Date=?", new String[]{selectDate});
        if (deletedRows > 0) {
            Toast.makeText(this, "Event cleared successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No event to clear", Toast.LENGTH_SHORT).show();
        }
        textView.setText("");
        text.setText("");
    }

    public void ReadEvent(View view) {
        String query = "Select Event from EventCalendar where Date = ?";
        try (Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{selectDate})) {
            if (cursor.moveToFirst()) {
                textView.setText(cursor.getString(0));
            } else {
                textView.setText("");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            textView.setText("");
        }
        text.setText("");
    }

}