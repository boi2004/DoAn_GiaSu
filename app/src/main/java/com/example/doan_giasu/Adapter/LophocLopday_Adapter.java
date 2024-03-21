package com.example.doan_giasu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_giasu.Model.LopHoc;
import com.example.doan_giasu.R;

import java.util.List;

public class LophocLopday_Adapter extends RecyclerView.Adapter<LophocLopday_Adapter.ViewHolder> {
    private List<LopHoc> listLopHoc; // Danh sách các lớp học
    private Context context; // Context của ứng dụng

    // Constructor để khởi tạo Adapter với danh sách các lớp học
    public LophocLopday_Adapter(List<LopHoc> listLopHoc) {
        this.listLopHoc = listLopHoc;
    }

    // Phương thức này tạo ra ViewHolder từ layout XML và trả về nó
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lophoclopday_item, parent, false);
        return new ViewHolder(view);
    }

    // Phương thức này gắn dữ liệu vào ViewHolder tại vị trí được chỉ định
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LopHoc lopHoc = listLopHoc.get(position); // Lấy lớp học tại vị trí position trong danh sách
        holder.bind(lopHoc); // Gọi phương thức bind để gắn dữ liệu vào ViewHolder
    }

    // Phương thức này trả về số lượng phần tử trong danh sách lớp học
    @Override
    public int getItemCount() {
        return listLopHoc != null ? listLopHoc.size() : 0;
    }

    // Lớp ViewHolder đại diện cho mỗi item trong RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title_item, MaLop_item, Time_item, Monhoc_item, Tien_item;
        Button btn_Xoa,btn_Edit,btn_Detail;

        // Constructor của ViewHolder, ánh xạ các thành phần UI từ layout XML
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title_item = itemView.findViewById(R.id.Title_item);
            MaLop_item = itemView.findViewById(R.id.MaLop_item);
            Time_item = itemView.findViewById(R.id.Time_item);
            Monhoc_item = itemView.findViewById(R.id.Monhoc_item);
            Tien_item = itemView.findViewById(R.id.Tien_item);
            btn_Xoa= itemView.findViewById(R.id.btn_Delete);   //Nút Xóa dữ liệu
            btn_Edit= itemView.findViewById(R.id.btn_Edit); //Nút Sửa  dữ liệu
            btn_Detail= itemView.findViewById(R.id.btn_detail);//Nút Chi tiết dữ liệu

        }



        // Phương thức này gắn dữ liệu từ một đối tượng LopHoc vào ViewHolder
        public void bind(LopHoc lopHoc) {
            Title_item.setText(lopHoc.getTitle()); // Thiết lập tiêu đề của lớp học
            // Tương tự, bạn có thể thiết lập các trường dữ liệu khác tại đây
            Time_item.setText(lopHoc.getGioMoiBuoi());
            Monhoc_item.setText(lopHoc.getMonHoc());
            Tien_item.setText(lopHoc.getHocPhi());
        }
    }
}
