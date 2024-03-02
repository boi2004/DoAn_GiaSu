package com.example.doan_giasu.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_giasu.Model.LopHoc;
import com.example.doan_giasu.R;

import java.util.List;

public class LopHocAdapter extends RecyclerView.Adapter<LopHocAdapter.MyViewHoler> {
    private List<LopHoc> listlophoc;

    public LopHocAdapter(List<LopHoc> listlophoc) {
        this.listlophoc = listlophoc;
    }

    @NonNull
    @Override
    public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lop_hoc_item,parent,false);
        return new MyViewHoler(view);
        // ánh xạ layout_item qua view
    }

    @Override
    public int getItemCount() {
        return listlophoc.size(); //số lượng item
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {
        LopHoc lophoc = listlophoc.get(position);
        holder.Title_item.setText(lophoc.getTitle());
        holder.MaLop_item.setText(lophoc.getMaLop());
        holder.Monhoc_item.setText(lophoc.getMonHoc());
        holder.Tien_item.setText(lophoc.getHocPhi());
        holder.Time_item.setText(lophoc.getThoiGian());


    }

    class MyViewHoler extends RecyclerView.ViewHolder{
     private TextView Title_item,MaLop_item,Time_item,Monhoc_item,Tien_item;
     private Button ChiTiet_item;
     private CardView card_view_item;

     public MyViewHoler(@NonNull View itemView) {
         super(itemView);
         Title_item = itemView.findViewById(R.id.Title_item);
         MaLop_item = itemView.findViewById(R.id.MaLop_item);
         Time_item = itemView.findViewById(R.id.Time_item);
         Monhoc_item = itemView.findViewById(R.id.Monhoc_item);
         Tien_item = itemView.findViewById(R.id.Tien_item);
         ChiTiet_item = itemView.findViewById(R.id.ChiTiet_item);
         card_view_item = itemView.findViewById(R.id.card_view_item);

     }
 }
}
