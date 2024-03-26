package com.example.doan_giasu.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doan_giasu.Adapter.GiaSu_Adapter;
import com.example.doan_giasu.Model.GiaSu;
import com.example.doan_giasu.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeacherFragment extends Fragment {

    private RecyclerView recyclerView;
    private GiaSu_Adapter giaSuAdapter;
    private List<GiaSu> ListGiasu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ListGiasu = new ArrayList<>(); // Khởi tạo danh sách gia sư
            giaSuAdapter = new GiaSu_Adapter(ListGiasu); // Khởi tạo Adapter
            LayDanhSachGiaSuTuData(); // Lấy dữ liệu từ Firebase
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher, container, false);
        recyclerView = view.findViewById(R.id.list_GiaoVien);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        // Kiểm tra xem adapter đã được khởi tạo trước đó hay chưa
        if (giaSuAdapter == null) {
            // Khởi tạo adapter và lấy dữ liệu từ Firebase
            ListGiasu = new ArrayList<>();
            giaSuAdapter = new GiaSu_Adapter(ListGiasu);
            LayDanhSachGiaSuTuData();
        } else {
            // Adapter đã được khởi tạo trước đó, không cần làm gì cả
        }

        // Gán adapter cho RecyclerView
        recyclerView.setAdapter(giaSuAdapter);

        return view;
    }
    public void LayDanhSachGiaSuTuData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("GiaSu");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ListGiasu.clear(); // Xóa dữ liệu cũ trước khi cập nhật mới
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        GiaSu giaSu = dataSnapshot.getValue(GiaSu.class);
                        Log.d("FirebaseData", "GiaSu: " + giaSu.toString()); // In ra dữ liệu của mỗi gia sư
                        ListGiasu.add(giaSu);
                    }
                    giaSuAdapter.notifyDataSetChanged(); // Thông báo cho adapter rằng dữ liệu đã thay đổi
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

