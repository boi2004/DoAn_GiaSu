package com.example.doan_giasu.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.doan_giasu.Dangnhap_Activity;
import com.example.doan_giasu.R;
import com.google.android.material.navigation.NavigationView;

import Activity_Menu.DangKyLamGiaSu_Activity;
import Activity_Menu.Danhsachlopday_Activity;
import Activity_Menu.Danhsachlophoc_Activity;
import Activity_Menu.DieuKhoanDichVuActivity;
import Activity_Menu.DoiMatKhau_Activity;
import Activity_Menu.ThongTinCaNhan_Activity;

public class SetFragment_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int FRAGMENT_HOME = 0;
    //private static final int FRAGMENT_INFOMATION = 1;
    private static final int FRAGMENT_DANHSACHLOPDAY = 2;
    private static final int FRAGMENT_DANHSACHLOPHOC = 3;
    //private static final int FRAGMENT_CAPNHATMATKHAU = 4;     //Frament đặt lại mật khẩu
    private static final int FRAGMENT_DANGKYLAMGIASU = 5;
    private static final int FRAGMENT_DIEUKHOANVADICHVU = 6;

    private  int mCurrentFragment = FRAGMENT_HOME;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_Trangchu).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_Trangchu){
            if (mCurrentFragment != FRAGMENT_HOME){             //FRAMENT TRANG CHỦ
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }

        }else if (id == R.id.nav_Dangkylamgiasu){           //
            Intent intent = new Intent(this, DangKyLamGiaSu_Activity.class);
            startActivity(intent);

        }else if (id == R.id.nav_Danhsachlopday){           //
            Intent intent = new Intent(this, Danhsachlopday_Activity.class);
            startActivity(intent);

        }else if (id == R.id.nav_Doimatkhau){               //Đổi Lại Mật Khẩu
            Intent intent = new Intent(this, DoiMatKhau_Activity.class);
            startActivity(intent);

        }else if (id == R.id.nav_Thongtincanhan) {           //THÔNG TIN CÁ NHÂN
            Intent intent = new Intent(this, ThongTinCaNhan_Activity.class);
            startActivity(intent);

        }else if (id == R.id.nav_dieukhoanvadichvu){           //
            Intent intent = new Intent(this, DieuKhoanDichVuActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_Danhsachlophoc){           //
            Intent intent = new Intent(this, Danhsachlophoc_Activity.class);
            startActivity(intent);
        }else if (id == R.id.nav_Dangxuat){           //FRAMENT Đăng xuất
            performLogout();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void performLogout() {
        Intent intent = new Intent(this, Dangnhap_Activity.class);      //Hàm đăng xuất để gọi trong dangxuat
        startActivity(intent);
        finish();
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}