package Edit_Detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Edit_DanhSachLopHoc_Activity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private String lopHocId;
    //Hàm lấy id từ firebase mà không phải tạo tránh trường hợp null
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String userId = currentUser.getUid();

    //Hạm gọi id lớp học và user từ intent trong adapter LOPHOCLOPDAY
    private void intent(){
        // Nhận ID của lớp học và ID của người dùng từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            lopHocId = intent.getStringExtra("LopMoi");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Hàm toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_danhsachlophoc);
        Toolbar toolbar = findViewById(R.id.toolbar_taoyeucautimgiasu);
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

        // Khởi tạo DatabaseReference để tham chiếu đến "LopMoi" node trong Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("LopMoi");
        intent();//Hàm lấy thông tin id của idlop và id user;
        loadthongtin(userId,lopHocId);       //Hàm load thông tin mà chưa chạy được (Muốn hiện thông tin lên edt)
        addEvent();
    }

    private void addEvent() {
        Button btn_taothaydoi= findViewById(R.id.btn_taoyeucau);
        btn_taothaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hàm cập nhật dữ liệu
                intent();
                capNhatDuLieuLenFirebase(lopHocId,userId);
            }
        });
    }


    //Hàm quay ra trên toolbar
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void loadthongtin(String userId,String lopHocId){
            // Truy vấn dữ liệu từ Firebase dựa trên ID của lớp học
            databaseReference.child(userId).child(lopHocId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Kiểm tra xem dữ liệu có tồn tại không
                    if (dataSnapshot.exists()) {
                        // Lấy dữ liệu từ dataSnapshot và hiển thị lên các EditText
                        LopHoc lopHoc = dataSnapshot.getValue(LopHoc.class);
                        if (lopHoc != null) {

                            EditText edtEmail = findViewById(R.id.edt_email);
                            edtEmail.setText(lopHoc.getEmail());

                            EditText edtTieuDe = findViewById(R.id.edt_tieude);
                            edtTieuDe.setText(lopHoc.getTitle());

                            EditText edtDiaDiemDay = findViewById(R.id.edt_diadiemday);
                            edtDiaDiemDay.setText(lopHoc.getDiaDiemDay());

                            EditText edtNgayBatDau = findViewById(R.id.edt_ngaybatdau);
                            edtNgayBatDau.setText(lopHoc.getNgayBatDau());

                            EditText edtMonHoc = findViewById(R.id.edt_monhoc);
                            edtMonHoc.setText(lopHoc.getMonHoc());

                            EditText edtGioMoMoiBuoi = findViewById(R.id.edt_giomoibuoi);
                            edtGioMoMoiBuoi.setText(lopHoc.getGioMoiBuoi());

                            EditText edtGioiTinhHocVien = findViewById(R.id.edt_gioitinhhocvien);
                            edtGioiTinhHocVien.setText(lopHoc.getGioiTinhHocVien());

                            EditText edtHocPhi = findViewById(R.id.edt_hocphi);
                            edtHocPhi.setText(lopHoc.getHocPhi());

                            EditText edtSoBuoiTrongTuan = findViewById(R.id.edt_sobuoitrongtuan);
                            edtSoBuoiTrongTuan.setText(lopHoc.getSoBuoiTrongTuan());

                            EditText edtGioiTinh = findViewById(R.id.edt_gioitinh);
                            edtGioiTinh.setText(lopHoc.getGioiTinh());

                            EditText edtHocPhiTheo = findViewById(R.id.edt_hocphitheo);
                            edtHocPhiTheo.setText(lopHoc.getHocPhiTheo());

                            EditText edtMoTaChiTiet = findViewById(R.id.edt_motachitiet);
                            edtMoTaChiTiet.setText(lopHoc.getMoTaChiTiet());
                        }
                    } else {
                        // Hiển thị thông báo nếu không tìm thấy dữ liệu
                        Toast.makeText(Edit_DanhSachLopHoc_Activity.this, "Không tìm thấy dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi truy vấn dữ liệu thất bại
                    Toast.makeText(Edit_DanhSachLopHoc_Activity.this, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    // Phương thức để cập nhật dữ liệu lên Firebase
    private void capNhatDuLieuLenFirebase( String lopHocId,String userId) {
        // Lấy các giá trị từ các EditText
        String email = ((EditText) findViewById(R.id.edt_email)).getText().toString();
        String tieuDe = ((EditText) findViewById(R.id.edt_tieude)).getText().toString();
        String diaDiemDay = ((EditText) findViewById(R.id.edt_diadiemday)).getText().toString();
        String ngayBatDau = ((EditText) findViewById(R.id.edt_ngaybatdau)).getText().toString();
        String monHoc = ((EditText) findViewById(R.id.edt_monhoc)).getText().toString();
        String gioMoMoiBuoi = ((EditText) findViewById(R.id.edt_giomoibuoi)).getText().toString();
        String gioiTinhHocVien = ((EditText) findViewById(R.id.edt_gioitinhhocvien)).getText().toString();
        String hocPhi = ((EditText) findViewById(R.id.edt_hocphi)).getText().toString();
        String soBuoiTrongTuan = ((EditText) findViewById(R.id.edt_sobuoitrongtuan)).getText().toString();
        String gioiTinh = ((EditText) findViewById(R.id.edt_gioitinh)).getText().toString();
        String hocPhiTheo = ((EditText) findViewById(R.id.edt_hocphitheo)).getText().toString();
        String moTaChiTiet = ((EditText) findViewById(R.id.edt_motachitiet)).getText().toString();

        // Tạo một đối tượng LopHoc mới với các giá trị đã lấy
        LopHoc lopHoc = new LopHoc(email, tieuDe, diaDiemDay, ngayBatDau, gioMoMoiBuoi,
                monHoc, gioiTinhHocVien, hocPhi, soBuoiTrongTuan,
                gioiTinh, hocPhiTheo, moTaChiTiet);


        // Đẩy dữ liệu lên Firebase theo ID của lớp học,dưới id user
        databaseReference.child(userId).child(lopHocId).setValue(lopHoc)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Cập nhật thành công
                        Toast.makeText(Edit_DanhSachLopHoc_Activity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Cập nhật thất bại
                        Toast.makeText(Edit_DanhSachLopHoc_Activity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
