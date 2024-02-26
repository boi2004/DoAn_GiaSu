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
                    return;
                }
                else if (phone.length() < 9|| !phone.matches("\\d+")) {
                    edtSodienthoai.setCompoundDrawables(null,null,icERR,null); //Kiểm tra số điện thoại phải hơn 9 số và không có ký tự hay chữ cái
                    edtSodienthoai.setError("Số điện thoại không hợp lệ", icERR);
                    return;
                }
                if(password.isEmpty()){
                    edtMatkhau.setCompoundDrawables(null,null,icERR,null);
                    edtMatkhau.setError("Vui lòng nhập mật khẩu",icERR);
                    return;
                }
                else if (password.length() < 8) {
                    edtMatkhau.setCompoundDrawables(null, null, icERR, null); //Kiểm tra mâ khẩu ít nhất 8 ký tự
                    edtMatkhau.setError("Mật khẩu phải có ít nhất 8 ký tự", icERR);
                    return;
                } else if (!password.matches(".*[A-Z].*")) {
                    edtMatkhau.setCompoundDrawables(null, null, icERR, null);
                    edtMatkhau.setError("Mật khẩu phải chứa ít nhất 1 ký tự viết hoa", icERR);//Mật khẩu phải có ít nhất một ký tự viết hoa
                    return;
                }
                if(passwordnew.isEmpty()){
                    edtMatkhaumoi.setCompoundDrawables(null,null,icERR,null);
                    edtMatkhaumoi.setError("Vui lòng nhập lại mật khẩu mới",icERR);
                    return;
                }
                if (!password.equals(passwordnew)) {
                    edtMatkhaumoi.setCompoundDrawables(null, null, icERR, null);  // Kiểm tra mật khẩu mới có giống mật khẩu cũ không
                    edtMatkhaumoi.setError("Mật khẩu mới không khớp", icERR);
                    return;
                }
                if(!password.isEmpty() && !passwordnew.isEmpty() && !phone.isEmpty()){
                    edtMatkhau.setCompoundDrawables(null,null,null,null);
                    edtMatkhaumoi.setCompoundDrawables(null,null,null,null);
                    edtSodienthoai.setCompoundDrawables(null,null,null,null);
                    Intent i = new Intent(Khoiphucmatkhau_Activity.this, Dangnhap_Activity.class);
                    startActivity(i);
                    //Return từng cái trên dùng để nếu sai dữ liệu thì nhập lại và không chuyển trang
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