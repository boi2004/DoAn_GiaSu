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
import java.util.List;


public class GiaSuAdapter extends RecyclerView.Adapter<GiaSuAdapter.viewholder> {
ArrayList<GiaSu>items;
Context context;
private List<GiaSu> GiaSu1;
    //Contructors
    public GiaSuAdapter(List<GiaSu> giaSu1) {
       this.GiaSu1 = giaSu1;
    }
        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Sử dụng LayoutInflater để inflate (nạp) layout từ file XML thành một View object
            // R.layout.giasu_item là layout cho một item trong RecyclerView
            // parent: ViewGroup cha mà View sẽ được gắn vào, thông thường là RecyclerView
            // false: không gắn View vào parent trực tiếp, vì RecyclerView sẽ tự quản lý việc này
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.giasu_item, parent, false);

            // Tạo một ViewHolder mới, và truyền vào View đã được inflate
            return new viewholder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
            GiaSu giaSu = GiaSu1 .get(position);
            if(giaSu==null)
            {
                return;
            }
            holder.Name.setText(giaSu.getHoVaTen());
            //holder.picture.setText(giaSu.getHoVaTen());
            holder.vitri .setText(giaSu.getDiaChi());
            holder.nghenghiep.setText(giaSu.getNgheNghiep());
            holder.namsinh.setText(giaSu.getNamSinh());
            holder.monhoc.setText(giaSu.getMonHoc());
        }

        @Override
        public int getItemCount() {
            // Kiểm tra nếu danh sách items không null
            if (GiaSu1 != null) {
                // Trả về số lượng phần tử trong danh sách
                return GiaSu1.size();
            }
            else {
                // Nếu danh sách là null, trả về 0
                return 0;
            }
        }
        public class viewholder extends RecyclerView.ViewHolder {
            TextView Name, vitri, nghenghiep, namsinh, monhoc;
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


