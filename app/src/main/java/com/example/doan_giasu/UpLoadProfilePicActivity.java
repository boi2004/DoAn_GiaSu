package com.example.doan_giasu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.doan_giasu.Fragment.SetFragment_Activity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import Activity_Menu.DangKyLamGiaSu_Activity;
import Activity_Menu.ThongTinCaNhan_Activity;

public class UpLoadProfilePicActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView imageViewUploadPic;
    private FirebaseAuth authProfile;
    private StorageReference storageReference;
    private FirebaseUser firebaseUser;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uriImage;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_profile_pic);

        /*getSupportActionBar().setTitle("Upload Pic");*/

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("DisplayPics");

        Button buttonUploadPicChoose = findViewById(R.id.btn_upload_pic_head);
        Button upload_pic_button = findViewById(R.id.upload_pic_button);
        progressBar = findViewById(R.id.progress_bar);
        imageViewUploadPic = findViewById(R.id.imageView_Profile_pic);

        Uri uri = firebaseUser.getPhotoUrl();

        Picasso.with(UpLoadProfilePicActivity.this).load(uri).into(imageViewUploadPic);

        buttonUploadPicChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        upload_pic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                UploadPic();
            }
        });

    }

    private void UploadPic() {
        if(uriImage != null){
            StorageReference fileReference = storageReference.child(authProfile.getCurrentUser().getUid() + ". "
                    + getFileExtension(uriImage));

            //Upload anh len storage
            fileReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri dowloadUri = uri;
                            firebaseUser = authProfile.getCurrentUser();

                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(dowloadUri).build();
                            firebaseUser.updateProfile(profileUpdate);

                            Intent broadcastIntent = new Intent("profile_pic_updated");
                            sendBroadcast(broadcastIntent);
                        }
                    });
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(UpLoadProfilePicActivity.this, "Cập nhật ảnh đại diện thành công",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UpLoadProfilePicActivity.this, SetFragment_Activity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpLoadProfilePicActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }else {
           progressBar.setVisibility(View.GONE);
           Toast.makeText(UpLoadProfilePicActivity.this,"Không tồn tại ảnh",Toast.LENGTH_SHORT).show();

        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime =MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null){
            uriImage = data.getData();
            imageViewUploadPic.setImageURI(uriImage);
        }
    }
}