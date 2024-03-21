package Activity_Menu;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_giasu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DoiMatKhau_Activity extends AppCompatActivity {
    private View mview;
    EditText edtTaiKhoan, edtMatKhauNew, edtMatKhauNewAgain;
   Button btnLuu;
   private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datlaimatkhau);

        addControls();

        addEvents();

        Toolbar toolbar = findViewById(R.id.toolbar3);          //Hàm toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đổi Mật Khẩu");
        int white = getResources().getColor(android.R.color.white);
        toolbar.setTitleTextColor(white);   //Trong đoạn mã trên, toolbar.setTitleTextColor(white) sẽ đặt màu trắng cho tiêu đề của Toolbar.

    }
    private void addControls() {

        progressDialog = new ProgressDialog(DoiMatKhau_Activity.this);
        // Khai báo các EditText
        edtTaiKhoan = findViewById(R.id.editT_matkhaunew_datlaimatkhau);
        edtMatKhauNew = findViewById(R.id.editTextText3);
        edtMatKhauNewAgain = findViewById(R.id.editT_matkhaunewagain_datlaimatkhau);

        // Khai báo các Button
        btnLuu = findViewById(R.id.btn_luu_datlaimatkhau);

    }

    private void addEvents() {
        // Các sự kiện xử lý khi các Button được click
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matKhauCu = edtTaiKhoan.getText().toString().trim();
                String matKhauMoi = edtMatKhauNew.getText().toString().trim();
                String nhapLaiMatKhau = edtMatKhauNewAgain.getText().toString().trim();

                //Kiểm tra mật khẩu mới phải khác mật khẩu cũ
                if (matKhauCu.equals(matKhauMoi)) {
                    edtMatKhauNew.setError("Mật khẩu mới phải khác mật khẩu cũ");
                    return;
                }

                // Kiểm tra nhập lại mật khẩu phải khớp với mật khẩu mới
                if (!matKhauMoi.equals(nhapLaiMatKhau)) {
                    edtMatKhauNewAgain.setError("Mật khẩu không đúng");
                    return;
                }

                // Kiểm tra mật khẩu mới đáp ứng yêu cầu độ dài và thành phần ký tự
                if (!kiemTraMatKhauMoi(matKhauMoi)) {
                    edtMatKhauNew.setError("Mật khẩu của bạn phải có tối thiểu 6 ký tự, đồng thời bao gồm cả chữ số, chữ cái và ký tự đặc biệt (!$@%).");
                    return;
                }
                kiemTraMatKhauCu(matKhauCu);
            }
        });
    }

    private void kiemTraMatKhauCu(String matKhauCu) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), matKhauCu);

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Thực hiện lưu mật khẩu
                            luuMatKhauMoi();
                        } else {
                            // Mật khẩu cũ không chính xác, thông báo cho người dùng
                            Toast.makeText(DoiMatKhau_Activity.this, "Mật Khẩu Cũ Không Đúng. Vui Lòng Nhập Lại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private boolean kiemTraMatKhauMoi(String matKhauMoi) {
        // Kiểm tra mật khẩu mới có đủ 6 ký tự không
        if (matKhauMoi.length() < 6) {
            return false;
        }

        // Kiểm tra mật khẩu mới có chứa ký tự viết hoa không
        boolean hasUppercase = false;
        for (char ch : matKhauMoi.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
                break;
            }
        }
        return hasUppercase;
    }

    private void luuMatKhauMoi() {
        // Thực hiện lưu mật khẩu mới vào cơ sở dữ liệu hoặc nơi lưu trữ khác
        String newpassword = edtMatKhauNew.getText().toString().trim();
        //show dialog
        progressDialog.show();
        progressDialog.setMessage("Đang Kiểm Tra....");
        //sử dụng update password của firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = "SOME-SECURE-PASSWORD";

        user.updatePassword(newpassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Thông Báo Lưu mật khẩu mới thành công
                            Toast.makeText(DoiMatKhau_Activity.this, "Lưu mật khẩu mới thành công", Toast.LENGTH_SHORT).show();
                            // tắt dialog
                            progressDialog.dismiss();
                        }
                    }
                });
    }
    @Override
    //Hàm thoát ra trên toorbal
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
