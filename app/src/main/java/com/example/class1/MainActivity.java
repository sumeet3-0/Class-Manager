package com.example.class1;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity  {

    TextView regHere ;
    private static final String TAG = "EmailPassword";
    private EditText mEmailField;
    private EditText mPasswordField;
    private FirebaseAuth mAuth;
    private Button teacher,parent;
    private FirebaseDatabase database;
    private DatabaseReference reference ;
    private String userName,admin="admin";
    ProgressBar p ;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        p.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regHere = findViewById(R.id.reghere);
        mEmailField = findViewById(R.id.email);
        mPasswordField = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        teacher =findViewById(R.id.teacher);
        parent = findViewById(R.id.parent);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        p = findViewById(R.id.progressBar1);
        p.setVisibility(View.GONE);
        regHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RegActivity.class);
                startActivity(i);
            }
        });
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateEmail()|!validatePassword())
                {
                    return;
                }
                p.setVisibility(View.VISIBLE);
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString(),"P");
            }
        });
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateEmail()|!validatePassword())
                {
                    return;
                }
                p.setVisibility(View.VISIBLE);
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString(),"A");

            }
        });

    }
    private boolean validateEmail() {
        String occParentS = mEmailField.getText().toString();
        if(occParentS.equals("")){
            mEmailField.setError("Please Enter EmailId");
            return false;
        }
        return true;
    }
    private boolean validatePassword() {
        String occParentS = mPasswordField.getText().toString();
        if(occParentS.equals("")){
            mPasswordField.setError("Please Enter Password");
            return false;
        }
        return true;
    }
    private void signIn(final String email, String password, final String status) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                           if(status=="A")
                           {

                               reference.child("Mapp").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                       userName = dataSnapshot.getValue().toString();
                                       if(userName.equals(admin))
                                       {
                                           Toast.makeText(getApplicationContext(), "Sign in Success!!!\nWelcome "+userName,Toast.LENGTH_LONG).show();

                                           Intent i = new Intent(getApplicationContext(), Admin.class);
                                           startActivity(i);
                                       }
                                       else
                                       {
                                           p.setVisibility(View.GONE);
                                           Toast.makeText(getApplicationContext(), "Not an Admin!!!",
                                                   Toast.LENGTH_LONG).show();
                                       }

                                   }
                                   @Override
                                   public void onCancelled(@NonNull DatabaseError databaseError) {
                                       p.setVisibility(View.GONE);
                                       Log.w("unique", "loadPost:onCancelled", databaseError.toException());
                                   }
                               });

                           }
                           else
                           {

                               reference.child("Mapp").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                       userName = dataSnapshot.getValue().toString();
                                       Toast.makeText(getApplicationContext(), "Sign in Success!!!\nWelcome "+userName,Toast.LENGTH_LONG).show();
                                       Intent i = new Intent(getApplicationContext(), Parent.class);
                                       startActivity(i);

                                   }
                                   @Override
                                   public void onCancelled(@NonNull DatabaseError databaseError) {
                                       p.setVisibility(View.GONE);
                                       Log.w("unique", "loadPost:onCancelled", databaseError.toException());
                                   }
                               });
                           }

                        } else {
                            p.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });
        // [END sign_in_with_email]
    }


}
