package com.example.doan_giasu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthOptions;
//import com.google.firebase.auth.PhoneAuthProvider;

import com.example.doan_giasu.Fragment.NewclassFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.TimeUnit;

public class Dangky_Activity extends AppCompatActivity {

    private static final String TAG = Dangky_Activity.class.getName();
    Button btnDk;
    EditText edtSdt, edtMk, edtMkmoi;

    private String selectedRole = ""; // Biến để lưu trữ vai trò đã chọn
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        addControls();
        addEvents();

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đăng ký");
    }

    private void addEvents() {
        btnDk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taotaikhoan();
                Drawable icERR =getResources().getDrawable(R.drawable.baseline_error_24);
                icERR.setBounds(0,0,icERR.getIntrinsicWidth(),icERR.getIntrinsicHeight());
                String password = edtMk.getText().toString().trim();
                String passwordnew = edtMkmoi.getText().toString().trim();
                String phone = edtSdt.getText().toString().trim();
                if(phone.isEmpty()){
                    edtSdt.setCompoundDrawables(null,null,icERR,null);
                    edtSdt.setError("Vui lòng nhập số điện thoại",icERR);
                    return;
                }
//                else if (phone.length() < 9|| !phone.matches("\\d+")) {
//                    edtSdt.setCompoundDrawables(null,null,icERR,null); //Kiểm tra số điện thoại phải hơn 9 số và không có ký tự hay chữ cái
//                    edtSdt.setError("Số điện thoại không hợp lệ", icERR);
//                    return;
//                }
                if(password.isEmpty()){
                    edtMk.setCompoundDrawables(null,null,icERR,null);
                    edtMk.setError("Vui lòng nhập mật khẩu",icERR);
                    return;
                }
                else if (password.length() < 8) {
                    edtMk.setCompoundDrawables(null, null, icERR, null); //Kiểm tra mâ khẩu ít nhất 8 ký tự
                    edtMk.setError("Mật khẩu phải có ít nhất 8 ký tự", icERR);
                    return;
                } else if (!password.matches(".*[A-Z].*")) {
                    edtMk.setCompoundDrawables(null, null, icERR, null);
                    edtMk.setError("Mật khẩu phải chứa ít nhất 1 ký tự viết hoa", icERR);//Mật khẩu phải có ít nhất một ký tự viết hoa
                    return;
                }
                if(passwordnew.isEmpty()){
                    edtMkmoi.setCompoundDrawables(null,null,icERR,null);
                    edtMkmoi.setError("Vui lòng nhập lại mật khẩu mới",icERR);
                    return;
                }
                if (!password.equals(passwordnew)) {
                    edtMkmoi.setCompoundDrawables(null, null, icERR, null);  // Kiểm tra mật khẩu mới có giống mật khẩu cũ không
                    edtMkmoi.setError("Mật khẩu mới không khớp", icERR);
                    return;
                }
                if(!password.isEmpty() && !passwordnew.isEmpty() && !phone.isEmpty()){
                    edtMk.setCompoundDrawables(null,null,null,null);
                    edtMkmoi.setCompoundDrawables(null,null,null,null);
                    edtSdt.setCompoundDrawables(null,null,null,null);
                    Intent i = new Intent(Dangky_Activity.this, Dangnhap_Activity.class);
                    startActivity(i);
                    //Return từng cái trên dùng để nếu sai dữ liệu thì nhập lại và không chuyển trang
                }

            }
        });

    }
    public void taotaikhoan() {
        String password = edtMk.getText().toString();
        String email = edtSdt.getText().toString();         //Coi lại layout chứ dùng sđt không được,dể tên thì được chứ dùng số là không cho này chỉ dùng gmail thôi
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, password)        //Toàn bộ nên chuyển sang gmail vì số điện thoại không dugnf được rảnh thì sửa layout lại thành gmail hết
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Thông báo đăng ký thành công
                            Toast.makeText(Dangky_Activity.this,"Đăng ký thành công.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Dangky_Activity.this, Khoiphucmatkhau_Activity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            // Nếu tạo tài khoản thất bại, hiển thị thông báo lỗi
                            Toast.makeText(Dangky_Activity.this, "Đăng ký thất bại.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addControls() {
        btnDk = findViewById(R.id.btn_dangky_dangky);
        edtSdt = findViewById(R.id.edt_Sdt_dangky);
        edtMkmoi = findViewById(R.id.edt_nhaplaimatkhau_dangky);
        edtMk = findViewById(R.id.edt_matkhau_dangky);
    }


    //Sự kiện quay lại khi ấn nút mũi tên trên toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}