package com.example.class1;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

public class ParentMarks1 extends AppCompatActivity {
    ArrayAdapter arrayAdapter;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    ArrayList<String> usersList = new ArrayList<String>();
    String userName;
    ListView List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_marks1);
        Log.w("unique", "Success");
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        List = findViewById(R.id.List);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //Toast.makeText(getApplicationContext(), user.getUid(),Toast.LENGTH_LONG).show();
       reference.child("Mapp").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userName = dataSnapshot.getValue().toString();
                reference.child("Marks By Name").child(userName).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String s =postSnapshot.getValue()+" On "+postSnapshot.getKey();
                            usersList.add(s);
                        }
                        Log.w("unique", "Success");
                        arrayAdapter =
                                new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, usersList);
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
                Toast.makeText(getApplicationContext(), "Error",Toast.LENGTH_LONG).show();
            }
        });

    }
}
