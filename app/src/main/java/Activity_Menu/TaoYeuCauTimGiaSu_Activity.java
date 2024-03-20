package Activity_Menu;

import static com.example.doan_giasu.R.id.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.example.doan_giasu.R;

public class TaoYeuCauTimGiaSu_Activity extends AppCompatActivity {
    EditText edt_email, edt_tieude, edt_diadiemday, edt_ngaybatdau, edt_giomoibuoi, edt_monhoc, edt_gioitinhhocvien,
    edt_hocphi, edt_sobuoitrongtuan, edt_gioitinh, edt_hocphitheo, edt_motachitiet;
    Button btn_taoyeucau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_yeu_cau_tim_gia_su);

        Toolbar toolbar = findViewById(toolbar_taoyeucautimgiasu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đăng ký làm gia sư");
        int white = getResources().getColor(android.R.color.white);
        toolbar.setTitleTextColor(white);   //Trong đoạn mã trên, toolbar.setTitleTextColor(white) sẽ đặt màu trắng cho tiêu đề của Toolbar.

        Controls();
        Events();
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
                        edt_gioitinhhocvien.setText("Tháng");
                        break;
                    case menubuoi:
                        edt_gioitinhhocvien.setText("Buổi");
                        break;
                    case menutuan:
                        edt_gioitinhhocvien.setText("Tuần");
                        break;
                    case menugio:
                        edt_gioitinhhocvien.setText("Giờ");
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
}