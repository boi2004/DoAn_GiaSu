package Activity_Menu;

import static com.example.doan_giasu.R.id.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.doan_giasu.Model.LopHoc;
import com.example.doan_giasu.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class TaoYeuCauTimGiaSu_Activity extends AppCompatActivity {
    EditText edt_email, edt_tieude, edt_diadiemday, edt_ngaybatdau, edt_giomoibuoi, edt_monhoc, edt_gioitinhhocvien,
    edt_hocphi, edt_sobuoitrongtuan, edt_gioitinh, edt_hocphitheo, edt_motachitiet;
    Button btn_taoyeucau;

    //Hàm chạy sự kiện
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_yeu_cau_tim_gia_su);
        Toolbar toolbar = findViewById(toolbar_taoyeucautimgiasu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tạo yêu cầu làm gia sư");
        int white = getResources().getColor(android.R.color.white);
        toolbar.setTitleTextColor(white);   //Trong đoạn mã trên, toolbar.setTitleTextColor(white) sẽ đặt màu trắng cho tiêu đề của Toolbar.
        Controls();
        Events();
        LuuDuLieu();//Hạm đẩy dữ liệu lên firebase
    }

    public void LuuDuLieu(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        DatabaseReference lopmoiRef = FirebaseDatabase.getInstance().getReference().child("LopMoi").child(userID);
        lopmoiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Lấy dữ liệu từ snapshot
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String tieude = dataSnapshot.child("tieude").getValue(String.class);
                    String diadiemday = dataSnapshot.child("diadiemday").getValue(String.class);
                    String ngaybatdau = dataSnapshot.child("ngaybatdau").getValue(String.class);
                    String monhoc = dataSnapshot.child("monhoc").getValue(String.class);
                    String giomoibuoi = dataSnapshot.child("giomoibuoi").getValue(String.class);
                    String gioitinhhocvien = dataSnapshot.child("gioitinhhocvien").getValue(String.class);
                    String hocphi = dataSnapshot.child("hocphi").getValue(String.class);
                    String sobuoitrongtuan = dataSnapshot.child("sobuoitrongtuan").getValue(String.class);
                    String gioitinh = dataSnapshot.child("gioitinh").getValue(String.class);
                    String hocphitheo = dataSnapshot.child("hocphitheo").getValue(String.class);
                    String motachitiet = dataSnapshot.child("motachitiet").getValue(String.class);

                    // Hiển thị dữ liệu lên các EditText
                    edt_email.setText(email);
                    edt_tieude.setText(tieude);
                    edt_diadiemday.setText(diadiemday);
                    edt_ngaybatdau.setText(ngaybatdau);
                    edt_monhoc.setText(monhoc);
                    edt_giomoibuoi.setText(giomoibuoi);
                    edt_gioitinhhocvien.setText(gioitinhhocvien);
                    edt_hocphi.setText(hocphi);
                    edt_sobuoitrongtuan.setText(sobuoitrongtuan);
                    edt_gioitinh.setText(gioitinh);
                    edt_hocphitheo.setText(hocphitheo);
                    edt_motachitiet.setText(motachitiet);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });
    }

    private void Events() {
        edt_monhoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenumonhoc();
            }
        });
        edt_giomoibuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenugiomoibuoi();
            }
        });
        edt_gioitinhhocvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenugioitinhhocvien();
            }
        });
        edt_gioitinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenugioitinh();
            }
        });
        edt_hocphitheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuhocphitheo();
            }
        });
        btn_taoyeucau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy giá trị từ các trường EditText
               /* EditText edt_email, edt_tieude, edt_diadiemday, edt_ngaybatdau, edt_giomoibuoi, edt_monhoc, edt_gioitinhhocvien,
                        edt_hocphi, edt_sobuoitrongtuan, edt_gioitinh, edt_hocphitheo, edt_motachitiet;*/
                String Email = edt_email.getText().toString().trim();
                String Title = edt_tieude.getText().toString().trim();
                String DiaDiemDay = edt_diadiemday.getText().toString().trim();
                String NgayBatDau = edt_ngaybatdau.getText().toString().trim();
                String GioMoiBuoi = edt_giomoibuoi.getText().toString().trim();
                String MonHoc = edt_monhoc.getText().toString().trim();
                String GioiTinhHocVien = edt_gioitinhhocvien.getText().toString().trim();
                String HocPhi = edt_hocphi.getText().toString().trim();
                String SoBuoiTrongTuan = edt_sobuoitrongtuan.getText().toString().trim();
                String GioiTinh = edt_gioitinh.getText().toString().trim();
                String HocPhiTheo = edt_hocphitheo.getText().toString().trim();
                String MoTaChiTiet = edt_motachitiet.getText().toString().trim();

                //Kiểm tra xem có thông tin nào bị thiếu không
                if(Email.isEmpty()||Title.isEmpty()||DiaDiemDay.isEmpty()||NgayBatDau.isEmpty()||
                        GioMoiBuoi.isEmpty()||MonHoc.isEmpty()||GioiTinhHocVien.isEmpty()||HocPhi.isEmpty()||
                        SoBuoiTrongTuan.isEmpty()||GioiTinh.isEmpty()||HocPhiTheo.isEmpty()||MoTaChiTiet.isEmpty()){
                    Toast.makeText(TaoYeuCauTimGiaSu_Activity.this,"Vui Lòng Nhập Đầy Đủ Thông Tin",Toast.LENGTH_LONG).show();
                    return;
                }
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                luuThongTinLopMoi();
            }
        });

    }

    private void luuThongTinLopMoi() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUserLopMoi = mAuth.getCurrentUser();

        if(currentUserLopMoi != null){
            String userID = currentUserLopMoi.getUid(); // Lấy ID của người dùng hiện tại

            // Tạo một ID duy nhất cho lớp học mới
            String lopHocId = UUID.randomUUID().toString();

            // Lưu thông tin của lớp học dưới một nhánh mới với ID duy nhất
            taoMoiThongTinLopHoc(userID, lopHocId);
        }
    }

    private void taoMoiThongTinLopHoc(String userId, String lopHocId) {
        // Lấy thông tin của lớp học từ EditText
        String Email = edt_email.getText().toString().trim();
        String Title = edt_tieude.getText().toString().trim();
        String DiaDiemDay = edt_diadiemday.getText().toString().trim();
        String NgayBatDau = edt_ngaybatdau.getText().toString().trim();
        String GioMoiBuoi = edt_giomoibuoi.getText().toString().trim();
        String MonHoc = edt_monhoc.getText().toString().trim();
        String GioiTinhHocVien = edt_gioitinhhocvien.getText().toString().trim();
        String HocPhi = edt_hocphi.getText().toString().trim();
        String SoBuoiTrongTuan = edt_sobuoitrongtuan.getText().toString().trim();
        String GioiTinh = edt_gioitinh.getText().toString().trim();
        String HocPhiTheo = edt_hocphitheo.getText().toString().trim();
        String MoTaChiTiet = edt_motachitiet.getText().toString().trim();

        // Tạo đối tượng LopHoc mới từ thông tin mới
        LopHoc lopHoc = new LopHoc(Email, Title, DiaDiemDay, NgayBatDau, GioMoiBuoi, MonHoc, GioiTinhHocVien, HocPhi, SoBuoiTrongTuan, GioiTinh, HocPhiTheo, MoTaChiTiet);

        // Lưu thông tin LopHoc dưới một nhánh mới với ID duy nhất
        FirebaseDatabase.getInstance().getReference("LopMoi").child(userId).child(lopHocId).setValue(lopHoc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(TaoYeuCauTimGiaSu_Activity.this, "Tạo lớp học thành công", Toast.LENGTH_SHORT).show();
                        // Xử lý khi tạo lớp học thành công, ví dụ: chuyển về màn hình chính
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TaoYeuCauTimGiaSu_Activity.this, "Tạo lớp học thất bại", Toast.LENGTH_SHORT).show();
                        // Xử lý khi tạo lớp học thất bại
                    }
                });
    }
    private void capNhatThongTinLopMoi(String userID) {
        // Lây thông tin mới từ các EditText
        String Email = edt_email.getText().toString().trim();
        String Title = edt_tieude.getText().toString().trim();
        String DiaDiemDay = edt_diadiemday.getText().toString().trim();
        String NgayBatDau = edt_ngaybatdau.getText().toString().trim();
        String GioMoiBuoi = edt_giomoibuoi.getText().toString().trim();
        String MonHoc = edt_monhoc.getText().toString().trim();
        String GioiTinhHocVien = edt_gioitinhhocvien.getText().toString().trim();
        String HocPhi = edt_hocphi.getText().toString().trim();
        String SoBuoiTrongTuan = edt_sobuoitrongtuan.getText().toString().trim();
        String GioiTinh = edt_gioitinh.getText().toString().trim();
        String HocPhiTheo = edt_hocphitheo.getText().toString().trim();
        String MoTaChiTiet = edt_motachitiet.getText().toString().trim();

        // Tạo đối tượng GiaSu mới từ thông tin mới
        LopHoc lopHoc = new LopHoc(Email,Title, DiaDiemDay, NgayBatDau,GioMoiBuoi,MonHoc,GioiTinhHocVien,HocPhi,SoBuoiTrongTuan,GioiTinh,HocPhiTheo,MoTaChiTiet);

        // Cập nhật thông tin GiaSu lên Firebase Realtime Database
        FirebaseDatabase.getInstance().getReference("LopHoc").child(userID).setValue(lopHoc)                ///Cập nật dữ liệu
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(TaoYeuCauTimGiaSu_Activity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        // Xử lý khi cập nhật thành công, ví dụ: chuyển về màn hình chính
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TaoYeuCauTimGiaSu_Activity.this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                        // Xử lý khi cập nhật thất bại
                    }
                });
    }

    private void ShowMenumonhoc() {
        PopupMenu popupMenu = new PopupMenu(this, edt_monhoc);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_monhoc, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menulaptrinh:
                        edt_monhoc.setText("Lập trình");
                        break;
                    case R.id.menulaptrinhandroid:
                        edt_monhoc.setText("Lập trình Android");
                        break;
                    case R.id.menulaptrinhaspnet:
                        edt_monhoc.setText("Lập trình ASP.NET");
                        break;
                    case R.id.menulaptrinhautolisp:
                        edt_monhoc.setText("Lập trình Autolisp");
                        break;
                    case R.id.menulaptrinhc:
                        edt_monhoc.setText("Lập trình C, C++, C#");
                        break;
                    case R.id.menulaptrinhcacngonngukhac:
                        edt_monhoc.setText("Lập trình các ngôn ngữ khác");
                        break;
                    case R.id.menulaptrinhgame:
                        edt_monhoc.setText("Lập trình Game");
                        break;
                    case R.id.menulaptrinhios:
                        edt_monhoc.setText("Lập trình IOS");
                        break;
                    case R.id.menulaptrinhjava:
                        edt_monhoc.setText("Lập trình Java");
                        break;
                    case R.id.menulaptrinhjavaspring:
                        edt_monhoc.setText("Lập trình java spring");
                        break;
                    case R.id.menulaptrinhojective:
                        edt_monhoc.setText("Lập trình Objective, SWIFT");
                        break;
                    case R.id.menulaptrinhonline:
                        edt_monhoc.setText("Lập trình online");
                        break;
                    case R.id.menulaptrinhpascal:
                        edt_monhoc.setText("Lập trình Pascal");
                        break;
                    case R.id.menulaptrinhpython:
                        edt_monhoc.setText("Lập trình Python");
                        break;
                    case R.id.menulaptrinhsharepoint:
                        edt_monhoc.setText("Lập trình sharepoint");
                        break;
                    case R.id.menulaptrinhsql:
                        edt_monhoc.setText("Lập trình SQL");
                        break;
                    case R.id.menulaptrinhdidong:
                        edt_monhoc.setText("Lập trình Ứng dụng di động");
                        break;
                    case R.id.menulaptrinhvba:
                        edt_monhoc.setText("Lập trình VBA");
                        break;
                    case R.id.menulaptrinhweb:
                        edt_monhoc.setText("Lập trình web");
                        break;
                    case R.id.menulaptrinhphp:
                        edt_monhoc.setText("Lập trình web PHP");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();

    }
    private void ShowMenugiomoibuoi() {
        PopupMenu popupMenu = new PopupMenu(this, edt_giomoibuoi);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_gio, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menun:
                        edt_giomoibuoi.setText("------");
                        break;
                    case menu30p:
                        edt_giomoibuoi.setText("30 phút");
                        break;
                    case menu45p:
                        edt_giomoibuoi.setText("45 phút");
                        break;
                    case menu60p:
                        edt_giomoibuoi.setText("60 phút");
                    case menun90p:
                        edt_giomoibuoi.setText("90 phút");
                        break;
                    case menu2h:
                        edt_giomoibuoi.setText("2 giờ");
                        break;
                    case menu25h:
                        edt_giomoibuoi.setText("2.5 giờ");
                    case menu3h:
                        edt_giomoibuoi.setText("3 giờ");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();


    }
    private void ShowMenuhocphitheo() {
        PopupMenu popupMenu = new PopupMenu(this, edt_hocphitheo);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_hocphi, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case menuthang:
                        edt_hocphitheo.setText("Tháng");
                        break;
                    case menubuoi:
                        edt_hocphitheo.setText("Buổi");
                        break;
                    case menutuan:
                        edt_hocphitheo.setText("Tuần");
                        break;
                    case menugio:
                        edt_hocphitheo.setText("Giờ");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();


    }
    private void ShowMenugioitinh() {
        PopupMenu popupMenu = new PopupMenu(this, edt_gioitinh);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_gioitinh, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case menunamnu:
                        edt_gioitinh.setText("Nam/nữ");
                        break;
                    case menunam:
                        edt_gioitinh.setText("Nam");
                        break;
                    case menunu:
                        edt_gioitinh.setText("nữ");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();


    }
    private void ShowMenugioitinhhocvien() {
        PopupMenu popupMenu = new PopupMenu(this, edt_gioitinhhocvien);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_gioitinh, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case menunamnu:
                        edt_gioitinhhocvien.setText("Nam/nữ");
                        break;
                    case menunam:
                        edt_gioitinhhocvien.setText("Nam");
                        break;
                    case menunu:
                        edt_gioitinhhocvien.setText("nữ");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    //Hàm ánh xạ
    private void Controls() {
        edt_email = findViewById(R.id.edt_email);
        edt_tieude = findViewById(R.id.edt_tieude);
        edt_diadiemday = findViewById(R.id.edt_diadiemday);
        edt_ngaybatdau = findViewById(R.id.edt_ngaybatdau);
        edt_giomoibuoi = findViewById(R.id.edt_giomoibuoi);
        edt_monhoc = findViewById(R.id.edt_monhoc);
        edt_gioitinhhocvien = findViewById(R.id.edt_gioitinhhocvien);
        edt_hocphi = findViewById(R.id.edt_hocphi);
        edt_sobuoitrongtuan = findViewById(R.id.edt_sobuoitrongtuan);
        edt_gioitinh = findViewById(R.id.edt_gioitinh);
        edt_hocphitheo = findViewById(R.id.edt_hocphitheo);
        edt_motachitiet = findViewById(R.id.edt_motachitiet);
        btn_taoyeucau = findViewById(R.id.btn_taoyeucau);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}