package com.example.doan_giasu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Khoiphucmatkhau_Activity extends AppCompatActivity {
    Button btnKhoiphuc;
    EditText edt_email_khoiphucmatkhau;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoiphucmatkhau);

        addControls();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Khôi phục mật khẩu");

        btnKhoiphuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickQuenMatKhau();
            }
        });

    }

    private void clickQuenMatKhau() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        Drawable icERR =getResources().getDrawable(R.drawable.baseline_error_24);
        icERR.setBounds(0,0,icERR.getIntrinsicWidth(),icERR.getIntrinsicHeight());

        ProgressDialog progressDialog = new ProgressDialog(Khoiphucmatkhau_Activity.this);
        progressDialog.setMessage("Đang kiểm tra email...");
        progressDialog.show();


        String email = edt_email_khoiphucmatkhau.getText().toString().trim();
        if(email.isEmpty()){        //Xem có bị trống không
            edt_email_khoiphucmatkhau.setCompoundDrawables(null,null,icERR,null);
            edt_email_khoiphucmatkhau.setError("Vui lòng nhập Email",icERR);
            return;
        }
        String emailAddress = email;


        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(Khoiphucmatkhau_Activity.this,"Mật khẩu mới đã được gửi đến email của bạn, vui lòng kiểm tra!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Khoiphucmatkhau_Activity.this,Dangnhap_Activity.class);
                            startActivity(intent);
                        }
                    }
                });

    }

    private void AddEvent() {
    }

    private void addControls() {
        edt_email_khoiphucmatkhau = findViewById(R.id.edt_email_khoiphucmatkhau);
        btnKhoiphuc = findViewById(R.id.btn_Khoiphucmatkhau_khoiphucmatkhau);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}