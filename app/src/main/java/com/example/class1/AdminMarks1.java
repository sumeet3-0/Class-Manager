package com.example.class1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class AdminMarks1 extends AppCompatActivity {

    Button b1,b2,b3;
    ProgressBar p;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        p.setVisibility(View.GONE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_marks1);
        b1 = findViewById(R.id.updateA);
        b2 = findViewById(R.id.viewAByName);
        b3 = findViewById(R.id.viewAByDate);

         p=findViewById(R.id.ProgressBar3);
        p.setVisibility(View.GONE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),UpdateMarks.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ViewMByName.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ViewMByDate.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);

            }
        });
    }
}
