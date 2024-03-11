package com.example.doan_giasu.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doan_giasu.Adapter.LopHocAdapter;
import com.example.doan_giasu.Model.LopHoc;
import com.example.doan_giasu.R;

import java.util.ArrayList;
import java.util.List;

public class NewclassFragment extends Fragment  {
        private RecyclerView rcv;
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
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_newclass, container, false);

            List<LopHoc> lophoclist = new ArrayList<>();
            lophoclist.add(new LopHoc(321, "Lớp học công nghệ thông tin cơ bản 1", 3320000,
                    "Lập Trình1","Sáng","HCM"));
            lophoclist.add(new LopHoc(321, "Lớp học công nghệ thông tin cơ bản 2", 321321,
                    "Lập Trình2","Sáng","HCM"));
            lophoclist.add(new LopHoc(321, "Lớp học công nghệ thông tin cơ bản 3", 321321,
                    "Lập Trình3","Sáng","HCM"));
            lophoclist.add(new LopHoc(321, "Lớp học công nghệ thông tin cơ bản 4 ", 350000,
                    "Lập Trình4","Sáng","HCM"));
            lophoclist.add(new LopHoc(321, "Lớp học công nghệ thông tin cơ bản 5", 321321,
                    "Lập Trình5","Sáng","HCM"));

            rcv = view.findViewById(R.id.rcv);

            // Kết nối LopHocAdapter vào RecyclerView
            LopHocAdapter lopHocAdapter = new LopHocAdapter(lophoclist);
            rcv.setAdapter(lopHocAdapter);

            // Bài trí cột sử dụng GridLayoutManager
            rcv.setLayoutManager(new GridLayoutManager(getActivity(), 1));

            // Thiết lập sự kiện nhấp vào item
            lopHocAdapter.setOnItemClickListener(new LopHocAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(LopHoc lophoc) {
                    // Xử lý sự kiện khi nhấp vào item ở đây
                }
            });

            return view;
        }

}