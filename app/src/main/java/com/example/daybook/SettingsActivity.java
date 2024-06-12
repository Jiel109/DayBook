package com.example.daybook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    Button clickme;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    TextView text6;
    TextView text7;
    TextView text8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            ImageButton back = findViewById(R.id.imageBack);
            clickme = findViewById(R.id.ClickMe);
            text1 = findViewById(R.id.T1);
            text2 = findViewById(R.id.T2);
            text3 = findViewById(R.id.T3);
            text4 = findViewById(R.id.T4);
            text5 = findViewById(R.id.T5);
            text6 = findViewById(R.id.T6);
            text7 = findViewById(R.id.T7);
            text8 = findViewById(R.id.T8);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            clickme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text1.setText("Everything");
                    text2.setText("that");
                    text3.setText("happened");
                    text4.setText("happens");
                    text5.setText("for");
                    text6.setText("a");
                    text7.setText("reason");
                    text8.setText("remember");
                }
            });
            return insets;
        });
    }
}