package com.example.class1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Admin extends AppCompatActivity {

    CardView attendenceCV,marksCV,feesCV,viewCV,notiCV,reqCV;
    ProgressBar p;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        p.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        attendenceCV = findViewById(R.id.attendenceCV);
        marksCV = findViewById(R.id.marksCV);
        feesCV = findViewById(R.id.feesCV);
        viewCV = findViewById(R.id.viewCV);
        notiCV = findViewById(R.id.notiCV);
        reqCV = findViewById(R.id.reqCV);
        p = findViewById(R.id.ProgressBar1);
        p.setVisibility(View.GONE);

        attendenceCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),AdminAttendence1.class);
            p.setVisibility(View.VISIBLE);
            startActivity(i);
            }
        });
        marksCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AdminMarks1.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);
            }
        });
        feesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Fees.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);
            }
        });
        viewCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Batches.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);
            }

        });
        notiCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),NotificationAdmin1.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);
            }
        });
        reqCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Discussion.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);
            }

        });

    }
}
