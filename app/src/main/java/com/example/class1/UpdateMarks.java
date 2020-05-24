package com.example.class1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class UpdateMarks extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ArrayList<String> usersList = new ArrayList<String>();
    ArrayList<String> batchList = new ArrayList<String>();
    FirebaseDatabase database;
    DatabaseReference reference,refByName,refByDate;
    TextView box,date;
    Button next,prev,submit,chooseDate;
    String Date;
    EditText marks,outof;
    Spinner chooseBatch ;
    ArrayAdapter<String> arrayAdapter;
    String name , b ="Batch" , m = "Mapp";
    int i =0;
    boolean flag=false , dateSet=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_marks);
        box = findViewById(R.id.nameField);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        next=findViewById(R.id.next);
        submit=findViewById(R.id.submit);
        prev=findViewById(R.id.prev);
        chooseDate=findViewById(R.id.chooseDate);
        date=findViewById(R.id.date);
        marks=findViewById(R.id.marks);
        outof=findViewById(R.id.outof);
        chooseBatch = findViewById(R.id.chooseBatch);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String s = postSnapshot.getKey();
                    if (s.equals(b) | s.equals(m))
                        continue;
                    batchList.add(s);
                }
                arrayAdapter =
                        new ArrayAdapter<>(UpdateMarks.this, R.layout.list, batchList);
                chooseBatch.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("unique", "loadPost:onCancelled", databaseError.toException());
            }
        });
        chooseBatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name = chooseBatch.getSelectedItem().toString();
                reference.child(name).child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        usersList.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String s = postSnapshot.getKey();
                            usersList.add(s);
                        }
                        box.setText(usersList.get(0));
                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(TextUtils.isEmpty(marks.getText().toString()) || TextUtils.isEmpty(outof.getText().toString()))
                                {
                                    Toast.makeText(getApplicationContext(),"Set the Marks First",Toast.LENGTH_SHORT).show();
                                }
                                else
                                if(!dateSet)
                                {
                                    Toast.makeText(getApplicationContext(),"Set the Date First",Toast.LENGTH_SHORT).show();
                                }
                                else
                                if(i!=usersList.size()-1)
                                {
                                    String s = marks.getText().toString()+"/"+outof.getText().toString();
                                    reference.child(name).child("Marks By Date").child(Date).child(box.getText().toString()).setValue(s);
                                    reference.child(name).child("Marks By Name").child(box.getText().toString()).child(Date).setValue(s);
                                    box.setText(usersList.get(++i));
                                }
                                else
                                {
                                    flag=true;
                                    Toast.makeText(getApplicationContext(),"List Ended\nClick Submit Button!!!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        prev.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(TextUtils.isEmpty(marks.getText().toString()) || TextUtils.isEmpty(outof.getText().toString()))
                                {
                                    Toast.makeText(getApplicationContext(),"Set the Marks First",Toast.LENGTH_SHORT).show();
                                }
                                else
                                if(!dateSet)
                                {
                                    Toast.makeText(getApplicationContext(),"Set the Date First",Toast.LENGTH_SHORT).show();
                                }
                                else
                                if(i!=0)
                                {
                                    String s = marks.getText().toString()+"/"+outof.getText().toString();
                                    reference.child(name).child("Marks By Date").child(Date).child(box.getText().toString()).setValue(s);
                                    reference.child(name).child("Marks By Name").child(box.getText().toString()).child(Date).setValue(s);
                                    box.setText(usersList.get(--i));
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"List Ended",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("unique", "loadPost:onCancelled", databaseError.toException());
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                if(TextUtils.isEmpty(marks.getText().toString()) || TextUtils.isEmpty(outof.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Set the Marks First",Toast.LENGTH_SHORT).show();
                }
                else
                if(!dateSet)
                {
                    Toast.makeText(getApplicationContext(),"Set the Date First",Toast.LENGTH_SHORT).show();
                }
                else
                if(flag)
                {
                    String s = marks.getText().toString()+"/"+outof.getText().toString();
                    reference.child(name).child("Marks By Date").child(Date).child(box.getText().toString()).setValue(s);
                    reference.child(name).child("Marks By Name").child(box.getText().toString()).child(Date).setValue(s);
                    box.setText(usersList.get(usersList.size()-1));
                    Toast.makeText(getApplicationContext(),"Recorded Successfully!!!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),AdminMarks1.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter Marks for all Students First!!!",Toast.LENGTH_SHORT).show();
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
        String e1="",e2="";
        if(month<10) e2="0";
        if(dayOfMonth<10) e1="0";
        String a = ":";
        String s = e1+Integer.toString(dayOfMonth)+a+e2+Integer.toString(month+1)+a+Integer.toString(year);
        date.setText("Recording Test Scores for :- "+s);
        Date=s;
    }
}

