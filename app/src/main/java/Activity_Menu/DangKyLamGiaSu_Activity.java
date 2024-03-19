package Activity_Menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import com.example.doan_giasu.Adapter.GiaSuAdapter;
import com.example.doan_giasu.Model.GiaSu;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;


public class DangKyLamGiaSu_Activity extends AppCompatActivity {
    EditText edt_monhoc, edt_nghenghiep, edt_thanhpho, edt_hovaten, edt_diachi, edt_namsinh, edt_email, edt_gioithieubanthan, edt_sodienthoai, edt_truongdahoc, edt_namtotnghiep,
            edt_t2, edt_t3, edt_t4, edt_t5, edt_t6, edt_t7, edt_cn;
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


    }

    private void addEvents() {
        edt_monhoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu();
            }
        });
        edt_nghenghiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu1();
            }
        });
        edt_thanhpho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu2();
            }
        });
        edt_t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuT2();
            }
        });
        edt_t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuT3();
            }
        });
        edt_t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuT4();
            }
        });
        edt_t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuT5();
            }
        });
        edt_t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuT6();
            }
        });
        edt_t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuT7();
            }
        });
        edt_cn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuCN();
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
                luuThongTinGiaSu();         //Hàm kểm tra nếu chưa có thông tin gia sư thì tạo mới,ngược lại có rồi thì cập nhật lại thông tin mà không tạo mới
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
            uploadImageToFirebaseStorage(userId);
        }
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
        edt_t2 = findViewById(R.id.edt_t2);
        edt_t3 = findViewById(R.id.edt_t3);
        edt_t4 = findViewById(R.id.edt_t4);
        edt_t5 = findViewById(R.id.edt_t5);
        edt_t6 = findViewById(R.id.edt_t6);
        edt_t7 = findViewById(R.id.edt_t7);
        edt_cn = findViewById(R.id.edt_cn);
        dangkylamgiasu = findViewById(R.id.btn_dangkylamgiasu_dangkylamgiasu);
        img_giasu = findViewById(R.id.image_GiaSu);
    }

    private void ShowMenu() {
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

    private void ShowMenu1() {
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

    private void ShowMenu2() {
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

    private void ShowMenuT2() {
        PopupMenu popupMenu = new PopupMenu(this, edt_t2);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_thu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu0:
                        edt_t2.setText("0 giờ");
                        break;
                    case R.id.menu1:
                        edt_t2.setText("1 giờ");
                        break;
                    case R.id.menu2:
                        edt_t2.setText("2 giờ");
                        break;
                    case R.id.menu3:
                        edt_t2.setText("3 giờ");
                        break;
                    case R.id.menu4:
                        edt_t2.setText("4 giờ");
                        break;
                    case R.id.menu5:
                        edt_t2.setText("5 giờ");
                        break;
                    case R.id.menu6:
                        edt_t2.setText("6 giờ");
                        break;
                    case R.id.menu7:
                        edt_t2.setText("7 giờ");
                        break;
                    case R.id.menu8:
                        edt_t2.setText("8 giờ");
                        break;
                    case R.id.menu9:
                        edt_t2.setText("9 giờ");
                        break;
                    case R.id.menu10:
                        edt_t2.setText("10 giờ");
                        break;
                    case R.id.menu11:
                        edt_t2.setText("11 giờ");
                        break;
                    case R.id.menu12:
                        edt_t2.setText("12 giờ");
                        break;
                    case R.id.menu13:
                        edt_t2.setText("13 giờ");
                        break;
                    case R.id.menu14:
                        edt_t2.setText("14 giờ");
                        break;
                    case R.id.menu15:
                        edt_t2.setText("15 giờ");
                        break;
                    case R.id.menu16:
                        edt_t2.setText("16 giờ");
                        break;
                    case R.id.menu17:
                        edt_t2.setText("17 giờ");
                        break;
                    case R.id.menu18:
                        edt_t2.setText("18 giờ");
                        break;
                    case R.id.menu19:
                        edt_t2.setText("19 giờ");
                        break;
                    case R.id.menu20:
                        edt_t2.setText("20 giờ");
                        break;
                    case R.id.menu21:
                        edt_t2.setText("21 giờ");
                        break;
                    case R.id.menu22:
                        edt_t2.setText("22 giờ");
                        break;
                    case R.id.menu23:
                        edt_t2.setText("23 giờ");
                        break;
                    case R.id.menu24:
                        edt_t2.setText("24 giờ");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void ShowMenuT3() {
        PopupMenu popupMenu = new PopupMenu(this, edt_t3);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_thu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu0:
                        edt_t3.setText("0 giờ");
                        break;
                    case R.id.menu1:
                        edt_t3.setText("1 giờ");
                        break;
                    case R.id.menu2:
                        edt_t3.setText("2 giờ");
                        break;
                    case R.id.menu3:
                        edt_t3.setText("3 giờ");
                        break;
                    case R.id.menu4:
                        edt_t3.setText("4 giờ");
                        break;
                    case R.id.menu5:
                        edt_t3.setText("5 giờ");
                        break;
                    case R.id.menu6:
                        edt_t3.setText("6 giờ");
                        break;
                    case R.id.menu7:
                        edt_t3.setText("7 giờ");
                        break;
                    case R.id.menu8:
                        edt_t3.setText("8 giờ");
                        break;
                    case R.id.menu9:
                        edt_t3.setText("9 giờ");
                        break;
                    case R.id.menu10:
                        edt_t3.setText("10 giờ");
                        break;
                    case R.id.menu11:
                        edt_t3.setText("11 giờ");
                        break;
                    case R.id.menu12:
                        edt_t3.setText("12 giờ");
                        break;
                    case R.id.menu13:
                        edt_t3.setText("13 giờ");
                        break;
                    case R.id.menu14:
                        edt_t3.setText("14 giờ");
                        break;
                    case R.id.menu15:
                        edt_t3.setText("15 giờ");
                        break;
                    case R.id.menu16:
                        edt_t3.setText("16 giờ");
                        break;
                    case R.id.menu17:
                        edt_t3.setText("17 giờ");
                        break;
                    case R.id.menu18:
                        edt_t3.setText("18 giờ");
                        break;
                    case R.id.menu19:
                        edt_t3.setText("19 giờ");
                        break;
                    case R.id.menu20:
                        edt_t3.setText("20 giờ");
                        break;
                    case R.id.menu21:
                        edt_t3.setText("21 giờ");
                        break;
                    case R.id.menu22:
                        edt_t3.setText("22 giờ");
                        break;
                    case R.id.menu23:
                        edt_t3.setText("23 giờ");
                        break;
                    case R.id.menu24:
                        edt_t3.setText("24 giờ");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void ShowMenuT4() {
        PopupMenu popupMenu = new PopupMenu(this, edt_t4);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_thu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu0:
                        edt_t4.setText("0 giờ");
                        break;
                    case R.id.menu1:
                        edt_t4.setText("1 giờ");
                        break;
                    case R.id.menu2:
                        edt_t4.setText("2 giờ");
                        break;
                    case R.id.menu3:
                        edt_t4.setText("3 giờ");
                        break;
                    case R.id.menu4:
                        edt_t4.setText("4 giờ");
                        break;
                    case R.id.menu5:
                        edt_t4.setText("5 giờ");
                        break;
                    case R.id.menu6:
                        edt_t4.setText("6 giờ");
                        break;
                    case R.id.menu7:
                        edt_t4.setText("7 giờ");
                        break;
                    case R.id.menu8:
                        edt_t4.setText("8 giờ");
                        break;
                    case R.id.menu9:
                        edt_t4.setText("9 giờ");
                        break;
                    case R.id.menu10:
                        edt_t4.setText("10 giờ");
                        break;
                    case R.id.menu11:
                        edt_t4.setText("11 giờ");
                        break;
                    case R.id.menu12:
                        edt_t4.setText("12 giờ");
                        break;
                    case R.id.menu13:
                        edt_t4.setText("13 giờ");
                        break;
                    case R.id.menu14:
                        edt_t4.setText("14 giờ");
                        break;
                    case R.id.menu15:
                        edt_t4.setText("15 giờ");
                        break;
                    case R.id.menu16:
                        edt_t4.setText("16 giờ");
                        break;
                    case R.id.menu17:
                        edt_t4.setText("17 giờ");
                        break;
                    case R.id.menu18:
                        edt_t4.setText("18 giờ");
                        break;
                    case R.id.menu19:
                        edt_t4.setText("19 giờ");
                        break;
                    case R.id.menu20:
                        edt_t4.setText("20 giờ");
                        break;
                    case R.id.menu21:
                        edt_t4.setText("21 giờ");
                        break;
                    case R.id.menu22:
                        edt_t4.setText("22 giờ");
                        break;
                    case R.id.menu23:
                        edt_t4.setText("23 giờ");
                        break;
                    case R.id.menu24:
                        edt_t4.setText("24 giờ");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void ShowMenuT5() {
        PopupMenu popupMenu = new PopupMenu(this, edt_t5);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_thu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu0:
                        edt_t5.setText("0 giờ");
                        break;
                    case R.id.menu1:
                        edt_t5.setText("1 giờ");
                        break;
                    case R.id.menu2:
                        edt_t5.setText("2 giờ");
                        break;
                    case R.id.menu3:
                        edt_t5.setText("3 giờ");
                        break;
                    case R.id.menu4:
                        edt_t5.setText("4 giờ");
                        break;
                    case R.id.menu5:
                        edt_t5.setText("5 giờ");
                        break;
                    case R.id.menu6:
                        edt_t5.setText("6 giờ");
                        break;
                    case R.id.menu7:
                        edt_t5.setText("7 giờ");
                        break;
                    case R.id.menu8:
                        edt_t5.setText("8 giờ");
                        break;
                    case R.id.menu9:
                        edt_t5.setText("9 giờ");
                        break;
                    case R.id.menu10:
                        edt_t5.setText("10 giờ");
                        break;
                    case R.id.menu11:
                        edt_t5.setText("11 giờ");
                        break;
                    case R.id.menu12:
                        edt_t5.setText("12 giờ");
                        break;
                    case R.id.menu13:
                        edt_t5.setText("13 giờ");
                        break;
                    case R.id.menu14:
                        edt_t5.setText("14 giờ");
                        break;
                    case R.id.menu15:
                        edt_t5.setText("15 giờ");
                        break;
                    case R.id.menu16:
                        edt_t5.setText("16 giờ");
                        break;
                    case R.id.menu17:
                        edt_t5.setText("17 giờ");
                        break;
                    case R.id.menu18:
                        edt_t5.setText("18 giờ");
                        break;
                    case R.id.menu19:
                        edt_t5.setText("19 giờ");
                        break;
                    case R.id.menu20:
                        edt_t5.setText("20 giờ");
                        break;
                    case R.id.menu21:
                        edt_t5.setText("21 giờ");
                        break;
                    case R.id.menu22:
                        edt_t5.setText("22 giờ");
                        break;
                    case R.id.menu23:
                        edt_t5.setText("23 giờ");
                        break;
                    case R.id.menu24:
                        edt_t5.setText("24 giờ");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void ShowMenuT6() {
        PopupMenu popupMenu = new PopupMenu(this, edt_t6);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_thu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu0:
                        edt_t6.setText("0 giờ");
                        break;
                    case R.id.menu1:
                        edt_t6.setText("1 giờ");
                        break;
                    case R.id.menu2:
                        edt_t6.setText("2 giờ");
                        break;
                    case R.id.menu3:
                        edt_t6.setText("3 giờ");
                        break;
                    case R.id.menu4:
                        edt_t6.setText("4 giờ");
                        break;
                    case R.id.menu5:
                        edt_t6.setText("5 giờ");
                        break;
                    case R.id.menu6:
                        edt_t6.setText("6 giờ");
                        break;
                    case R.id.menu7:
                        edt_t6.setText("7 giờ");
                        break;
                    case R.id.menu8:
                        edt_t6.setText("8 giờ");
                        break;
                    case R.id.menu9:
                        edt_t6.setText("9 giờ");
                        break;
                    case R.id.menu10:
                        edt_t6.setText("10 giờ");
                        break;
                    case R.id.menu11:
                        edt_t6.setText("11 giờ");
                        break;
                    case R.id.menu12:
                        edt_t6.setText("12 giờ");
                        break;
                    case R.id.menu13:
                        edt_t6.setText("13 giờ");
                        break;
                    case R.id.menu14:
                        edt_t6.setText("14 giờ");
                        break;
                    case R.id.menu15:
                        edt_t6.setText("15 giờ");
                        break;
                    case R.id.menu16:
                        edt_t6.setText("16 giờ");
                        break;
                    case R.id.menu17:
                        edt_t6.setText("17 giờ");
                        break;
                    case R.id.menu18:
                        edt_t6.setText("18 giờ");
                        break;
                    case R.id.menu19:
                        edt_t6.setText("19 giờ");
                        break;
                    case R.id.menu20:
                        edt_t6.setText("20 giờ");
                        break;
                    case R.id.menu21:
                        edt_t6.setText("21 giờ");
                        break;
                    case R.id.menu22:
                        edt_t6.setText("22 giờ");
                        break;
                    case R.id.menu23:
                        edt_t6.setText("23 giờ");
                        break;
                    case R.id.menu24:
                        edt_t6.setText("24 giờ");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void ShowMenuT7() {
        PopupMenu popupMenu = new PopupMenu(this, edt_t7);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_thu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu0:
                        edt_t7.setText("0 giờ");
                        break;
                    case R.id.menu1:
                        edt_t7.setText("1 giờ");
                        break;
                    case R.id.menu2:
                        edt_t7.setText("2 giờ");
                        break;
                    case R.id.menu3:
                        edt_t7.setText("3 giờ");
                        break;
                    case R.id.menu4:
                        edt_t7.setText("4 giờ");
                        break;
                    case R.id.menu5:
                        edt_t7.setText("5 giờ");
                        break;
                    case R.id.menu6:
                        edt_t7.setText("6 giờ");
                        break;
                    case R.id.menu7:
                        edt_t7.setText("7 giờ");
                        break;
                    case R.id.menu8:
                        edt_t7.setText("8 giờ");
                        break;
                    case R.id.menu9:
                        edt_t7.setText("9 giờ");
                        break;
                    case R.id.menu10:
                        edt_t7.setText("10 giờ");
                        break;
                    case R.id.menu11:
                        edt_t7.setText("11 giờ");
                        break;
                    case R.id.menu12:
                        edt_t7.setText("12 giờ");
                        break;
                    case R.id.menu13:
                        edt_t7.setText("13 giờ");
                        break;
                    case R.id.menu14:
                        edt_t7.setText("14 giờ");
                        break;
                    case R.id.menu15:
                        edt_t7.setText("15 giờ");
                        break;
                    case R.id.menu16:
                        edt_t7.setText("16 giờ");
                        break;
                    case R.id.menu17:
                        edt_t7.setText("17 giờ");
                        break;
                    case R.id.menu18:
                        edt_t7.setText("18 giờ");
                        break;
                    case R.id.menu19:
                        edt_t7.setText("19 giờ");
                        break;
                    case R.id.menu20:
                        edt_t7.setText("20 giờ");
                        break;
                    case R.id.menu21:
                        edt_t7.setText("21 giờ");
                        break;
                    case R.id.menu22:
                        edt_t7.setText("22 giờ");
                        break;
                    case R.id.menu23:
                        edt_t7.setText("23 giờ");
                        break;
                    case R.id.menu24:
                        edt_t7.setText("24 giờ");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void ShowMenuCN() {
        PopupMenu popupMenu = new PopupMenu(this, edt_cn);
        popupMenu.getMenuInflater().inflate(R.menu.menu_pupop_thu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu0:
                        edt_cn.setText("0 giờ");
                        break;
                    case R.id.menu1:
                        edt_cn.setText("1 giờ");
                        break;
                    case R.id.menu2:
                        edt_cn.setText("2 giờ");
                        break;
                    case R.id.menu3:
                        edt_cn.setText("3 giờ");
                        break;
                    case R.id.menu4:
                        edt_cn.setText("4 giờ");
                        break;
                    case R.id.menu5:
                        edt_cn.setText("5 giờ");
                        break;
                    case R.id.menu6:
                        edt_cn.setText("6 giờ");
                        break;
                    case R.id.menu7:
                        edt_cn.setText("7 giờ");
                        break;
                    case R.id.menu8:
                        edt_cn.setText("8 giờ");
                        break;
                    case R.id.menu9:
                        edt_cn.setText("9 giờ");
                        break;
                    case R.id.menu10:
                        edt_cn.setText("10 giờ");
                        break;
                    case R.id.menu11:
                        edt_cn.setText("11 giờ");
                        break;
                    case R.id.menu12:
                        edt_cn.setText("12 giờ");
                        break;
                    case R.id.menu13:
                        edt_cn.setText("13 giờ");
                        break;
                    case R.id.menu14:
                        edt_cn.setText("14 giờ");
                        break;
                    case R.id.menu15:
                        edt_cn.setText("15 giờ");
                        break;
                    case R.id.menu16:
                        edt_cn.setText("16 giờ");
                        break;
                    case R.id.menu17:
                        edt_cn.setText("17 giờ");
                        break;
                    case R.id.menu18:
                        edt_cn.setText("18 giờ");
                        break;
                    case R.id.menu19:
                        edt_cn.setText("19 giờ");
                        break;
                    case R.id.menu20:
                        edt_cn.setText("20 giờ");
                        break;
                    case R.id.menu21:
                        edt_cn.setText("21 giờ");
                        break;
                    case R.id.menu22:
                        edt_cn.setText("22 giờ");
                        break;
                    case R.id.menu23:
                        edt_cn.setText("23 giờ");
                        break;
                    case R.id.menu24:
                        edt_cn.setText("24 giờ");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void luuThongTinGiaSu() {
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
                        capNhatThongTinGiaSu(userId);
                    } else {
                        // Người dùng chưa có thông tin gia sư trong cơ sở dữ liệu, tạo mới thông tin
                        taoMoiThongTinGiaSu(userId);
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
    private void capNhatThongTinGiaSu(String userId) {
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
        GiaSu giaSu = new GiaSu(nghenghiep, monhoc, thanhpho, hovaten, diachi, Integer.parseInt(namsinh), email, gioithieubanthan, sodienthoai, truongdahoc, Integer.parseInt(namtotnghiep));

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
    private void taoMoiThongTinGiaSu(String userId) {
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
        GiaSu giaSu = new GiaSu(nghenghiep, monhoc, thanhpho, hovaten, diachi, Integer.parseInt(namsinh), email, gioithieubanthan, sodienthoai, truongdahoc, Integer.parseInt(namtotnghiep));

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
                    }
                });
    }
    private void uploadImageToFirebaseStorage(String userId) {
        if (imageUri != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            String imageName = "profile_image.jpg";
            StorageReference imageRef = storageRef.child("profile_images").child(userId).child(imageName);

            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            // Lưu đường dẫn URL của ảnh vào Firestore hoặc Realtime Database
                            luuThongTinGiaSu();
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(DangKyLamGiaSu_Activity.this, "Tải ảnh lên thất bại", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(DangKyLamGiaSu_Activity.this, "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
        }
    }
    // Tham chiếu đến node chứa dữ liệu trên Realtime Database
    private GiaSuAdapter giaSuAdapter;
    private List<GiaSu> ListGiasu;
    public void LuuDuLieu(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("GiaSu");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ListGiasu.clear(); // Xóa dữ liệu cũ trước khi cập nhật mới
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        GiaSu giaSu = dataSnapshot.getValue(GiaSu.class);
                        Log.d("FirebaseData", "GiaSu: " + giaSu.toString()); // In ra dữ liệu của mỗi gia sư
                        ListGiasu.add(giaSu);
                    }
                    giaSuAdapter.notifyDataSetChanged(); // Thông báo cho adapter rằng dữ liệu đã thay đổi
                } else {
                    Log.d("FirebaseData", "No data available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}





