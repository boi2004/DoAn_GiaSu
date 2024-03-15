package com.example.doan_giasu.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.doan_giasu.Dangnhap_Activity;
import com.example.doan_giasu.Model.LopHoc;
import com.example.doan_giasu.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URI;

import Activity_Menu.DangKyLamGiaSu_Activity;
import Activity_Menu.Danhsachlopday_Activity;
import Activity_Menu.Danhsachlophoc_Activity;
import Activity_Menu.DieuKhoanDichVuActivity;
import Activity_Menu.DoiMatKhau_Activity;
import Activity_Menu.ThongTinCaNhan_Activity;

public class SetFragment_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_UserProfile = 0;
    private DrawerLayout mDrawerLayout;
    private ImageView imageViewAvatar;
    private TextView txvName, txvEmail;
    private NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        // Do 3 id trên không nằm trong trong layout toolbar mà nằm trong header_nav
        // Cần mNavigationView mới có thể ánh xạ id được
        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new HomeFragment());
        mNavigationView.getMenu().findItem(R.id.nav_Trangchu).setChecked(true);
        // Ánh xạ id
        addControl();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        showUserInformation();
    }

    public void addControl() {
        txvEmail = mNavigationView.getHeaderView(0).findViewById(R.id.txv_mail_header);
        imageViewAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.img_avatar_header);
        txvName = mNavigationView.getHeaderView(0).findViewById(R.id.txv_ten_header);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_Dangkylamgiasu) {               // ĐĂNG KÝ LÀM GIA SƯ
            Intent intent = new Intent(this, DangKyLamGiaSu_Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Doimatkhau) {               //Đổi Lại Mật Khẩu
            Intent intent = new Intent(this, DoiMatKhau_Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_dieukhoanvadichvu) {           //Điều khoản dịch vụ
            Intent intent = new Intent(this, DieuKhoanDichVuActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Dangxuat) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, Dangnhap_Activity.class);      //Đăng xuất
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_Danhsachlopday) {
            Intent intent = new Intent(this, Danhsachlopday_Activity.class);     //Danh sách lớp học
            startActivity(intent);
        } else if (id == R.id.nav_Danhsachlophoc) {
            Intent intent = new Intent(this, Danhsachlophoc_Activity.class);      //Danh sách lớp dạy
            startActivity(intent);
        } else if (id == R.id.nav_Thongtincanhan) {           //THÔNG TIN CÁ NHÂN
            Intent intent = new Intent(this, ThongTinCaNhan_Activity.class);
            startActivity(intent);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    private void showUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();   // Nếu bạn đăng nhập thì Firebase sẽ trả về thông qua user
        if (user == null) {
            return;
        }
        String name = user.getDisplayName();        // Lấy tên và email từ user
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if (name == null) {
            txvName.setVisibility(View.GONE);  // Nếu tên là null, ẩn TextView txvName
        } else {
            txvName.setVisibility(View.VISIBLE);  // Nếu tên không null, hiển thị TextView txvName và thiết lập giá trị
            txvName.setText(name);
        }

        txvEmail.setText(email);  // Thiết lập giá trị cho email
        Glide.with(this).load(photoUrl).error(R.drawable.img_1).into(imageViewAvatar);  // Load ảnh từ URL và hiển thị vào imageViewAvatar
    }
}
