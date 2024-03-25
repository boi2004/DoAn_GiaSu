package com.example.doan_giasu.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doan_giasu.Adapter.LopMoiAdapter;
import com.example.doan_giasu.Model.LopHoc;
import com.example.doan_giasu.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewclassFragment extends Fragment  {
    RecyclerView rcv;
    private LopMoiAdapter lopMoiAdapter;
    private List<LopHoc> ListLopHoc;
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;

        public NewclassFragment() {
            // Required empty public constructor
        }

        // TODO: Rename and change types and number of parameters
        public static NewclassFragment newInstance(String param1, String param2) {
            NewclassFragment fragment = new NewclassFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                ListLopHoc = new ArrayList<>(); // Khởi tạo danh sách gia sư
                lopMoiAdapter = new LopMoiAdapter(ListLopHoc); // Khởi tạo Adapter
                LayDanhSachLopHocTuData(); // Lấy dữ liệu từ Firebase
            }

        }

    /*private void LayDanhSachLopMoiTuData() {
       FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference databaseReference = database.getReference("LopMoi");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ListLopHoc.clear(); // Xóa dữ liệu cũ trước khi cập nhật mới
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        LopHoc lopHoc = dataSnapshot.getValue(LopHoc.class);
                        Log.d("FirebaseData", "LopMoi: " + lopHoc.toString()); // In ra dữ liệu của mỗi gia sư
                       ListLopHoc.add(lopHoc);
                    }
                    lopMoiAdapter.notifyDataSetChanged(); // Thông báo cho adapter rằng dữ liệu đã thay đổi
                } else {
                    Log.d("FirebaseData", "No data available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi lấy dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_newclass, container, false);
            rcv = view.findViewById(R.id.rcv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcv.addItemDecoration(dividerItemDecoration);

        // Kiểm tra xem adapter đã được khởi tạo trước đó hay chưa
        if(lopMoiAdapter == null){
            // Khởi tạo adapter và lấy dữ liệu từ Firebase
            ListLopHoc = new ArrayList<>();
            lopMoiAdapter = new LopMoiAdapter(ListLopHoc);
            LayDanhSachLopHocTuData();
        } else {
            // Adapter đã được khởi tạo trước đó, không cần làm gì cả
        }
        // Gán adapter cho RecyclerView
        rcv.setAdapter(lopMoiAdapter);
        return view;
        }

    private void LayDanhSachLopHocTuData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LopMoi");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ListLopHoc.clear(); // Xóa dữ liệu cũ trước khi cập nhật mới
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        // Lấy ID của người dùng
                        String userId = userSnapshot.getKey();
                        // Duyệt qua từng lớp học của người dùng
                        for (DataSnapshot lopHocSnapshot : userSnapshot.getChildren()) {
                            // Lấy thông tin của lớp học
                            LopHoc lopHoc = lopHocSnapshot.getValue(LopHoc.class);
                            lopHoc.setID(userId);
                            Log.d("FirebaseData", "LopMoi: " + lopHoc.toString()); // In ra dữ liệu của mỗi lớp học
                            ListLopHoc.add(lopHoc);
                        }
                    }
                    lopMoiAdapter.notifyDataSetChanged(); // Thông báo cho adapter rằng dữ liệu đã thay đổi
                } else {
                    Log.d("FirebaseData", "No data available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi lấy dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

}