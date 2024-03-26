package com.example.doan_giasu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.doan_giasu.Model.GiaSu;
import com.example.doan_giasu.R;

import java.util.ArrayList;
import java.util.List;


public class GiaSu_Adapter extends RecyclerView.Adapter<GiaSu_Adapter.viewholder> {
    ArrayList<GiaSu> items;
    Context context;
    private List<GiaSu> GiaSu1;

    //Contructors
    public GiaSu_Adapter(List<GiaSu> giaSu1) {
        this.GiaSu1 = giaSu1;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.giasu_item, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        GiaSu giaSu = GiaSu1.get(position);
        holder.Name.setText(giaSu.getHoVaTen());
        holder.vitri.setText(giaSu.getDiaChi());
        holder.nghenghiep.setText(giaSu.getNgheNghiep());
        holder.namsinh.setText(String.valueOf(giaSu.getNamSinh()));
        holder.monhoc.setText(giaSu.getMonHoc());
        holder.sodienthoai.setText(giaSu.getSoDienThoai());

        Glide.with(holder.itemView.getContext())
                .load(giaSu.getAvatarUrl())
                .placeholder(R.drawable.img_2) // Hình ảnh mặc định nếu URL không hợp lệ hoặc hình ảnh không thể tải được
                .error(R.drawable.baseline_error_24) // Hình ảnh mặc định nếu có lỗi xảy ra trong quá trình tải hình ảnh
                .transform(new CenterCrop(), new RoundedCorners(10)) // Hiển thị hình ảnh với góc bo tròn và căn giữa
                .into(holder.picture);
    }

    @Override
    public int getItemCount() {

        return GiaSu1 != null ? GiaSu1.size() : 0;
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView Name, vitri, nghenghiep, namsinh, monhoc, sodienthoai;
        ImageView picture;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các thành phần giao diện từ layout item gia sư
            Name = itemView.findViewById(R.id.Hoten_giasu_item);
            picture = itemView.findViewById(R.id.Avatar_giasu_item);
            vitri = itemView.findViewById(R.id.Diachi_giasu_item);
            nghenghiep = itemView.findViewById(R.id.Nghenghiep_giasu_item);
            namsinh = itemView.findViewById(R.id.Namsinh_giasu_item);
            monhoc = itemView.findViewById(R.id.Monday_giaovien_item);
            sodienthoai = itemView.findViewById(R.id.Sdt_giasu_item);
        }
    }
}


