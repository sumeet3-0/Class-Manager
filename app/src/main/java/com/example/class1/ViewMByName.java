package com.example.class1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewMByName extends AppCompatActivity {

    ArrayAdapter arrayAdapter;
    FirebaseDatabase database;
    DatabaseReference reference;
    Spinner chooseBatch ;
    ArrayList<String> batchList = new ArrayList<String>();
    ArrayList<String> usersList = new ArrayList<String>();
    ArrayList<String> recordList = new ArrayList<String>();
    ListView List;
    String batch , b ="Batch" , m = "Mapp" , n = "Notifications" , f="Fees" , c ="Chats";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_m_by_name);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        List = findViewById(R.id.List);
        chooseBatch = findViewById(R.id.chooseBatch);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String s = postSnapshot.getKey();
                    if(s.equals(b) | s.equals(m) | s.equals(n) | s.equals(f) | s.equals(c))
                        continue;
                    batchList.add(s);
                }
                arrayAdapter =
                        new ArrayAdapter<>(ViewMByName.this,R.layout.customlist,batchList);
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
                batch = chooseBatch.getSelectedItem().toString();
                reference.child(batch).child("Marks By Name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        usersList.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String s =postSnapshot.getKey();
                            usersList.add(s);
                        }
                        arrayAdapter =
                                new ArrayAdapter<>(ViewMByName.this, R.layout.customlist, usersList);
                        List.setAdapter(arrayAdapter);
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
        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                reference.child(batch).child("Marks By Name").child(s).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        recordList.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String s =postSnapshot.getKey()+" : "+postSnapshot.getValue();
                            recordList.add(s);
                        }
                        arrayAdapter =
                                new ArrayAdapter<>(ViewMByName.this, R.layout.customlist, recordList);
                        List.setAdapter(arrayAdapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("unique", "loadPost:onCancelled", databaseError.toException());
                    }
                });
            }
        });
    }
}
