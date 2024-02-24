package com.example.doan_giasu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Khoiphucmatkhau_Activity extends AppCompatActivity {
    Button btnKhoiphuc;
    EditText edtSodienthoai, edtMatkhau, edtMatkhaumoi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoiphucmatkhau);
        addControls();
        addEvents();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Khôi phục mật khẩu");
    }

    private void addEvents() {
        btnKhoiphuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable icERR =getResources().getDrawable(R.drawable.baseline_error_24);
                icERR.setBounds(0,0,icERR.getIntrinsicWidth(),icERR.getIntrinsicHeight());
                String password = edtMatkhau.getText().toString().trim();
                String passwordnew = edtMatkhaumoi.getText().toString().trim();
                String phone = edtSodienthoai.getText().toString().trim();
                if(phone.isEmpty()){
                    edtSodienthoai.setCompoundDrawables(null,null,icERR,null);
                    edtSodienthoai.setError("Vui lòng nhập số điện thoại",icERR);
                }
                if(password.isEmpty()){
                    edtMatkhau.setCompoundDrawables(null,null,icERR,null);
                    edtMatkhau.setError("Vui lòng nhập mật khẩu",icERR);
                }
                if(passwordnew.isEmpty()){
                    edtMatkhaumoi.setCompoundDrawables(null,null,icERR,null);
                    edtMatkhaumoi.setError("Vui lòng nhập lại mật khẩu mới",icERR);
                }
                if(!password.isEmpty() && !passwordnew.isEmpty() && !phone.isEmpty()){
                    edtMatkhau.setCompoundDrawables(null,null,null,null);
                    edtMatkhaumoi.setCompoundDrawables(null,null,null,null);
                    edtSodienthoai.setCompoundDrawables(null,null,null,null);
                    Intent i = new Intent(Khoiphucmatkhau_Activity.this, MainActivity4.class);
                    startActivity(i);
                }
            }
        });
    }

    private void addControls() {
        edtSodienthoai = findViewById(R.id.edt_Sodienthoai_khoiphucmatkhau);
        edtMatkhau = findViewById(R.id.edt_Matkhaumoi_khoiphucmatkhau);
        edtMatkhaumoi = findViewById(R.id.edt_Nhaplaimatkhaumoii_khoiphucmatkhau);
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