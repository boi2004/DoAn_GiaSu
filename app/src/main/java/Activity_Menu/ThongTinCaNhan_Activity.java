package Activity_Menu;

import android.Manifest;
import android.content.BroadcastReceiver;
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
import android.widget.TextView;
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
import com.example.doan_giasu.R;
import com.example.doan_giasu.UpLoadProfilePicActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.IOException;
import java.util.BitSet;

public class ThongTinCaNhan_Activity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 1;
    TextView Change_Image_Profile;
    EditText edt_Name_fragment_Infomation, edtEmail, edtSdt;
    Button btnLuuThayDoi;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    ImageView circleImageView_InforUser;
    Uri mSelectedImageUri;
    String mUri;
    StorageTask uploadTask;
    StorageReference mstoragePicRef;
    private BroadcastReceiver profilePicUpdatedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitythong_tincanhan);

        addControls();
        SetInformationUser();

        Toolbar toolbar = findViewById(R.id.toolbar_thongtincanhan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông tin cá nhân");
        int white = getResources().getColor(android.R.color.white);
        toolbar.setTitleTextColor(white);

        LuuDuLieu();

        btnLuuThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUserInfo();
            }
        });

        Change_Image_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongTinCaNhan_Activity.this, UpLoadProfilePicActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("GiaSu");
        mstoragePicRef = FirebaseStorage.getInstance().getReference().child("Profile Pic");
        circleImageView_InforUser = findViewById(R.id.circleImageView_InforUser);
        Change_Image_Profile = findViewById(R.id.Change_Image_Profile);

        edt_Name_fragment_Infomation = findViewById(R.id.edt_Name_fragment_Infomation);
        edtEmail = findViewById(R.id.edt_Email_fragment_Infomation);
        edtSdt = findViewById(R.id.edt_SDT_fragment_Infomation);

        btnLuuThayDoi = findViewById(R.id.btn_LuuThayDoi_fragment_Infomation);
    }

    private void SetInformationUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        edt_Name_fragment_Infomation.setText(user.getDisplayName());
        edtEmail.setText(user.getEmail());
        edtSdt.setText(user.getPhoneNumber());
        Glide.with(this).load(photoUrl).error(R.drawable.img_1).into(circleImageView_InforUser);
    }

    public void LuuDuLieu() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        DatabaseReference giaSuRef = FirebaseDatabase.getInstance().getReference().child("GiaSu").child(userID);
        giaSuRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String soDienThoai = dataSnapshot.child("soDienThoai").getValue(String.class);
                    edtEmail.setText(email);
                    edtSdt.setText(soDienThoai);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });
    }

    private void UpdateUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String strName = edt_Name_fragment_Infomation.getText().toString().trim();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strName)
                .setPhotoUri(Uri.parse(String.valueOf(mSelectedImageUri))) //**
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            SetInformationUser();
                            LuuDuLieu();
                            Toast.makeText(ThongTinCaNhan_Activity.this, "Cập Nhật Thông Tin Cá Nhân Thành Công!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
