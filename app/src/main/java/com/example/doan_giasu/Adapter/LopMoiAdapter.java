package com.example.doan_giasu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_giasu.Model.LopHoc;
import com.example.doan_giasu.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LopMoiAdapter extends RecyclerView.Adapter<LopMoiAdapter.viewholder> {
    ArrayList<LopHoc> arraylophoc;
    Context contextlophoc;
    private List<LopHoc> listLopHoc;

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
    }

    @Override
    public int getItemCount() {
        return listLopHoc != null ? listLopHoc.size():0;
    }

    public class viewholder extends RecyclerView.ViewHolder{
        //ánh xạ id trong layout lop moi
        TextView Title_item, MaLop_item, Time_item, Monhoc_item, Tien_item;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            Title_item = itemView.findViewById(R.id.Title_item);
            MaLop_item = itemView.findViewById(R.id.MaLop_item);
            Time_item = itemView.findViewById(R.id.Time_item);
            Monhoc_item = itemView.findViewById(R.id.Monhoc_item);
            Tien_item = itemView.findViewById(R.id.Tien_item);


        }
    }
}
