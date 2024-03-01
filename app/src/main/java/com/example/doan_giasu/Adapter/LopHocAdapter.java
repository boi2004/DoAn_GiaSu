package com.example.doan_giasu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_giasu.Model.LopHoc;
import com.example.doan_giasu.R;
import com.example.doan_giasu.RecyclerViewLopHocActivity;

import org.w3c.dom.Text;

//2. Lại tiếp tục kế thừa LopHocAdapter cho RecyclerView.ViewHolder
public class LopHocAdapter extends RecyclerView.Adapter<LopHocAdapter.ViewHoler>{

    //Khởi tạo biến danh sách dựa tren model
    private ArrayAdapter<LopHoc> alLopHoc;
    private Context context;

    public LopHocAdapter(ArrayAdapter<LopHoc> alLopHoc, Context context) {
        this.alLopHoc = alLopHoc;
        this.context = context;
    }

    @NonNull
    @Override //onCreateViewHolder : Khai báo lop_hoc_item
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lop_hoc_item, parent, false);
        return new ViewHoler(view);

    }

    @Override // onBindViewHolder : chuyển dữ liệu phần tử vào ViewHolde
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.MaLop_item.setText(String.valueOf(alLopHoc.getItem(position).getMaLop()));
        holder.Title_item.setText(alLopHoc.getItem(position).getTitle());
        holder.Monhoc_item.setText(alLopHoc.getItem(position).getMonHoc());
        holder.Tien_item.setText(String.valueOf(alLopHoc.getItem(position).getHocPhi()));
        holder.Time_item.setText(alLopHoc.getItem(position).getThoiGian());
        holder.ChiTiet_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"dang chon nut" + (holder.getAdapterPosition() + 1 ), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override //getItemCount() : cho biết số phần tử của dữ liệu
    public int getItemCount() {
        return alLopHoc.getCount() ;
    }

//1. extends: kế thừa. Trong Adapter sử dụng một lớp
//tên ViewHolder kế thừa RecyclerView.ViewHolder, nó giúp View hiện thị dữ liệu
    public class ViewHoler extends RecyclerView.ViewHolder{
    public TextView Title_item, MaLop_item, Time_item, Monhoc_item, Tien_item;
    public Button ChiTiet_item;

    public ViewHoler(@NonNull View itemView) {
        super(itemView);
        addControls();
    }

    private void addControls() {
        Title_item = itemView.findViewById(R.id.Title_item);
        MaLop_item = itemView.findViewById(R.id.MaLop_item);
        Time_item = itemView.findViewById(R.id.Time_item);
        Monhoc_item = itemView.findViewById(R.id.Monhoc_item);
        Tien_item = itemView.findViewById(R.id.Tien_item);
        ChiTiet_item = itemView.findViewById(R.id.ChiTiet_item);
    }


}
}
