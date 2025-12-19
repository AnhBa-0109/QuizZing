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

import ntu.edu.nguyenquockhanh.btl_quizzing.model.GameMode;

public class ManHinhMoTa extends AppCompatActivity {
    MaterialButton btn_back, btn_play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_mo_ta);
        TimDK();
        int mode = getIntent().getIntExtra("Game_Mode", GameMode.RANDOM);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChuyenMH = new Intent(ManHinhMoTa.this, MainActivity.class);
                startActivity(iChuyenMH);
                overridePendingTransition(R.anim.slide_in_from_left, android.R.anim.slide_out_right);
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChuyenMH = new Intent(ManHinhMoTa.this, ManHinhChoi.class);
                iChuyenMH.putExtra("Game_Mode",mode);
                if(mode == GameMode.BY_CATEGORY)
                {
                    int categoryId = getIntent().getIntExtra("category_id", -1);
                    iChuyenMH.putExtra("category_id", categoryId);
                }
                startActivity(iChuyenMH);
            }
        });
    }
    void TimDK()
    {
        btn_back = findViewById(R.id.btn_backMoTa);
        btn_play = findViewById(R.id.btn_playMota);
    }

}