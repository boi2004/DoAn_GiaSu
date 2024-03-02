package com.example.doan_giasu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doan_giasu.Fragment.SetFragment_Activity;

public class Dangnhap_Activity extends AppCompatActivity {
    Button btn_Dangnhap;
    EditText edt_Sodienthoai, edt_Matkhau;
    TextView txtQuenmatkhau, txt_Dangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
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
                    Intent i = new Intent(Dangnhap_Activity.this, SetFragment_Activity.class);
                    startActivity(i);
                }

            }
        });
        txtQuenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dangnhap_Activity.this, Khoiphucmatkhau_Activity.class));

            }
        });
        txt_Dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dangnhap_Activity.this, Dangky_Activity.class));
            }
        });

    }
    @Override
    public void onBackPressed() {
        //Tạo hộp thoại
        AlertDialog.Builder mydialog = new AlertDialog.Builder(Dangnhap_Activity.this);
        mydialog.setTitle("EXIT");
        mydialog.setMessage("Bạn có muốn thoát khỏi ứng dụng");
        mydialog.setIcon(R.drawable.ic_error);
        mydialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish(); //thoát ứng dụng
            }
        });
        mydialog.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        mydialog.show().create();
    };



    private void addControls() {
        btn_Dangnhap = findViewById(R.id.btn_Dangnhap_dangnhap);
        edt_Sodienthoai = findViewById(R.id.edt_Sodienthoai_dangnhap);
        edt_Matkhau = findViewById(R.id.edt_Matkhau_dangnhap);
        txtQuenmatkhau = findViewById(R.id.txt_Quenmatkhau_dangnhap);
        txt_Dangky = findViewById(R.id.txt_Dangky_dangnhap);
    }
}