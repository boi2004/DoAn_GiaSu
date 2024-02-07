package com.example.doan_giasu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn_Dangnhap;
    EditText edt_Sodienthoai, edt_Matkhau;
    TextView txtQuenmatkhau, txt_Dangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_Dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable icERR =getResources().getDrawable(R.drawable.baseline_error_24);
                icERR.setBounds(0,0,icERR.getIntrinsicWidth(),icERR.getIntrinsicHeight());
                String phone = edt_Sodienthoai.getText().toString().trim();
                String password = edt_Matkhau.getText().toString().trim();
                if(phone.isEmpty()){
                    edt_Sodienthoai.setCompoundDrawables(null,null,icERR,null);
                    edt_Sodienthoai.setError("Vui lòng nhập số điện thoại",icERR);
                }
                if(password.isEmpty()){
                    edt_Matkhau.setCompoundDrawables(null,null,icERR,null);
                    edt_Matkhau.setError("Vui lòng nhập mật khẩu",icERR);
                }
                if(!phone.isEmpty() && !password.isEmpty()){
                    edt_Sodienthoai.setCompoundDrawables(null,null,null,null);
                    edt_Matkhau.setCompoundDrawables(null,null,null,null);
                    Intent i = new Intent(MainActivity.this, MainActivity4.class);
                    startActivity(i);
                }

            }
        });
        txtQuenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));

            }
        });
        txt_Dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity3.class));
            }
        });

    }

    private void addControls() {
        btn_Dangnhap = findViewById(R.id.btn_Dangnhap);
        edt_Sodienthoai = findViewById(R.id.edt_Sodienthoai);
        edt_Matkhau = findViewById(R.id.edt_Matkhau);
        txtQuenmatkhau = findViewById(R.id.txtQuenmatkhau);
        txt_Dangky = findViewById(R.id.txt_Dangky);
    }
}