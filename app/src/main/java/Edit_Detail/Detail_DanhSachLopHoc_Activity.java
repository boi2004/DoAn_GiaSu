package Edit_Detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doan_giasu.Model.LopHoc;
import com.example.doan_giasu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Detail_DanhSachLopHoc_Activity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private String lopHocId;
    //Hàm lấy id từ firebase mà không phải tạo tránh trường hợp null
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String userId = currentUser.getUid();
    private void intent(){
        // Nhận ID của lớp học và ID của người dùng từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            lopHocId = intent.getStringExtra("LopMoi");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_danhsachlophoc);
        Toolbar toolbar = findViewById(R.id.toolbar_dangkylamgiasu1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết thông tin lớp học");
        int white = getResources().getColor(android.R.color.white);
        toolbar.setTitleTextColor(white);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("LopMoi");
        intent();
        loadThongTinLopHoc(userId,lopHocId);
    }
    //Hàm thoát ra trên toorbal
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void loadThongTinLopHoc(String userId, String lopHocId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LopMoi").child(userId).child(lopHocId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String tieuDe = dataSnapshot.child("title").getValue(String.class);
                    String maLop = dataSnapshot.getKey();
                    String hoc = dataSnapshot.child("moTaChiTiet").getValue(String.class);
                    String tenGiaSu = dataSnapshot.child("hocPhi").getValue(String.class);
                    String monHoc = dataSnapshot.child("monHoc").getValue(String.class);
                    String diaDiem = dataSnapshot.child("diaDiemDay").getValue(String.class);
                    String tuanHoc = dataSnapshot.child("soBuoiTrongTuan").getValue(String.class);
                    String sdt =dataSnapshot.child("gioiTinhHocVien").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String gioitinh = dataSnapshot.child("gioiTinh").getValue(String.class);

                    // Ánh xạ các TextView từ layout XML
                    TextView txtTieuDe = findViewById(R.id.txt_tieude);
                    TextView txtMaLop = findViewById(R.id.txt_malop);
                    TextView txtHoc = findViewById(R.id.txt_hoc);
                    TextView txtTenGiaSu = findViewById(R.id.txt_ten_gia_su);
                    TextView txtMonHoc = findViewById(R.id.txt_monhoc);
                    TextView txtDiaDiem = findViewById(R.id.txt_diadiem);
                    TextView txtTuanHoc = findViewById(R.id.txt_tuanhoc);
                    TextView txtsdt = findViewById(R.id.txt_sodienthoai_detail_danhsachlopday);
                    TextView txtemail = findViewById(R.id.txt_gmail_detail_danhsachlopday);
                    TextView txtgioitinh = findViewById(R.id.txt_gioitinh_detail_danhsachlopday);

                    // Hiển thị thông tin lên các TextView
                    txtTieuDe.setText(tieuDe);
                    txtMaLop.setText(maLop);
                    txtHoc.setText(hoc);
                    txtTenGiaSu.setText(tenGiaSu);
                    txtMonHoc.setText(monHoc);
                    txtDiaDiem.setText(diaDiem);
                    txtTuanHoc.setText(tuanHoc);
                    txtsdt.setText(sdt);
                    txtemail.setText(email);
                    txtgioitinh.setText(gioitinh);
                } else {
                    Toast.makeText(Detail_DanhSachLopHoc_Activity.this, "Không tìm thấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Detail_DanhSachLopHoc_Activity.this, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}