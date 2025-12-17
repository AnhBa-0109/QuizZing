package ntu.edu.nguyenquockhanh.btl_quizzing;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ntu.edu.nguyenquockhanh.btl_quizzing.adapter.CategoryAdapter;
import ntu.edu.nguyenquockhanh.btl_quizzing.database.DatabaseHelper;
import ntu.edu.nguyenquockhanh.btl_quizzing.model.Category;

public class ChonChuDe extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_chu_de);
        DatabaseHelper db = new DatabaseHelper(this);
        db.insertDefaultCategories();

        recyclerView = findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        List<Category> categories = new ArrayList<>();
        Category vpop = new Category(0, "V-Pop", R.drawable.ic_vpop,
                ContextCompat.getColor(this, R.color.vpop_start),
                ContextCompat.getColor(this, R.color.vpop_end));
        Category kpop = new Category(0, "K-Pop", R.drawable.ic_kpop,
                ContextCompat.getColor(this, R.color.kpop_start),
                ContextCompat.getColor(this, R.color.kpop_end));
        Category usuk = new Category(0, "US-UK", R.drawable.ic_usuk,
                ContextCompat.getColor(this, R.color.usuk_start),
                ContextCompat.getColor(this, R.color.usuk_end));
        Category rap = new Category(0, "Rap/HipHop", R.drawable.ic_rap,
                ContextCompat.getColor(this, R.color.rap_start),
                ContextCompat.getColor(this, R.color.rap_end));
        Category ballad = new Category(0, "K-Pop", R.drawable.ic_ballad,
                ContextCompat.getColor(this, R.color.ballad_start),
                ContextCompat.getColor(this, R.color.ballad_end));
        Category indie = new Category(0, "K-Pop", R.drawable.ic_indie,
                ContextCompat.getColor(this, R.color.indie_start),
                ContextCompat.getColor(this, R.color.indie_end));
        categories.add(vpop);
        categories.add(kpop);
        categories.add(usuk);
        categories.add(rap);
        categories.add(ballad);
        categories.add(indie);

        CategoryAdapter adapter = new CategoryAdapter(categories, this);
        recyclerView.setAdapter(adapter);

    }
}