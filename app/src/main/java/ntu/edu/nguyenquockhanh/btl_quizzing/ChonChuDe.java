package ntu.edu.nguyenquockhanh.btl_quizzing;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ntu.edu.nguyenquockhanh.btl_quizzing.database.DatabaseHelper;

public class ChonChuDe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_chu_de);
        DatabaseHelper db = new DatabaseHelper(this);
        db.insertDefaultCategories();
        db.getAllCategories();
    }
}