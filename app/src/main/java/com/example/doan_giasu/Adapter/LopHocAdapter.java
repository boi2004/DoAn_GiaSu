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
import java.text.DecimalFormat;
import java.util.List;

public class LopHocAdapter extends RecyclerView.Adapter<LopHocAdapter.MyViewHolder> {
    private List<LopHoc> listlophoc;
    private OnItemClickListener listener;

    public LopHocAdapter(List<LopHoc> listlophoc) {
        this.listlophoc = listlophoc;
    }

    public interface OnItemClickListener {
        void onItemClick(LopHoc lophoc);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lop_hoc_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LopHoc lophoc = listlophoc.get(position);
        holder.Title_item.setText(lophoc.getTitle());
        holder.MaLop_item.setText(String.valueOf(lophoc.getMaLop()));
        holder.Monhoc_item.setText(lophoc.getMonHoc());

        // Format và hiển thị số tiền
        DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
        String formattedHocPhi = decimalFormat.format(lophoc.getHocPhi()) + "đ";
        holder.Tien_item.setText(formattedHocPhi);

        holder.Time_item.setText(lophoc.getThoiGian());

        // Sự kiện nhấp vào item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(lophoc);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listlophoc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Title_item, MaLop_item, Time_item, Monhoc_item, Tien_item;
        private Button ChiTiet_item;
        private CardView card_view_item;

        public MyViewHolder(@NonNull View itemView) {
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
