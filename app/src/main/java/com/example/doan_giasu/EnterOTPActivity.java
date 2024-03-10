package com.example.doan_giasu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnterOTPActivity extends AppCompatActivity {
    EditText edt_Enter_Otp;
    Button btn_verifyNumber,btn_guilai_Otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        //
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
    }
    private void addControl() {
        edt_Enter_Otp = findViewById(R.id.edt_Enter_Otp);
        btn_verifyNumber = findViewById(R.id.btn_verifyNumber);
        btn_guilai_Otp = findViewById(R.id.btn_guilai_Otp);
    }

    private void SendOTPcode(String strOtp) {
    }

    private void SendOTPAgain() {
    }




    //quay lại
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}