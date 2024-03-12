package Activity_Menu;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.doan_giasu.Adapter.GiaSu;
import com.example.doan_giasu.R;
import com.google.firebase.auth.FirebaseAuth;               //FireBase
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ThongTinCaNhan_Activity extends AppCompatActivity {
    EditText edt_Name_fragment_Infomation, edtDiaChi, edtNamSinh, edtEmail, edtSdt;
    RadioGroup radioGroupGioiTinh;
    RadioButton radioButtonNam, radioButtonNu;
    Button btnLuuThayDoi;
    ImageView circleImageView_InforUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitythong_tincanhan);
        addControls();
        /*addEvents();*/

        SetInformationUser();

        Toolbar toolbar = findViewById(R.id.toolbar_thongtincanhan);          //Hàm toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông tin cá nhân");
        int white = getResources().getColor(android.R.color.white);
        toolbar.setTitleTextColor(white);   //Trong đoạn mã trên, toolbar.setTitleTextColor(white) sẽ đặt màu trắng cho tiêu đề của Toolbar.
    }



    /*  private void UppData(GiaSu giaSu){                     //TẠO THÔNG TIN CÁ NHÂN
          FirebaseDatabase database =FirebaseDatabase.getInstance();
          DatabaseReference databaseReference = database.getReference("Thông tin cá nhân gia sư");              //Truyền thông tin vào firebase
          String thongtingiasu=String.valueOf(giaSu.getSDT());
          databaseReference.child(thongtingiasu).setValue(giaSu, new DatabaseReference.CompletionListener() {
              @Override
              public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                  Toast.makeText(ThongTinCaNhan_Activity.this, "Đã cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
              }
          });

      }*/
    private void addControls() {
        // Khai báo các EditText
        edt_Name_fragment_Infomation = findViewById(R.id.edt_Name_fragment_Infomation);
        edtEmail = findViewById(R.id.edt_Email_fragment_Infomation);
        edtSdt = findViewById(R.id.edt_SDT_fragment_Infomation);
        circleImageView_InforUser = findViewById(R.id.circleImageView_InforUser);


       /* // Khai báo RadioGroup và RadioButton
        radioGroupGioiTinh = findViewById(R.id.RadioGroup_GioiTinh_fragment_Infomation);
        radioButtonNam = findViewById(R.id.RadioButton_Nam_fragment_Infomation);
        radioButtonNu = findViewById(R.id.RadioButton_Nu_fragment_Infomation);*/

        // Khai báo các Button
        btnLuuThayDoi = findViewById(R.id.btn_LuuThayDoi_fragment_Infomation);


    }
    private void SetInformationUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        edt_Name_fragment_Infomation.setText(user.getDisplayName());
        edtEmail.setText(user.getEmail());
        edtSdt.setText(user.getPhoneNumber());
        Glide.with(this).load(user.getPhotoUrl()).error(R.drawable.img_1).into(circleImageView_InforUser);
    }


    //Hàm thoát ra trên toolbal

    /*private void addEvents() {
        // Viết các sự kiện xử lý khi các Button được click
        btnLuuThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Khai báo Gia sư bên adapter
                String sdt = edtSdt.getText().toString().trim();
                String email= edtEmail.getText().toString().trim();
                String diachi = edtDiaChi.getText().toString().trim();
                String ten = edtTaiKhoan.getText().toString().trim();
                String namsinh = edtNamSinh.getText().toString().trim();
                //String gioitinh = edtSdt().getText().toString().trim();
                GiaSu giaSu = new GiaSu(ten,email,namsinh,diachi,sdt);
                UppData(giaSu);
            }
        });
        }*/
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}

