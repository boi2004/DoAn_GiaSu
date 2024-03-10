package com.example.doan_giasu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_giasu.Fragment.NewclassFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterOTPActivity extends AppCompatActivity {
    public static final String TAG = EnterOTPActivity.class.getName();
    FirebaseAuth mAuth;
    EditText edt_Enter_Otp;
    Button btn_verifyNumber,btn_guilai_Otp;
    String phone;
    String ma_id;
    PhoneAuthProvider.ForceResendingToken mforceResendingToken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        addControl();

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_enter_otp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Xác minh mã OTP");

        //Sự kiện click vào "SEND"
        btn_verifyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strOtp = edt_Enter_Otp.getText().toString().trim();
                SendOTPcode(strOtp);
            }
        });

        //Sự kiện click vào "GUI LAI OTP"
        btn_guilai_Otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendOTPAgain();
            }
        });

        getDataIntent();
    }
    private void addControl() {
        edt_Enter_Otp = findViewById(R.id.edt_Enter_Otp);
        btn_verifyNumber = findViewById(R.id.btn_verifyNumber);
        btn_guilai_Otp = findViewById(R.id.btn_guilai_Otp);
        mAuth = FirebaseAuth.getInstance();
    }

    private void SendOTPcode(String strOtp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(ma_id, strOtp);
        signInWithPhoneAuthCredentinal(credential);
    }

    private void SendOTPAgain() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L, TimeUnit.SECONDS)   //
                        .setActivity(this)
                        .setForceResendingToken(mforceResendingToken)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            //
                            //onVerificationCompleted: sau khi verify thành công thì tiếp theo làm những gì.
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                signInWithPhoneAuthCredentinal(phoneAuthCredential);
                            }


                            //onVerificationCompleted: verify thất bại thì..
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                //thong bao toast
                                Toast.makeText(EnterOTPActivity.this,"Failed",Toast.LENGTH_LONG).show();

                            }

                            //ForceResendingToken:
                            @Override
                            public void onCodeSent(@NonNull String ma, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(ma, forceResendingToken);
                                ma_id = ma;
                                mforceResendingToken = forceResendingToken;
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredentinal(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){ // sự kiện sau khi nhập otp thành công
                            Log.e(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            gotoMainActivity(user.getPhoneNumber());
                        } else { // sự kiện nhâp mã thất bại
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(EnterOTPActivity.this,
                                        "Mã không chính xác, vui lòng thử lại.",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
    private void gotoMainActivity(String phone) {
        Intent intent = new Intent(this, NewclassFragment.class);
        intent.putExtra("phone_number", phone);
        startActivity(intent);

    }


    private void getDataIntent(){//lay du lieu tu intent

        phone = getIntent().getStringExtra("phone_number");
        ma_id = getIntent().getStringExtra("ma_id");

    }



    //quay lại toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}