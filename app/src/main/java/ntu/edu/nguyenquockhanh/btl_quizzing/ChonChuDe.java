package ntu.edu.nguyenquockhanh.btl_quizzing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import ntu.edu.nguyenquockhanh.btl_quizzing.adapter.CategoryAdapter;
import ntu.edu.nguyenquockhanh.btl_quizzing.database.DatabaseHelper;
import ntu.edu.nguyenquockhanh.btl_quizzing.model.Category;

public class ChonChuDe extends AppCompatActivity {
    RecyclerView recyclerView;
    MaterialButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_chu_de);
        DatabaseHelper db = new DatabaseHelper(this);
        db.insertDefaultCategories();

        TimDK();

        btnBack.setOnClickListener(ChuyenMH);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        List<Category> categories = db.getAllCategories(this);


        CategoryAdapter adapter = new CategoryAdapter(categories, this);
        recyclerView.setAdapter(adapter);

    }
    void TimDK()
    {
        recyclerView = findViewById(R.id.recyclerView);
        btnBack = findViewById(R.id.btn_back);
    }

    View.OnClickListener ChuyenMH = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent iMH = new Intent(ChonChuDe.this, MainActivity.class);
            startActivity(iMH);
            overridePendingTransition(R.anim.slide_in_from_left, android.R.anim.slide_out_right);
        }
    };
}