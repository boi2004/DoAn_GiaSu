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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;


public class GiaSuAdapter extends RecyclerView.Adapter<GiaSuAdapter.viewholder> {
ArrayList<GiaSu>items;
Context context;

    public GiaSuAdapter(ArrayList<GiaSu> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public GiaSuAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Lấy ra Context từ parent ViewGroup
        context = parent.getContext();

        // Sử dụng LayoutInflater để inflate (nạp) layout từ file XML thành một View object
        // R.layout.fragment_teacher là layout cho một item trong RecyclerView
        // parent: ViewGroup cha mà View sẽ được gắn vào, thông thường là RecyclerView
        // false: không gắn View vào parent trực tiếp, vì RecyclerView sẽ tự quản lý việc này
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_teacher, parent, false);

        // Tạo một ViewHolder mới, và truyền vào View đã được inflate
        return new GiaSuAdapter.viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull GiaSuAdapter.viewholder holder, int position) {
        // Lấy ra đối tượng GiaSu ở vị trí position trong danh sách items
        GiaSu giaSu = items.get(position);

        // Gắn dữ liệu từ đối tượng GiaSu vào các thành phần giao diện của ViewHolder
        holder.Name.setText(giaSu.getHoVaTen());          // Gắn tên gia sư
        holder.namsinh.setText(giaSu.getNamSinh());    // Gắn năm sinh
        holder.vitri.setText(giaSu.getDiaChi());        // Gắn vị trí
        holder.monhoc.setText(giaSu.getMonHoc());      // Gắn môn học
        holder.nghenghiep.setText(giaSu.getNgheNghiep());  // Gắn nghề nghiệp

        // Sử dụng thư viện Glide để tải và hiển thị hình ảnh của gia sư
        Glide.with(context)
                .load(giaSu.getAvatarUrl())   // Đường dẫn đến hình ảnh của gia sư
                .transform(new CenterCrop(), new RoundedCorners(30))  // Hiệu ứng khi load ảnh và bo góc
                .into(holder.picture);   // Hiển thị ảnh trong ImageView của ViewHolder
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewholder  extends RecyclerView.ViewHolder{
        TextView  Name,vitri,nghenghiep,namsinh,monhoc;
        ImageView picture;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các thành phần giao diện từ layout item gia sư
            Name = itemView.findViewById(R.id.Hoten_giasu_item);         // TextView hiển thị tên của gia sư
            picture = itemView.findViewById(R.id.Avatar_giasu_item);      // ImageView hiển thị hình ảnh đại diện của gia sư
            vitri = itemView.findViewById(R.id.Diachi_giasu_item);        // TextView hiển thị địa chỉ làm việc của gia sư
            nghenghiep = itemView.findViewById(R.id.Nghenghiep_giasu_item);   // TextView hiển thị nghề nghiệp của gia sư
            namsinh = itemView.findViewById(R.id.Namsinh_giasu_item);    // TextView hiển thị năm sinh của gia sư
            monhoc = itemView.findViewById(R.id.Monday_giaovien_item);   // TextView hiển thị môn học mà gia sư dạy

        }
    }
}
