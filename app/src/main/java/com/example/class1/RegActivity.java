package com.example.class1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegActivity extends AppCompatActivity {

    TextView rules;
    private static final String TAG = "EmailPassword";
    public EditText mEmailField,name,school,address,occParent,parent,mobNo;
    public Spinner std,board,medium;
    private EditText mPasswordField;
    private FirebaseAuth mAuth;
    private Button submit;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        rules = findViewById(R.id.rules);
        submit = findViewById(R.id.submit);
        name = findViewById(R.id.name);
        school = findViewById(R.id.school);
        mobNo = findViewById(R.id.mobNo);
        address = findViewById(R.id.address);
        occParent = findViewById(R.id.occParent);
        parent = findViewById(R.id.parent);
        std = findViewById(R.id.std);
        board = findViewById(R.id.board);
        medium = findViewById(R.id.medium);
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TermsConditions.class);
                startActivity(i);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("Users");
                String nameS = name.getText().toString();
                String schoolS = school.getText().toString();
                String parentS = parent.getText().toString();
                String occParentS = occParent.getText().toString();
                String addressS = address.getText().toString();
                String mobNoS = mobNo.getText().toString();
                String mEmailFieldS = mEmailField.getText().toString();
                String boardS =board.getSelectedItem().toString();
                String mediumS = medium.getSelectedItem().toString();
                String stdS= std.getSelectedItem().toString();
                String mPasswordFieldS = mPasswordField.getText().toString();
                Info info = new Info(nameS,schoolS,parentS,occParentS,addressS,mobNoS,mEmailFieldS,mPasswordFieldS,boardS,mediumS,stdS);
                reference.child(nameS).setValue(info);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END create_user_with_email]

    }
}