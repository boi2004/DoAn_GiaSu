package Activity_Menu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.doan_giasu.Adapter.GiaSu;
import com.example.doan_giasu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;               //FireBase
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.BitSet;


public class ThongTinCaNhan_Activity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 1;
    EditText edt_Name_fragment_Infomation, edtDiaChi, edtNamSinh, edtEmail, edtSdt;
    RadioGroup radioGroupGioiTinh;
    RadioButton radioButtonNam, radioButtonNu;
    Button btnLuuThayDoi;
    ImageView circleImageView_InforUser;
    Uri mSelectedImageUri;
    private ActivityResultLauncher<Intent> mActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK);{
                Intent intent =result.getData();
                if (intent == null){
                    return;
                }
               Uri uri = intent.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    mSelectedImageUri = uri;
                    setBitmapImageView(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitythong_tincanhan);
        addControls();
        /*addEvents();*/

        SetInformationUser();
        ChangeAvatar();

        Toolbar toolbar = findViewById(R.id.toolbar_thongtincanhan);          //Hàm toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông tin cá nhân");
        int white = getResources().getColor(android.R.color.white);
        toolbar.setTitleTextColor(white);   //Trong đoạn mã trên, toolbar.setTitleTextColor(white) sẽ đặt màu trắng cho tiêu đề của Toolbar.


        btnLuuThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null){
                    return;
                }
                String strName = edt_Name_fragment_Infomation.getText().toString().trim();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(strName)
                        .setPhotoUri(mSelectedImageUri) //**
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ThongTinCaNhan_Activity.this,"Cập Nhật Thông Tin Cá Nhân Thành Công!",Toast.LENGTH_LONG).show();
                                    }
                                }
                        });
            }
        });
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

    private void ChangeAvatar(){
        circleImageView_InforUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                    openGallery();
                    return;
                }
                //nếu người dùng cho phép quyền đọc thư viện thì openGallery()
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    openGallery();
                }else {
                    String [] permisstions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permisstions,MY_REQUEST_CODE);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }
    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent,"Chọn ảnh"));
    }

    public void setBitmapImageView(Bitmap bitmapImageView){
        circleImageView_InforUser.setImageBitmap(bitmapImageView);

    }

    //Hàm thoát ra trên toolbal
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}

