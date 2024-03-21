package Activity_Menu;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_giasu.Adapter.LopMoiAdapter;
import com.example.doan_giasu.Adapter.LophocLopday_Adapter;
import com.example.doan_giasu.Model.LopHoc;
import com.example.doan_giasu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Danhsachlophoc_Activity extends AppCompatActivity {
    RecyclerView rcv;
    private LophocLopday_Adapter lophocLopdayAdapter;
    private List<LopHoc> ListLopHoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachlophoc);

        //Toolbal
        Toolbar toolbar = findViewById(R.id.toolbar_DanhSachLopHoc);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh Sách Lớp Học");
        int white = getResources().getColor(android.R.color.white);
        toolbar.setTitleTextColor(white);

        // Khởi tạo RecyclerView
        rcv = findViewById(R.id.list_rcv_lophoc);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcv.addItemDecoration(dividerItemDecoration);

        // Khởi tạo danh sách lớp học và adapter
        ListLopHoc = new ArrayList<>();
        lophocLopdayAdapter = new LophocLopday_Adapter(ListLopHoc);

        //Lấy id người dùng
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();

        // Lấy danh sách lớp học từ Firebase
        LayDanhSachLopHocTuData(userId);

        // Gán adapter cho RecyclerView
        rcv.setAdapter( lophocLopdayAdapter);
    }

    // Hàm thoát ra trên toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Phương thức lấy danh sách lớp học đã tạo từ Firebase
    private void LayDanhSachLopHocTuData(String userId) {
        DatabaseReference userClassesRef = FirebaseDatabase.getInstance().getReference("LopMoi").child(userId);
        userClassesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ListLopHoc.clear(); // Xóa dữ liệu cũ trước khi cập nhật mới
                    for (DataSnapshot lopHocSnapshot : dataSnapshot.getChildren()) {
                        // Lấy thông tin của lớp học
                        LopHoc lopHoc = lopHocSnapshot.getValue(LopHoc.class);
                        Log.d("FirebaseData", "LopMoi: " + lopHoc.toString()); // In ra dữ liệu của mỗi lớp học
                        ListLopHoc.add(lopHoc);
                    }
                    lophocLopdayAdapter.notifyDataSetChanged(); // Thông báo cho adapter rằng dữ liệu đã thay đổi
                } else {
                    Log.d("FirebaseData", "No data available");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Danhsachlophoc_Activity.this, "Lỗi khi lấy dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
