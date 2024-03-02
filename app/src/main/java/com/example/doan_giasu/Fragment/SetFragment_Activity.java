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

public class SetFragment_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_INFOMATION = 1;
    private static final int FRAGMENT_DANHSACHLOPDAY = 2;
    private static final int FRAGMENT_DANHSACHLOPHOC = 3;
    private static final int FRAGMENT_CAPNHATMATKHAU = 4;
    private static final int FRAGMENT_DANGKYLAMGIASU = 5;
    private static final int FRAGMENT_DIEUKHOANVADICHVU = 6;

    private  int mCurrentFragment = FRAGMENT_HOME;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

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

        }else if (id == R.id.nav_Dangkylamgiasu){               //FRAMENT ĐĂNG KÝ LÀM GIA SƯ
            if (mCurrentFragment != FRAGMENT_DANGKYLAMGIASU){
                replaceFragment(new DangKyLamGiaSu_Fragment());
                mCurrentFragment = FRAGMENT_DANGKYLAMGIASU;
            }

        }else if (id == R.id.nav_Danhsachlopday){              //FRAMENT DANH SÁCH LỚP DẠY
            if (mCurrentFragment != FRAGMENT_DANHSACHLOPDAY){
                replaceFragment(new DanhSachLopDay_Fragment());
                mCurrentFragment = FRAGMENT_DANHSACHLOPDAY;
            }

        }else if (id == R.id.nav_Doimatkhau){               //FRAMENT DANH SÁCH LỚP HỌC
            if (mCurrentFragment != FRAGMENT_CAPNHATMATKHAU){
                replaceFragment(new CapNhatMatKhau_Fragment());
                mCurrentFragment = FRAGMENT_CAPNHATMATKHAU;
            }

        }else if (id == R.id.nav_Thongtincanhan){           //FRAMNENT THÔNG TIN CÁ NHÂN
            if (mCurrentFragment != FRAGMENT_INFOMATION){
                replaceFragment(new InformationFragment());
                mCurrentFragment = FRAGMENT_INFOMATION;
            }

        }else if (id == R.id.nav_dieukhoanvadichvu){        //FRAMENT ĐIỀU KHOẢN VÀ DỊCH VỤ
            if (mCurrentFragment != FRAGMENT_DIEUKHOANVADICHVU){
                replaceFragment(new Dieukhoanvadichvu_Fragment());
                mCurrentFragment = FRAGMENT_DIEUKHOANVADICHVU;
            }
        }else if (id == R.id.nav_Danhsachlophoc){           //FRAMENT DANH SÁCH LỚP HỌC
            if (mCurrentFragment != FRAGMENT_DANHSACHLOPHOC){
                replaceFragment(new DanhSachLopHoc_Fragment());
                mCurrentFragment = FRAGMENT_DANHSACHLOPHOC;
            }
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