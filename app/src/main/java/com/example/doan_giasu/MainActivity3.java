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

public class MainActivity3 extends AppCompatActivity {
    Button btnDk;
    EditText edtSdt, edtMk, edtMkmoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        addControls();
        addEvents();

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đăng ký");
    }

    private void addEvents() {
        btnDk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable icERR =getResources().getDrawable(R.drawable.baseline_error_24);
                icERR.setBounds(0,0,icERR.getIntrinsicWidth(),icERR.getIntrinsicHeight());
                String password = edtMk.getText().toString().trim();
                String passwordnew = edtMkmoi.getText().toString().trim();
                String phone = edtSdt.getText().toString().trim();
                if(phone.isEmpty()){
                    edtSdt.setCompoundDrawables(null,null,icERR,null);
                    edtSdt.setError("Vui lòng nhập số điện thoại",icERR);
                }
                if(password.isEmpty()){
                    edtMk.setCompoundDrawables(null,null,icERR,null);
                    edtMk.setError("Vui lòng nhập mật khẩu",icERR);
                }
                if(passwordnew.isEmpty()){
                    edtMkmoi.setCompoundDrawables(null,null,icERR,null);
                    edtMkmoi.setError("Vui lòng nhập lại mật khẩu mới",icERR);
                }
                if(!password.isEmpty() && !passwordnew.isEmpty() && !phone.isEmpty()){
                    edtMk.setCompoundDrawables(null,null,null,null);
                    edtMkmoi.setCompoundDrawables(null,null,null,null);
                    edtSdt.setCompoundDrawables(null,null,null,null);
                    Intent i = new Intent(MainActivity3.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private void addControls() {
        btnDk = findViewById(R.id.btnDk);
        edtSdt = findViewById(R.id.edtSdt);
        edtMkmoi = findViewById(R.id.edtMkmoi);
        edtMk = findViewById(R.id.edtMk);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}