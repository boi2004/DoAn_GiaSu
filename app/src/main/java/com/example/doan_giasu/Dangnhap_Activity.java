package com.example.doan_giasu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_giasu.Fragment.SetFragment_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Dangnhap_Activity extends AppCompatActivity {
    Button btn_Dangnhap;
    EditText edt_Email_dangnhap, edt_Matkhau;
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

                String email = edt_Email_dangnhap.getText().toString().trim();
                String password = edt_Matkhau.getText().toString().trim();
                if(email.isEmpty()){        //Xem có bị trống không
                    edt_Email_dangnhap.setCompoundDrawables(null,null,icERR,null);
                    edt_Email_dangnhap.setError("Vui lòng nhập Email",icERR);
                    return;
                }
                if(password.isEmpty()){ ////Xem có bị trống không
                    edt_Matkhau.setCompoundDrawables(null,null,icERR,null);
                    edt_Matkhau.setError("Vui lòng nhập Mật khẩu",icERR);
                    return;
                }
                dangnhap(); //Hàm đăng nhập

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
    //thoat ung dung
    @Override
    public void onBackPressed() {
        //Tạo hộp thoại
        super.onBackPressed();
        AlertDialog.Builder mydialog = new AlertDialog.Builder(Dangnhap_Activity.this);             //Thông báo thoát ứng dụng
        mydialog.setTitle("EXIT");
        mydialog.setMessage("Bạn có muốn thoát khỏi ứng dụng");
        mydialog.setIcon(R.drawable.ic_error);
        mydialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish(); //thoát ứng dụng
            }
        });
        mydialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        mydialog.show().create();
    };
    public void dangnhap() {    //Form đăng nhập
        String password = edt_Matkhau.getText().toString().trim();
        String email = edt_Email_dangnhap.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(Dangnhap_Activity.this);
        progressDialog.setMessage("Đang kiểm tra đăng nhập...");
        progressDialog.show();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();   // Ẩn ProgressDialog khi quá trình kiểm tra kết thúc (cái này là loafd trang khi chờ đợi)
                        if (task.isSuccessful()) {
                            // Thông báo đăng nhập thành công
                            Intent intent = new Intent(Dangnhap_Activity.this, SetFragment_Activity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                        else
                        {
                            // Nếu đăng nhập thất bại, hiển thị thông báo lỗi
                            Toast.makeText(Dangnhap_Activity.this, "Đăng Nhập thất bại.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void addControls() {
        btn_Dangnhap = findViewById(R.id.btn_Dangnhap_dangnhap);
        edt_Email_dangnhap = findViewById(R.id.edt_Email_dangnhap);
        edt_Matkhau = findViewById(R.id.edt_Matkhau_dangnhap);
        txtQuenmatkhau = findViewById(R.id.txt_Quenmatkhau_dangnhap);
        txt_Dangky = findViewById(R.id.txt_Dangky_dangnhap);
    }
}