package com.example.class1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class ParentFees extends AppCompatActivity {

    Button payFees,viewFeeStatus;
    ProgressBar p;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        p.setVisibility(View.GONE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_fees);
        payFees = findViewById(R.id.payFees);
        p=findViewById(R.id.ProgressBar8);
        p.setVisibility(View.GONE);


        payFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),PayFees.class);
                p.setVisibility(View.VISIBLE);
                startActivity(i);
            }
        });


    }
}
