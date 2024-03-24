package com.example.doan_giasu.Adapter;

import android.content.Context;
import android.content.Intent;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Edit_Detail.Detail_DanhSachLopHoc_Activity;
import Edit_Detail.Detail_NewClass_Activity;

public class LopMoiAdapter extends RecyclerView.Adapter<LopMoiAdapter.viewholder> {
    private List<LopHoc> listLopHoc;
    private Context context; // Context của ứng dụng

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
        Button btn_dangkynhanlop,btn_chitiet;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            Title_item = itemView.findViewById(R.id.Title_item);
            MaLop_item = itemView.findViewById(R.id.MaLop_item);//Chưa có
            Time_item = itemView.findViewById(R.id.Time_item);
            Monhoc_item = itemView.findViewById(R.id.Monhoc_item);
            Tien_item = itemView.findViewById(R.id.Tien_item);
            btn_dangkynhanlop=itemView.findViewById(R.id.btn_dangynhanlop);
            btn_chitiet=itemView.findViewById(R.id.btn_chitiet);
            btn_dangkynhanlop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // 2. Lấy ID của lớp học từ danh sách dựa vào vị trí của item
                        String lopHocId = listLopHoc.get(position).getID();
                        //Lấy id người dùng
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        String userId = currentUser.getUid();
                        // Thêm một đăng ký mới vào cơ sở dữ liệu Firebase
                        // Thêm lớp học mới vào danh sách đăng ký nhận lớp của người dùng
                        DatabaseReference dangKyRef = FirebaseDatabase.getInstance().getReference("Danh sách đăng ký nhận lớp").child(userId);
                        dangKyRef.child(lopHocId).setValue(true);

                        // Hiển thị thông báo hoặc thực hiện hành động khác nếu cần
                           Toast.makeText(context, "Đã đăng ký nhận lớp học", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btn_chitiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        LopHoc lopHoc = listLopHoc.get(position);
                        String lopHocId = lopHoc.getID();

                        //Lấy id người dùng
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if (currentUser != null) {
                            String userId = currentUser.getUid();

                            // Tạo Intent và truyền ID của lớp học và ID của người dùng
                            Intent intent = new Intent(context, Detail_NewClass_Activity.class);
                            intent.putExtra("LopMoi", lopHocId);
                            intent.putExtra("iduser", userId); //Phần này không cần cũng đc

                            // Khởi chạy Intent để chuyển sang Detail_NewClass_Activity
                            context.startActivity(intent);
                        } else {
                            // Nếu không có người dùng hiện tại, có thể cần xử lý theo cách thích hợp
                            Toast.makeText(context, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

}
