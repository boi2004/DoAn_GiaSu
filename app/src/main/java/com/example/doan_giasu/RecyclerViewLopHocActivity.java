package com.example.doan_giasu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListAdapter;

import com.example.doan_giasu.Adapter.LopHocAdapter;
import com.example.doan_giasu.Model.LopHoc;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewLopHocActivity extends AppCompatActivity {
    private RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_lop_hoc);
        List<LopHoc> lophoclist = new ArrayList<LopHoc>();
        lophoclist.add(new LopHoc(321, "Lớp học công nghệ thông tin cơ bản", 321321,
                "Lập Trình1","Sáng","HCM"));
        lophoclist.add(new LopHoc(321, "Lớp học công nghệ thông tin cơ bản", 321321,
                "Lập Trình2","Sáng","HCM"));
        lophoclist.add(new LopHoc(321, "Lớp học công nghệ thông tin cơ bản", 321321,
                "Lập Trình3","Sáng","HCM"));
        lophoclist.add(new LopHoc(321, "Lớp học công nghệ thông tin cơ bản", 321321,
                "Lập Trình4","Sáng","HCM"));
        lophoclist.add(new LopHoc(321, "Lớp học công nghệ thông tin cơ bản", 321321,
                "Lập Trình5","Sáng","HCM"));


        rcv = findViewById(R.id.rcv);

        //kết nối LopHocAdapter vao
        LopHocAdapter lopHocAdapter = new LopHocAdapter(lophoclist);
        rcv.setAdapter(lopHocAdapter);

        //Bài trí cột
        rcv.setLayoutManager(new GridLayoutManager(this,1));
    }
}