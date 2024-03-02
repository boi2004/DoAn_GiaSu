package com.example.doan_giasu.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.doan_giasu.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class InformationFragment extends Fragment {
    EditText edt_TaiKhoan,edt_NamSinh,edt_DiaChi,edt_Email;
    RadioGroup RadioGroup_GioiTinh;
    RadioButton  RadioButton_Nam,RadioButton_Nu;
    Button btn_Luuthaydoi;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InformationFragment newInstance(String param1, String param2) {
        InformationFragment fragment = new InformationFragment();
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
        addControl();
        addEvent();
    }
    public void addControl()
    {
        edt_TaiKhoan = getView().findViewById(R.id.edt_TaiKhoan_fragment_Infomation);
        edt_Email = getView().findViewById(R.id.edt_Email_fragment_Infomation);
        edt_NamSinh = getView().findViewById(R.id.edt_NamSinh_fragment_Infomation);
        edt_DiaChi = getView().findViewById(R.id.edt_DiaChi_fragment_Infomation);
        RadioGroup_GioiTinh = getView().findViewById(R.id.RadioGroup_GioiTinh_fragment_Infomation);
        RadioButton_Nam = getView().findViewById(R.id.RadioButton_Nam_fragment_Infomation);
        RadioButton_Nu = getView().findViewById(R.id.RadioButton_Nu_fragment_Infomation);
        btn_Luuthaydoi = getView().findViewById(R.id.btn_LuuThayDoi_fragment_Infomation);
    }
    public void addEvent() {
        btn_Luuthaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taiKhoan = edt_TaiKhoan.getText().toString();
                String email = edt_Email.getText().toString();
                String namSinh = edt_NamSinh.getText().toString();
                String diaChi = edt_DiaChi.getText().toString();
                int selectedRadioButtonId = RadioGroup_GioiTinh.getCheckedRadioButtonId();
                if (taiKhoan.isEmpty() || email.isEmpty() || namSinh.isEmpty() || diaChi.isEmpty() || selectedRadioButtonId == -1) {
                    // Hiển thị thông báo lỗi nếu một trong những trường chưa được điền
                    // (Bạn có thể thay thế bằng cách sử dụng Toast hoặc AlertDialog)
                    // Ví dụ:
                    Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information, container, false);
    }
}