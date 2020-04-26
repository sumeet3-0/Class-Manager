package com.example.class1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Admin extends AppCompatActivity {

    CardView attendenceCV,marksCV,feesCV,viewCV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        attendenceCV = findViewById(R.id.attendenceCV);
        marksCV = findViewById(R.id.marksCV);
        feesCV = findViewById(R.id.feesCV);
        viewCV = findViewById(R.id.viewCV);
        attendenceCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),AdminAttendence1.class);
            startActivity(i);
            }
        });
        marksCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AdminMarks1.class);
                startActivity(i);
            }
        });
        feesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AdminMarks1.class);
                startActivity(i);
            }
        });
        viewCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ViewList.class);
                startActivity(i);
            }
        });
    }
}
