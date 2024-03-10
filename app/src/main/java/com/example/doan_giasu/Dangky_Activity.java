package com.example.doan_giasu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import com.example.doan_giasu.Fragment.NewclassFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Dangky_Activity extends AppCompatActivity {

    private static final String TAG = Dangky_Activity.class.getName();
    Button btnDk;
    EditText edtSdt, edtMk, edtMkmoi,edt_Enter_Otp;
    RadioButton button_GV,button_SV;
    RadioGroup radioGroup;
    FirebaseAuth mAuth;


    private String selectedRole = ""; // Biến để lưu trữ vai trò đã chọn
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        addControls();
        mAuth = FirebaseAuth.getInstance(); // Khởi tạo FirebaseAuth ở đây

        btnDk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtSdt.getText().toString().trim();
                //veryfy sdt
                veryfyphonenumber(phone);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar3);
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
                    return;
                }
                else if (phone.length() < 9) {
                    edtSdt.setCompoundDrawables(null,null,icERR,null); //Kiểm tra số điện thoại phải hơn 9 số và không có ký tự hay chữ cái
                    edtSdt.setError("Số điện thoại không hợp lệ", icERR);
                    return;
                }
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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Xác định ROLE nào được chọn
                if (checkedId == R.id.radioButton_GV_dangky)
                {
                    selectedRole = "GiaoVien";
                    showToast("Đã chọn vai trò Giáo viên");
                }
                else if (checkedId == R.id.radioButton_SV_dangky)
                {
                    selectedRole = "SinhVien";
                    showToast("Đã chọn vai trò Sinh viên");
                }
            }
        });
    }

    private void veryfyphonenumber(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)
                        .setTimeout(10L, TimeUnit.SECONDS)   //
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            //
                            //onVerificationCompleted: sau khi verify thành công thì tiếp theo làm những gì.
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                signInWithPhoneAuthCredentinal(phoneAuthCredential);
                            }


                            //onVerificationCompleted: verify thất bại thì..
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                //thong bao toast
                                Toast.makeText(Dangky_Activity.this, "Xác minh thất bại: " + e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String ma, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(ma, forceResendingToken);

                                goEnterOTPActivity(phone, ma);
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }



    private void signInWithPhoneAuthCredentinal(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){ // sự kiện sau khi nhập otp thành công
                            Log.e(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            gotoMainActivity(user.getPhoneNumber());
                        } else { // sự kiện nhâp mã thất bại
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(Dangky_Activity.this,
                                        "Mã không chính xác, vui lòng thử lại.",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void gotoMainActivity(String phone) {
        Intent intent = new Intent(this, NewclassFragment.class);
        intent.putExtra("phone_number", phone);
        startActivity(intent);

    }
    private void goEnterOTPActivity(String phone, String ma) {
        Intent intent = new Intent(this, EnterOTPActivity.class);
        intent.putExtra("phone_number", phone);
        intent.putExtra("ma_id", ma);
        startActivity(intent);
    }

    private void addControls() {
        btnDk = findViewById(R.id.btn_dangky_dangky);
        edtSdt = findViewById(R.id.edt_Sdt_dangky);
        edtMkmoi = findViewById(R.id.edt_nhaplaimatkhau_dangky);
        edtMk = findViewById(R.id.edt_matkhau_dangky);
        button_GV = findViewById(R.id.radioButton_GV_dangky);
        button_SV = findViewById(R.id.radioButton_SV_dangky);
        radioGroup = findViewById(R.id.radioGroup_GV_HV_dangky);
        edt_Enter_Otp = findViewById(R.id.edt_Enter_Otp);
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
