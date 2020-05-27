package com.example.class1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Parent extends AppCompatActivity {

    CardView attendenceCV,marksCV,feesCV,notificationsCV,chatCV;
    ProgressBar p;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        p.setVisibility(View.GONE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        attendenceCV = findViewById(R.id.attendenceCV);
        marksCV = findViewById(R.id.marksCV);
        feesCV = findViewById(R.id.feesCV);
        chatCV = findViewById(R.id.chatCV);
        notificationsCV = findViewById(R.id.notiCV);
        p=findViewById(R.id.ProgressBar7);
        p.setVisibility(View.GONE);
        attendenceCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ParentAttendence1.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);
            }
        });
        marksCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ParentMarks1.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);
            }
        });
        feesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ParentFees.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);
            }
        });
        notificationsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ParentNotification.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);
            }
        });
        chatCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Discussion.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);
            }
        });

    }
}
