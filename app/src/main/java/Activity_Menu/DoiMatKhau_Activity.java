package Activity_Menu;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_giasu.R;

public class DoiMatKhau_Activity extends AppCompatActivity {
    EditText edtTaiKhoan, edtMatKhauNew, edtMatKhauNewAgain;
   Button btnLuu, btnQuayLai;
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
        // Khai báo các EditText
        edtTaiKhoan = findViewById(R.id.editT_matkhaunew_datlaimatkhau);
        edtMatKhauNew = findViewById(R.id.editTextText3);
        edtMatKhauNewAgain = findViewById(R.id.editT_matkhaunewagain_datlaimatkhau);

        // Khai báo các Button
        btnLuu = findViewById(R.id.btn_luu_datlaimatkhau);
        btnQuayLai = findViewById(R.id.button2);


    }

    private void addEvents() {
        // Các sự kiện xử lý khi các Button được click
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matKhauCu = edtTaiKhoan.getText().toString().trim();
                String matKhauMoi = edtMatKhauNew.getText().toString().trim();
                String nhapLaiMatKhau = edtMatKhauNewAgain.getText().toString().trim();

//                 Kiểm tra mật khẩu cũ trùng khớp
//                if (!kiemTraMatKhauCu(matKhauCu)) {
//                    edtTaiKhoan.setError("Mật khẩu cũ không đúng");
//                    return;
//                }

                // Kiểm tra mật khẩu mới phải khác mật khẩu cũ
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

                // Nếu tất cả điều kiện đều đúng, thực hiện lưu mật khẩu mới
                luuMatKhauMoi(matKhauMoi);
            }
        });

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi nhấn nút "Quay Lại"
                finish();
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


     private boolean kiemTraMatKhauCu(String matKhauCu) {
        // Thực hiện kiểm tra mật khẩu cũ, bạn cần thay thế bằng logic kiểm tra thực tế
        return matKhauCu.equals("matkhaucu");
    }
    // // Kiểm tra mật khẩu mới đáp ứng yêu cầu độ dài và thành phần ký tự
    private boolean kiemTraMatKhauMoi(String matKhauMoi) {
        return matKhauMoi.length() >= 6 && chuaChuSoChuCaiKyTuDacBiet(matKhauMoi);
    }
    // // Kiểm tra mật khẩu mới đáp ứng yêu cầu độ dài và thành phần ký tự
    private boolean chuaChuSoChuCaiKyTuDacBiet(String matKhau) {
        return matKhau.matches(".*\\d.*") && matKhau.matches(".*[a-zA-Z].*") && matKhau.matches(".*[!@$%].*");
    }

    private void luuMatKhauMoi(String matKhauMoi) {
        // Thực hiện lưu mật khẩu mới vào cơ sở dữ liệu hoặc nơi lưu trữ khác
        Toast.makeText(DoiMatKhau_Activity.this, "Lưu mật khẩu mới thành công", Toast.LENGTH_SHORT).show();
    }

}
