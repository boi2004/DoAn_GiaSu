package com.example.doan_giasu.Fragment;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.doan_giasu.Adapter.GiaSuAdapter;
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
    private GiaSuAdapter giaSuAdapter;
    private List<GiaSu> ListGiasu;
    Button button3;
    private Activity view;

    // Phần còn lại của Fragment không thay đổi

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ListGiasu = new ArrayList<>(); // Khởi tạo danh sách gia sư
            giaSuAdapter = new GiaSuAdapter(ListGiasu); // Khởi tạo Adapter
            addControl();
            LayDanhSachGiaSuTuData(); // Lấy dữ liệu từ Firebase
        }
//
    }

    private void addControl() {
        recyclerView = view.findViewById(R.id.list_GiaoVien); // Gán recyclerView từ view đã inflate
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()); // Sử dụng getContext() thay vì this
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration=  new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL); // Sử dụng getContext() thay vì this
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(giaSuAdapter); // Thiết lập Adapter cho RecyclerView
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher, container, false);
        recyclerView = view.findViewById(R.id.list_GiaoVien); // Gán recyclerView từ view đã inflate
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()); // Sử dụng getContext() thay vì this
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration=  new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL); // Sử dụng getContext() thay vì this
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(giaSuAdapter); // Thiết lập Adapter cho RecyclerView

        return view;
    }

    public void LayDanhSachGiaSuTuData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference("GiaSu");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<GiaSu> tempList = new ArrayList<>(); // Tạo một danh sách tạm thời để lưu trữ dữ liệu mới

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    GiaSu giaSu = dataSnapshot.getValue(GiaSu.class);
                    Log.d("FirebaseData", "Gia su: " + giaSu.getHoVaTen()); // In ra Logcat
                    tempList.add(giaSu);
                }

                // Cập nhật danh sách dữ liệu mới trên luồng UI chính
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListGiasu.clear(); // Xóa danh sách cũ
                        ListGiasu.addAll(tempList); // Thêm dữ liệu mới vào danh sách
                        giaSuAdapter.notifyDataSetChanged(); // Cập nhật adapter
                    }
                });

                Toast.makeText(getActivity(), "Thành công khi lấy dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi khi lấy dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
