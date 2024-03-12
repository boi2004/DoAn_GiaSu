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
                if(phone.isEmpty()){        //Xem có bị trống không
                    edt_Sodienthoai.setCompoundDrawables(null,null,icERR,null);
                    edt_Sodienthoai.setError("Vui lòng nhập số điện thoại",icERR);
                    return;
                }
                if(password.isEmpty()){ ////Xem có bị trống không
                    edt_Matkhau.setCompoundDrawables(null,null,icERR,null);
                    edt_Matkhau.setError("Vui lòng nhập mật khẩu",icERR);
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
    @Override
    public void onBackPressed() {
        //Tạo hộp thoại
        AlertDialog.Builder mydialog = new AlertDialog.Builder(Dangnhap_Activity.this);             //Thông báo thoát ứng dụng
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
    public void dangnhap() {    //Form đăng nhập
        String password = edt_Matkhau.getText().toString();
        String email = edt_Sodienthoai.getText().toString();         //Coi lại layout chứ dùng sđt không được,dể tên thì được chứ dùng số là không cho này chỉ dùng gmail thôi
        FirebaseAuth auth = FirebaseAuth.getInstance();
        ProgressDialog progressDialog = new ProgressDialog(Dangnhap_Activity.this);
        progressDialog.setMessage("Đang kiểm tra đăng nhập...");
        progressDialog.show();
        progressDialog.show();

        auth.signInWithEmailAndPassword(email, password)        //Toàn bộ nên chuyển sang gmail vì số điện thoại không dugnf được rảnh thì sửa layout lại thành gmail hết
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
        edt_Sodienthoai = findViewById(R.id.edt_Sodienthoai_dangnhap);
        edt_Matkhau = findViewById(R.id.edt_Matkhau_dangnhap);
        txtQuenmatkhau = findViewById(R.id.txt_Quenmatkhau_dangnhap);
        txt_Dangky = findViewById(R.id.txt_Dangky_dangnhap);
    }
}