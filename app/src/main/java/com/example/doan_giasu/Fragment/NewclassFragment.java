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
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NewclassFragment extends Fragment  {
    RecyclerView rcv;
    LopHocAdapter lopHocAdapter;
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
            rcv = view.findViewById(R.id.rcv);


            FirebaseRecyclerOptions<LopHoc> options =
                    new FirebaseRecyclerOptions.Builder<LopHoc>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("LopHoc"), LopHoc.class)
                            .build();

            lopHocAdapter = new LopHocAdapter(options);
            rcv.setAdapter(lopHocAdapter);

            return view;
        }

}