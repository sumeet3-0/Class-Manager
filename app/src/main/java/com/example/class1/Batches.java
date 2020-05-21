package com.example.class1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Batches extends AppCompatActivity {
Button createBatch , viewBatch ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches);
        createBatch = findViewById(R.id.createBatch);
        viewBatch = findViewById(R.id.viewBatch);
        createBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),CreateBatch.class);
                startActivity(i);
            }
        });
        viewBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ViewList.class);
                startActivity(i);
            }
        });
    }
}
