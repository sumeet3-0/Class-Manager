package com.example.class1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class UpdateNotification extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    FirebaseDatabase database;
    DatabaseReference reference;
    TextView date;
    Button submit,chooseDate;
    String Date, e1="",e2="" , a=":" , s;
    EditText matter;
    int i =0;
    boolean flag=false , dateSet=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notification);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        submit=findViewById(R.id.submit);
        chooseDate=findViewById(R.id.chooseDate);
        date=findViewById(R.id.date);
        matter = findViewById(R.id.matter);
        findViewById(R.id.chooseDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSet=true;
                showDatePickerDialog();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dateSet)
                {
                    Toast.makeText(getApplicationContext(),"Set the Date First",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    reference.child("Notifications").child(date.getText().toString()).setValue(matter.getText().toString());
                    Toast.makeText(getApplicationContext(),"Recorded Succesfully!!!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),NotificationAdmin1.class);
                    startActivity(i);
                }

            }
        });
    }
    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(month<10) e2="0";
        if(dayOfMonth<10) e1="0";
        s = e1+ Integer.toString(dayOfMonth)+a+e2+Integer.toString(month+1)+a+Integer.toString(year);
        date.setText(s);
        Date=s;
    }
}

