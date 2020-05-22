package com.example.class1;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Collections;

public class Fees extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<String> usersList = new ArrayList<String>();
    ListView List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        List = findViewById(R.id.List);
        List.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog diaBox = AskOption(parent, view, position, id);
                diaBox.show();
                return false;
            }

            private AlertDialog AskOption(final AdapterView<?> parent, View view, final int position, long id)
            {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(Fees.this)
                        .setTitle("Delete")
                        .setMessage("Do you want to Delete")
                        .setIcon(R.drawable.delete)

                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                String s = (String) parent.getItemAtPosition(position);
                                reference.child("Users").child(s).removeValue();
                                dialog.dismiss();
                            }

                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .create();

                return myQuittingDialogBox;
            }
        });
        reference.child("Fees").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String s = postSnapshot.getKey() + " : " + postSnapshot.getValue();
                    usersList.add(s);
                }
                Collections.reverse(usersList);
                arrayAdapter =
                        new ArrayAdapter<>(Fees.this, R.layout.customlist, usersList);
                List.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("unique", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }
}
