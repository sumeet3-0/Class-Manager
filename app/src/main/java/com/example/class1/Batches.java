package com.example.class1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class Batches extends AppCompatActivity {
Button createBatch , viewBatch ;
ProgressBar p;
    @Override
    protected void onPostResume() {
        super.onPostResume();
        p.setVisibility(View.GONE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches);
        createBatch = findViewById(R.id.createBatch);
        viewBatch = findViewById(R.id.viewBatch);
        p=findViewById(R.id.ProgressBar4);
        p.setVisibility(View.GONE);


        createBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),CreateBatch.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);
            }
        });
        viewBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ViewList.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);
            }
        });
    }
}
