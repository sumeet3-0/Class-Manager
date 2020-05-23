package com.example.class1;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParentAttendence1 extends AppCompatActivity {


    ArrayAdapter arrayAdapter;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    ArrayList<String> usersList = new ArrayList<String>();
    ListView List;
    String userName , batch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_attendence1);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        List = findViewById(R.id.List);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        reference.child("Mapp").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userName = dataSnapshot.getValue().toString();
                reference.child("Batch").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        batch = dataSnapshot.getValue().toString();
                        reference.child(batch).child("Attendance By Name").child(userName).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String s =postSnapshot.getValue()+" On "+postSnapshot.getKey();
                                    usersList.add(s);
                                }
                                arrayAdapter =
                                        new ArrayAdapter(ParentAttendence1.this, R.layout.customlist, usersList);
                                List.setAdapter(arrayAdapter);

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.w("unique", "loadPost:onCancelled", databaseError.toException());
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
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("unique", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
