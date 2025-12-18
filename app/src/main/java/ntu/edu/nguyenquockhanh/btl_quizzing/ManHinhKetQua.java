package ntu.edu.nguyenquockhanh.btl_quizzing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class ManHinhKetQua extends AppCompatActivity {
    MaterialButton btn_chudekhac, btn_manhinhchinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_ket_qua);

        TimDK();

        btn_chudekhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IChuyenMH = new Intent(ManHinhKetQua.this, ChonChuDe.class);
                startActivity(IChuyenMH);
                overridePendingTransition(R.anim.slide_in_from_left, android.R.anim.slide_out_right);
            }
        });

        btn_manhinhchinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IChuyenMH = new Intent(ManHinhKetQua.this, MainActivity.class);
                startActivity(IChuyenMH);
                overridePendingTransition(R.anim.slide_in_from_left, android.R.anim.slide_out_right);
            }
        });
    }
    void TimDK()
    {
        btn_chudekhac = findViewById(R.id.btn_chudekhac);
        btn_manhinhchinh = findViewById(R.id.btn_manhinhchinh);
    }
}