package com.example.doan_giasu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_giasu.Model.LopHoc;
import com.example.doan_giasu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LopMoiAdapter extends RecyclerView.Adapter<LopMoiAdapter.viewholder> {
    private List<LopHoc> listLopHoc;
    private Context context ; // Context của ứng dụng

    public LopMoiAdapter(Context context,List<LopHoc> listLopHoc) {
        this.context = context;
        this.listLopHoc = listLopHoc;
    }

    public LopMoiAdapter(List<LopHoc> listLopHoc) {
        this.listLopHoc = listLopHoc;
    }

    @NonNull
    @Override
    public LopMoiAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lop_hoc_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LopMoiAdapter.viewholder holder, int position) {
        LopHoc lopHoc = listLopHoc.get(position);
        holder.Title_item.setText(lopHoc.getTitle());
        holder.Time_item.setText(lopHoc.getGioMoiBuoi());
        holder.Monhoc_item.setText(lopHoc.getMonHoc());
        holder.Tien_item.setText(lopHoc.getHocPhi());
        holder.MaLop_item.setText(lopHoc.getID());      //Lấy id
    }

    @Override
    public int getItemCount() {
        return listLopHoc != null ? listLopHoc.size():0;
    }

    public class viewholder extends RecyclerView.ViewHolder{
        //ánh xạ id trong layout lop moi
        TextView Title_item, MaLop_item, Time_item, Monhoc_item, Tien_item;
        Button btn_dangkynhanlop;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            Title_item = itemView.findViewById(R.id.Title_item);
            MaLop_item = itemView.findViewById(R.id.MaLop_item);//Chưa có
            Time_item = itemView.findViewById(R.id.Time_item);
            Monhoc_item = itemView.findViewById(R.id.Monhoc_item);
            Tien_item = itemView.findViewById(R.id.Tien_item);
            btn_dangkynhanlop = itemView.findViewById(R.id.btn_dangynhanlop);
            btn_dangkynhanlop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Lấy ID của lớp học từ danh sách dựa vào vị trí của item
                        //String lopHocId = listLopHoc.get(position).getID();

                        // Lấy đối tượng lớp học từ danh sách
                        LopHoc lophoc = listLopHoc.get(position);


                        // Lấy id người dùng
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        String userId = currentUser.getUid();


                        // Thêm lớp học vào danh sách đăng ký nhận lớp của người dùng
                        DatabaseReference dangKyRef = FirebaseDatabase.getInstance().getReference("Danh sách đăng ký nhận lớp").child(userId);
                        String lopHocId = dangKyRef.push().getKey(); // Tạo một key mới
                        dangKyRef.child(lopHocId).setValue(lophoc);

                        Toast.makeText(itemView.getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}
