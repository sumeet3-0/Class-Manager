package com.example.class1;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PayFees  extends AppCompatActivity{

    EditText name,upi_id,amount;
    Button pay_now;
    String TAG = "main" , userName , batch;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    final int UPI_PAYMENT=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_payfees);
        database = FirebaseDatabase.getInstance();
        reference= database.getReference();
        mAuth=FirebaseAuth.getInstance();
        pay_now=(Button)findViewById(R.id.pay_now);
        name=(EditText)findViewById(R.id.name);
        upi_id=(EditText)findViewById(R.id.upi_id);
        amount=(EditText)findViewById(R.id.amount);


        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString().trim())){
                    Toast.makeText(PayFees.this,"Name is invalid",Toast.LENGTH_SHORT).show();
                }else  if (TextUtils.isEmpty(upi_id.getText().toString().trim())){
                    Toast.makeText(PayFees.this,"Upi_id is invalid",Toast.LENGTH_SHORT).show();
                }else  if (TextUtils.isEmpty(amount.getText().toString().trim())){
                    Toast.makeText(PayFees.this,"Amount is invalid",Toast.LENGTH_SHORT).show();
                }else{

                    reference.child("UPI").child("id").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           String UPI = dataSnapshot.getValue().toString();
                            Toast.makeText(PayFees.this,"Paying to "+UPI,Toast.LENGTH_SHORT).show();
                            payUsingUpi("Vineet Paranjpe",UPI,amount.getText().toString());
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w("unique", "loadPost:onCancelled", databaseError.toException());
                        }
                    });
                }
            }
        });
    }

    void payUsingUpi(String name,String upi_id,String amount){
        Log.e("main","Name " + name + "--up--" + upi_id + "--" + amount);

        String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
        int GOOGLE_PAY_REQUEST_CODE = 123;

        Uri uri = Uri.parse("upi://pay").buildUpon()
                        .appendQueryParameter("pa", upi_id)
                        .appendQueryParameter("pn",name)
                       // .appendQueryParameter("mc", "your-merchant-code")
                        //.appendQueryParameter("tr", "your-transaction-ref-id")
                        //.appendQueryParameter("tn", "your-transaction-note")
                        .appendQueryParameter("am", amount)
                        .appendQueryParameter("cu", "INR")
                        //.appendQueryParameter("url", "your-transaction-url")
                        .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser=Intent.createChooser(upiPayIntent,"Pay with");


        if(null!=chooser.resolveActivity(getPackageManager())){
            startActivityForResult(chooser,UPI_PAYMENT);
        }else
        {
            Toast.makeText(PayFees.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();;
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        Log.e("main","response "+ resultCode);

        switch (requestCode){
            case UPI_PAYMENT:
                if(RESULT_OK==resultCode||(resultCode==11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                }
                else{
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);

            }

                break;
        }
    }


    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(PayFees.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.

                Toast.makeText(PayFees.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successful: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                final FirebaseUser user = mAuth.getCurrentUser();
                reference.child("Mapp").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userName = dataSnapshot.getValue().toString();
                        reference.child("Batch").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                batch = dataSnapshot.getValue().toString();
                                SimpleDateFormat s = new SimpleDateFormat("dd:MM:yyyy hh:mm:ss");
                                String format = s.format(new Date());
                                reference.child("Fees").child(format).setValue(userName + " from batch "+batch+" has paid "+amount.getText().toString());

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
                Toast.makeText(PayFees.this, "Succesful!!!\nContact Class Teacher", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);
            }
            else {
                Toast.makeText(PayFees.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);
            }
        } else {
            Log.e("UPI", "Internet issue: ");
            Toast.makeText(PayFees.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }


    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

}
