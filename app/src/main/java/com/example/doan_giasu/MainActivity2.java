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

public class MainActivity2 extends AppCompatActivity {
    Button btnKhoiphuc;
    EditText edtSodienthoai, edtMatkhau, edtMatkhaumoi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
                    Intent i = new Intent(MainActivity2.this, MainActivity4.class);
                    startActivity(i);
                }
            }
        });
    }

    private void addControls() {
        edtSodienthoai = findViewById(R.id.edtSodienthoai);
        edtMatkhau = findViewById(R.id.edtMatkhau);
        edtMatkhaumoi = findViewById(R.id.edtMatkhaumoi);
        btnKhoiphuc = findViewById(R.id.btnKhoiphuc);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}