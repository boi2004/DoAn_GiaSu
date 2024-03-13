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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.ktx.Firebase;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LopHocAdapter extends FirebaseRecyclerAdapter<LopHoc,LopHocAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public LopHocAdapter(@NonNull FirebaseRecyclerOptions<LopHoc> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull LopHoc model) {
        holder.Malop.setText(model.getMaLop());
        holder.ThoiGian.setText(model.getThoiGian());
        holder.Hocphi.setText(String.valueOf(model.getHocPhi()));
        holder.Tenmonhoc.setText(model.getMonHoc());
        holder.Title.setText(model.getTitle());

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lop_hoc_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView Malop,ThoiGian,Hocphi,Tenmonhoc,Title;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            Malop = (TextView)itemView.findViewById(R.id.MaLop_item);
            ThoiGian = (TextView)itemView.findViewById(R.id.Time_item);
            Tenmonhoc = (TextView)itemView.findViewById(R.id.Monhoc_item);
            Hocphi = (TextView)itemView.findViewById(R.id.Tien_item);
            Title = (TextView)itemView.findViewById(R.id.Title_item);
        }
    }


}
