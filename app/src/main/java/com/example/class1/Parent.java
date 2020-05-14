package com.example.class1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Parent extends AppCompatActivity {

    CardView attendenceCV,marksCV,feesCV,notificationsCV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        attendenceCV = findViewById(R.id.attendenceCV);
        marksCV = findViewById(R.id.marksCV);
        feesCV = findViewById(R.id.feesCV);
        notificationsCV = findViewById(R.id.viewCV);
        attendenceCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ParentAttendence1.class);
                startActivity(i);
            }
        });
        marksCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ParentMarks1.class);
                startActivity(i);
            }
        });
        feesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Under Development!!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        notificationsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Under Development!!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
