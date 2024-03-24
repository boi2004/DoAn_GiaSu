package Activity_Menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doan_giasu.Model.GiaSu;
import com.example.doan_giasu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class DangKyLamGiaSu_Activity extends AppCompatActivity {
    EditText edt_monhoc, edt_nghenghiep, edt_thanhpho, edt_hovaten, edt_diachi, edt_namsinh, edt_email, edt_gioithieubanthan, edt_sodienthoai, edt_truongdahoc, edt_namtotnghiep;
    Button dangkylamgiasu;
    ImageButton img_giasu;
    Uri imageUri; // Đường dẫn của ảnh đã chọn
    // private StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String userId; // Khai báo biến userId ở cấp độ lớp


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dangkylam_giasu);
        Toolbar toolbar = findViewById(R.id.toolbar_dangkylamgiasu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đăng ký làm gia sư");
        int white = getResources().getColor(android.R.color.white);
        toolbar.setTitleTextColor(white);   //Trong đoạn mã trên, toolbar.setTitleTextColor(white) sẽ đặt màu trắng cho tiêu đề của Toolbar.

        addControls();
        addEvents();
        LuuDuLieu();
    }

    private void addEvents() {
        edt_monhoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuMonhoc();
            }
        });
        edt_nghenghiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuNghenghiep();
            }
        });
        edt_thanhpho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuThanhpho();
            }
        });
        edt_truongdahoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuTruongdahoc();
            }
        });
        edt_namtotnghiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuNamtotnghiep();
            }
        });
        dangkylamgiasu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị từ các trường EditText
                String monhoc = edt_monhoc.getText().toString().trim();
                String nghenghiep = edt_nghenghiep.getText().toString().trim();
                String thanhpho = edt_thanhpho.getText().toString().trim();
                String hovaten = edt_hovaten.getText().toString().trim();
                String diachi = edt_diachi.getText().toString().trim();
                String namsinh = edt_namsinh.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                String gioithieubanthan = edt_gioithieubanthan.getText().toString().trim();
                String sodienthoai = edt_sodienthoai.getText().toString().trim();
                String truongdahoc = edt_truongdahoc.getText().toString().trim();
                String namtotnghiep = edt_namtotnghiep.getText().toString().trim();
                // Kiểm tra xem có thông tin nào bị thiếu không
                if (monhoc.isEmpty() || nghenghiep.isEmpty() || thanhpho.isEmpty() || hovaten.isEmpty() ||
                        diachi.isEmpty() || namsinh.isEmpty() || email.isEmpty() || gioithieubanthan.isEmpty() ||
                        sodienthoai.isEmpty() || truongdahoc.isEmpty() || namtotnghiep.isEmpty()) {
                    Toast.makeText(DangKyLamGiaSu_Activity.this, "Vui lòng nhập đầy đủ thông tin ", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                uploadImageToFirebaseStorage(userId, imageUri);
            }
        });
        img_giasu.setOnClickListener(new View.OnClickListener() {   //Nút  luu hình ảnh

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // Set ảnh cho ImageButton
            img_giasu.setImageURI(imageUri);
            // Gọi hàm uploadImageToFirebaseStorage(userId) để tải ảnh lên Firebase Storage
        }
    }

    //hàm thoát toolbar
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void luuThongTinGiaSu(String urlAvatar) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid(); // Lấy ID của người dùng hiện tại

            // Kiểm tra xem người dùng đã có thông tin gia sư trong cơ sở dữ liệu chưa
            FirebaseDatabase.getInstance().getReference("GiaSu").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Người dùng đã có thông tin gia sư trong cơ sở dữ liệu, cập nhật thông tin
                        capNhatThongTinGiaSu(userId, urlAvatar);
                    } else {
                        // Người dùng chưa có thông tin gia sư trong cơ sở dữ liệu, tạo mới thông tin
                        taoMoiThongTinGiaSu(userId, urlAvatar);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi có lỗi xảy ra
                    Toast.makeText(DangKyLamGiaSu_Activity.this, "Đã xảy ra lỗi, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Người dùng chưa đăng nhập, xử lý tùy thuộc vào yêu cầu của bạn
        }
    }

    // Phương thức để cập nhật thông tin gia sư
    private void capNhatThongTinGiaSu(String userId, String urlAvatar) {
        // Lấy thông tin mới từ các EditText
        String monhoc = edt_monhoc.getText().toString().trim();
        String nghenghiep = edt_nghenghiep.getText().toString().trim();
        String thanhpho = edt_thanhpho.getText().toString().trim();
        String hovaten = edt_hovaten.getText().toString().trim();
        String diachi = edt_diachi.getText().toString().trim();
        String namsinh = edt_namsinh.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String gioithieubanthan = edt_gioithieubanthan.getText().toString().trim();
        String sodienthoai = edt_sodienthoai.getText().toString().trim();
        String truongdahoc = edt_truongdahoc.getText().toString().trim();
        String namtotnghiep = edt_namtotnghiep.getText().toString().trim();

// Tạo đối tượng GiaSu mới từ thông tin mới
        GiaSu giaSu = new GiaSu(nghenghiep, monhoc, thanhpho, hovaten, diachi,namsinh, email, gioithieubanthan, sodienthoai, truongdahoc,namtotnghiep, urlAvatar);

// Cập nhật thông tin GiaSu lên Firebase Realtime Database
        FirebaseDatabase.getInstance().getReference("GiaSu").child(userId).setValue(giaSu)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DangKyLamGiaSu_Activity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        // Xử lý khi cập nhật thành công, ví dụ: chuyển về màn hình chính
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DangKyLamGiaSu_Activity.this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                        // Xử lý khi cập nhật thất bại
                    }
                });
    }

    // Phương thức để tạo mới thông tin gia sư
    private void taoMoiThongTinGiaSu(String userId, String urlAvatar) {
        // Lấy thông tin từ các EditText
        String monhoc = edt_monhoc.getText().toString().trim();
        String nghenghiep = edt_nghenghiep.getText().toString().trim();
        String thanhpho = edt_thanhpho.getText().toString().trim();
        String hovaten = edt_hovaten.getText().toString().trim();
        String diachi = edt_diachi.getText().toString().trim();
        String namsinh = edt_namsinh.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String gioithieubanthan = edt_gioithieubanthan.getText().toString().trim();
        String sodienthoai = edt_sodienthoai.getText().toString().trim();
        String truongdahoc = edt_truongdahoc.getText().toString().trim();
        String namtotnghiep = edt_namtotnghiep.getText().toString().trim();

        // Tạo đối tượng GiaSu mới từ thông tin
        GiaSu giaSu = new GiaSu(nghenghiep, monhoc, thanhpho, hovaten, diachi,namsinh, email, gioithieubanthan, sodienthoai, truongdahoc, namtotnghiep, urlAvatar);

        // Lưu thông tin GiaSu mới vào Firebase Realtime Database
        FirebaseDatabase.getInstance().getReference("GiaSu").child(userId).setValue(giaSu)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DangKyLamGiaSu_Activity.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DangKyLamGiaSu_Activity.this, "Lưu thông tin thất bại", Toast.LENGTH_SHORT).show();
                        // Xử lý khi cập nhật thất bại
                    }
                });
    }

    private void uploadImageToFirebaseStorage(String userId, Uri imageUri) {
        if (imageUri != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference riversRef = storageRef.child("images/" + imageUri.getLastPathSegment());
            riversRef.putFile(imageUri).continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return riversRef.getDownloadUrl();
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        luuThongTinGiaSu(downloadUri.toString());
                    }
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(DangKyLamGiaSu_Activity.this, "Tải ảnh lên thất bại", Toast.LENGTH_SHORT).show();
            });
        }
    }

    // Tham chiếu đến node chứa dữ liệu trên Realtime Database
    public void LuuDuLieu() {
// Lấy reference đến node chứa dữ liệu gia sư từ Realtime Database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        DatabaseReference giaSuRef = FirebaseDatabase.getInstance().getReference().child("GiaSu").child(userID);
        giaSuRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Lấy dữ liệu từ snapshot
                    String diaChi = dataSnapshot.child("diaChi").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String gioiThieuBanThan = dataSnapshot.child("gioiThieuBanThan").getValue(String.class);
                    String hoVaTen = dataSnapshot.child("hoVaTen").getValue(String.class);
                    String monHoc = dataSnapshot.child("monHoc").getValue(String.class);
                    String namSinh = dataSnapshot.child("namSinh").getValue(String.class);
                    String ngheNghiep = dataSnapshot.child("ngheNghiep").getValue(String.class);
                    String soDienThoai = dataSnapshot.child("soDienThoai").getValue(String.class);
                    String thanhPho = dataSnapshot.child("thanhPho").getValue(String.class);
                    String truongDaHoc = dataSnapshot.child("truongDaHoc").getValue(String.class);
                    String namTotNghiep = dataSnapshot.child("namTotNghiep").getValue(String.class);

                    // Hiển thị dữ liệu lên các EditText
                    edt_diachi.setText(diaChi);
                    edt_email.setText(email);
                    edt_gioithieubanthan.setText(gioiThieuBanThan);
                    edt_hovaten.setText(hoVaTen);
                    edt_monhoc.setText(monHoc);
                    edt_namsinh.setText(namSinh);
                    edt_nghenghiep.setText(ngheNghiep);
                    edt_sodienthoai.setText(soDienThoai);
                    edt_thanhpho.setText(thanhPho);
                    edt_truongdahoc.setText(truongDaHoc);
                    edt_namtotnghiep.setText(namTotNghiep);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });
    }


    private void ShowMenuMonhoc() {
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

    private void ShowMenuNghenghiep() {
        PopupMenu popupMenu = new PopupMenu(this, edt_nghenghiep);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_nghenghiep, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menunghetudo:
                        edt_nghenghiep.setText("Nghề tự do");
                        break;
                    case R.id.menusinhvien:
                        edt_nghenghiep.setText("Sinh viên");
                        break;
                    case R.id.menugiaovien:
                        edt_nghenghiep.setText("Giáo viên");
                        break;
                    case R.id.menudatotnghiep:
                        edt_nghenghiep.setText("Đã tốt nghiệp");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();


    }

    private void ShowMenuThanhpho() {
        PopupMenu popupMenu = new PopupMenu(this, edt_thanhpho);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_thanhpho, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuhochiminh:
                        edt_thanhpho.setText("Hồ Chí Minh");
                        break;
                    case R.id.menuhanoi:
                        edt_thanhpho.setText("Hà Nội");
                        break;
                    case R.id.menudanang:
                        edt_thanhpho.setText("Đà Nẵng");
                        break;
                    case R.id.menukhanhhoa:
                        edt_thanhpho.setText("Khánh Hòa");
                        break;
                    case R.id.menulamdong:
                        edt_thanhpho.setText("Lâm Đồng");
                        break;
                    case R.id.menubariavungtau:
                        edt_thanhpho.setText("Bà Rịa-Vũng Tàu");
                        break;
                    case R.id.menucantho:
                        edt_thanhpho.setText("Cần Thơ");
                        break;
                    case R.id.menubinhphuoc:
                        edt_thanhpho.setText("Bình Phước");
                        break;
                    case R.id.menucamau:
                        edt_thanhpho.setText("Cà Mau");
                        break;
                    case R.id.menuquangngai:
                        edt_thanhpho.setText("Quãng Ngãi");
                        break;
                    case R.id.menubinhdinh:
                        edt_thanhpho.setText("Bình Định");
                        break;
                    case R.id.menuphuyen:
                        edt_thanhpho.setText("Phú Yên");
                        break;
                    case R.id.menubaclieu:
                        edt_thanhpho.setText("Bạc Liêu");
                        break;
                    case R.id.menuninhthuan:
                        edt_thanhpho.setText("Ninh Thuận");
                        break;
                    case R.id.menubinhthuan:
                        edt_thanhpho.setText("Bình Thuận");
                        break;
                    case R.id.menukontum:
                        edt_thanhpho.setText("Kon Tum");
                        break;
                    case R.id.menugialai:
                        edt_thanhpho.setText("Gia Lai");
                        break;
                    case R.id.menudaklak:
                        edt_thanhpho.setText("Đắk Lắk");
                        break;
                    case R.id.menudaknong:
                        edt_thanhpho.setText("Đắk Nông");
                        break;
                    case R.id.menusoctrang:
                        edt_thanhpho.setText("Sóc Trăng");
                        break;
                    case R.id.menukiengiang:
                        edt_thanhpho.setText("Kiên Giang");
                        break;
                    case R.id.menutayninh:
                        edt_thanhpho.setText("Tây Ninh");
                        break;
                    case R.id.menubinhduong:
                        edt_thanhpho.setText("Bình Dương");
                        break;
                    case R.id.menudongnai:
                        edt_thanhpho.setText("Đồng Nai");
                        break;
                    case R.id.menuhaugiang:
                        edt_thanhpho.setText("Hậu Giang");
                        break;
                    case R.id.menulongan:
                        edt_thanhpho.setText("Long An");
                        break;
                    case R.id.menutiengiang:
                        edt_thanhpho.setText("Tiền Giang");
                        break;
                    case R.id.menubentre:
                        edt_thanhpho.setText("Bến Tre");
                        break;
                    case R.id.menutravinh:
                        edt_thanhpho.setText("Trà Vinh");
                        break;
                    case R.id.menuvinhlong:
                        edt_thanhpho.setText("Vĩnh Long");
                        break;
                    case R.id.menudongthap:
                        edt_thanhpho.setText("Đồng Tháp");
                        break;
                    case R.id.menuangiang:
                        edt_thanhpho.setText("An Giang");
                        break;
                    case R.id.menuvinhphuc:
                        edt_thanhpho.setText("Vĩnh Phúc");
                        break;
                    case R.id.menuhagiang:
                        edt_thanhpho.setText("Hà Giang");
                        break;
                    case R.id.menucaobang:
                        edt_thanhpho.setText("Cao Bằng");
                        break;
                    case R.id.menubackan:
                        edt_thanhpho.setText("Bắc Kạn");
                        break;
                    case R.id.menutuyenquang:
                        edt_thanhpho.setText("Tuyên Quang");
                        break;
                    case R.id.menulaocai:
                        edt_thanhpho.setText("Lào Cai");
                        break;
                    case R.id.menudienbien:
                        edt_thanhpho.setText("Điện Biên");
                        break;
                    case R.id.menulaichau:
                        edt_thanhpho.setText("Lai Châu");
                        break;
                    case R.id.menusonla:
                        edt_thanhpho.setText("Sơn La");
                        break;
                    case R.id.menuyenbai:
                        edt_thanhpho.setText("Yên Bái");
                        break;
                    case R.id.menuhoabinh:
                        edt_thanhpho.setText("Hòa Bình");
                        String thanhpho = "Hòa Bình";
                        FirebaseDatabase.getInstance().getReference("your_reference").child("thanhpho").setValue(thanhpho);
                        break;
                    case R.id.menuthainguyen:
                        edt_thanhpho.setText("Thái Nguyên");
                        break;
                    case R.id.menulangson:
                        edt_thanhpho.setText("Lạng Sơn");
                        break;
                    case R.id.menuquangninh:
                        edt_thanhpho.setText("Quảng Ninh");
                        break;
                    case R.id.menubacgiang:
                        edt_thanhpho.setText("Bắc Giang");
                        break;
                    case R.id.menuphutho:
                        edt_thanhpho.setText("Phú Thọ");
                        break;
                    case R.id.menuquangnam:
                        edt_thanhpho.setText("Quảng Nam");
                        break;
                    case R.id.menubacninh:
                        edt_thanhpho.setText("Bắc Ninh");
                        break;
                    case R.id.menuhaiduong:
                        edt_thanhpho.setText("Hải Dương");
                        break;
                    case R.id.menuhaiphong:
                        edt_thanhpho.setText("Hải Phòng");
                        break;
                    case R.id.menuhungyen:
                        edt_thanhpho.setText("Hưng Yên");
                        break;
                    case R.id.menuthaibinh:
                        edt_thanhpho.setText("Thái Bình");
                        break;
                    case R.id.menuhanam:
                        edt_thanhpho.setText("Hà Nam");
                        break;
                    case R.id.menunamdinh:
                        edt_thanhpho.setText("Nam Định");
                        break;
                    case R.id.menuninhbinh:
                        edt_thanhpho.setText("Ninh Bình");
                        break;
                    case R.id.menuthanhhoa:
                        edt_thanhpho.setText("Thanh Hóa");
                        break;
                    case R.id.menunghean:
                        edt_thanhpho.setText("Nghệ An");
                        break;
                    case R.id.menuhatinh:
                        edt_thanhpho.setText("Hà Tĩnh");
                        break;
                    case R.id.menuquangbinh:
                        edt_thanhpho.setText("Quảng Bình");
                        break;
                    case R.id.menuquangtri:
                        edt_thanhpho.setText("Quảng Trị");
                        break;
                    case R.id.menuthuathienhue:
                        edt_thanhpho.setText("Thừa Thiên Huế ");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();

    }

    private void ShowMenuTruongdahoc() {
        PopupMenu popupMenu = new PopupMenu(this, edt_truongdahoc);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_truongdahoc, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menulehongphong:
                        edt_truongdahoc.setText("THPT Lê Hồng Phong");
                        break;
                    case R.id.menunguyenthuonghien:
                        edt_truongdahoc.setText("THPT Nguyễn Thượng Hiền");
                        break;
                    case R.id.menunguyenthiminhkhai:
                        edt_truongdahoc.setText("THPT Nguyễn Thị Minh Khai");
                        break;
                    case R.id.menunguyencongtru:
                        edt_truongdahoc.setText("THPT Nguyễn Công Trứ");
                        break;
                    case R.id.menuquangtrung:
                        edt_truongdahoc.setText("THPT Quang Trung");
                        break;
                    case R.id.menuphuocthanh:
                        edt_truongdahoc.setText("THCS Phước Thạnh");
                        break;
                    case R.id.menunguyendu:
                        edt_truongdahoc.setText("THCS Nguyễn Du");
                        break;
                    case R.id.menunguyenhuutho:
                        edt_truongdahoc.setText("THCS Nguyễn Hữu Thọ");
                        break;
                    case R.id.menulythuongkiet:
                        edt_truongdahoc.setText("THCS Lý Thường Kiệt");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();


    }

    private void ShowMenuNamtotnghiep() {
        PopupMenu popupMenu = new PopupMenu(this, edt_namtotnghiep);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_namtotnghiep, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu2016:
                        edt_namtotnghiep.setText("Năm 2016");
                        break;
                    case R.id.menu2017:
                        edt_namtotnghiep.setText("Năm 2017");
                        break;
                    case R.id.menu2018:
                        edt_namtotnghiep.setText("Năm 2018");
                        break;
                    case R.id.menu2019:
                        edt_namtotnghiep.setText("Năm 2019");
                        break;
                    case R.id.menu2020:
                        edt_namtotnghiep.setText("Năm 2020");
                        break;
                    case R.id.menu2021:
                        edt_namtotnghiep.setText("Năm 2021");
                        break;
                    case R.id.menu2022:
                        edt_namtotnghiep.setText("Năm 2022");
                        break;
                    case R.id.menu2023:
                        edt_namtotnghiep.setText("Năm 2023");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();


    }

    private void addControls() {
        edt_monhoc = findViewById(R.id.edt_monhoc);
        edt_nghenghiep = findViewById(R.id.edt_nghenghiep);
        edt_thanhpho = findViewById(R.id.edt_thanhpho);
        edt_hovaten = findViewById(R.id.edt_hovaten);
        edt_diachi = findViewById(R.id.edt_diachi);
        edt_namsinh = findViewById(R.id.edt_namsinh);
        edt_email = findViewById(R.id.edt_email);
        edt_gioithieubanthan = findViewById(R.id.edt_gioithieubanthan);
        edt_sodienthoai = findViewById(R.id.edt_sodienthoai);
        edt_truongdahoc = findViewById(R.id.edt_truongdahoc);
        edt_namtotnghiep = findViewById(R.id.edt_namtotnghiep);
        dangkylamgiasu = findViewById(R.id.btn_dangkylamgiasu_dangkylamgiasu);
         img_giasu = findViewById(R.id.image_GiaSu);
    }

}





