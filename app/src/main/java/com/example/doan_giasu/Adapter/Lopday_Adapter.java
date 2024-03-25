package com.example.doan_giasu.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.List;

import Edit_Detail.Detail_DanhSachLopHoc_Activity;
import Edit_Detail.Detail_Danhsachlopday_Activity;
import Edit_Detail.Edit_DanhSachLopHoc_Activity;

public class Lopday_Adapter extends RecyclerView.Adapter<Lopday_Adapter.ViewHolder> {
    private List<LopHoc> listLopHoc; // Danh sách các lớp học
    private Context context; // Context của ứng dụng



    public Lopday_Adapter(Context context, List<LopHoc> listLopHoc) {
        this.context = context;
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
            addEvent();
        }
        //Trong Adapter, bạn không thể trực tiếp chuyển đổi hoạt động giữa các Activity như bạn làm trong Activity chính. Thay vào đó, bạn nên sử dụng các Interface để tương tác giữa Adapter và Activity.
        public void addEvent(){
            btn_Detail.setOnClickListener(new View.OnClickListener() {
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

                        // 3. Tạo Intent và truyền ID của lớp học
                        Intent intent = new Intent(context, Detail_Danhsachlopday_Activity.class);
                        intent.putExtra("LopMoi", lopHocId);
                        intent.putExtra("iduser",userId);

                        // 4. Khởi chạy Intent để chuyển sang Edit_Activity
                        context.startActivity(intent);
                    }
                }
            });
            btn_Xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // 2. Lấy ID của lớp học từ danh sách dựa vào vị trí của item
                        String lopHocId = listLopHoc.get(position).getID();
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        String userId = currentUser.getUid();

                        // 3. Tạo DatabaseReference để tham chiếu đến nút Firebase muốn xóa
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Danh sách đăng ký nhận lớp").child(userId).child(lopHocId);

                        // Hiển thị hộp thoại xác nhận
                        new AlertDialog.Builder(context)
                                .setTitle("Xác nhận xóa")
                                .setMessage("Bạn có chắc chắn muốn xóa lớp học này?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 4. Xóa dữ liệu
                                        reference.removeValue()
                                                .addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()) {
                                                        // Xóa thành công
                                                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        // Xóa thất bại
                                                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null) // Không làm gì nếu người dùng không đồng ý
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
            });
            btn_Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    {
                        Toast.makeText(context, "Ban không có quyền truy cập", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        // Phương thức này gắn dữ liệu từ một đối tượng LopHoc vào ViewHolder
        public void bind(LopHoc lopHoc) {
            Title_item.setText(lopHoc.getTitle()); // Thiết lập tiêu đề của lớp học
            Time_item.setText(lopHoc.getGioMoiBuoi());
            Monhoc_item.setText(lopHoc.getMonHoc());
            Tien_item.setText(lopHoc.getHocPhi());
            MaLop_item.setText(lopHoc.getID());

        }
    }
}
