package com.example.doan_giasu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.doan_giasu.Fragment.SetFragment_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },2000);
    }

    //Kiểm tra nếu đăng nhập thì  ở frament new calsas,chưa thì quay về trang đăng nhập
    private void nextActivity(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            //chưa login
            Intent intent=new Intent(this, Dangnhap_Activity.class);
            startActivity(intent);
        }
        else {
            Intent intent=new Intent(this, SetFragment_Activity.class);
            startActivity(intent);
        }

    }
}