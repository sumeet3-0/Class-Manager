package com.example.class1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAByName extends AppCompatActivity {

    ArrayAdapter arrayAdapter;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<String> usersList = new ArrayList<String>();
    ArrayList<String> recordList = new ArrayList<String>();
    ListView List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_a_by_name);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        List = findViewById(R.id.List);
        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                reference.child("Attendance By Name").child(s).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String s =postSnapshot.getValue()+" On "+postSnapshot.getKey();
                            recordList.add(s);
                        }
                        arrayAdapter =
                                new ArrayAdapter(ViewAByName.this, android.R.layout.simple_list_item_1, recordList);
                        List.setAdapter(arrayAdapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("unique", "loadPost:onCancelled", databaseError.toException());
                    }
                });
            }
        });
        reference.child("Attendance By Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String s = postSnapshot.getKey();
                    usersList.add(s);
                }
                arrayAdapter =
                        new ArrayAdapter(ViewAByName.this, android.R.layout.simple_list_item_1, usersList);
                List.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("unique", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }
}
