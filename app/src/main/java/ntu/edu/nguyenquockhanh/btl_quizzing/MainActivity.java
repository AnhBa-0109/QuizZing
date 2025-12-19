package ntu.edu.nguyenquockhanh.btl_quizzing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import ntu.edu.nguyenquockhanh.btl_quizzing.database.DatabaseHelper;
import ntu.edu.nguyenquockhanh.btl_quizzing.model.GameMode;
import ntu.edu.nguyenquockhanh.btl_quizzing.model.Score;

public class MainActivity extends AppCompatActivity {
    MaterialButton btn_choi, btn_chonchude;
    TextView tv_name, tv_score;

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimDK();
        btn_choi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMH = new Intent(MainActivity.this, ManHinhMoTa.class);
                iMH.putExtra("Game_Mode", GameMode.RANDOM);
                startActivity(iMH);
            }
        });
        btn_chonchude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMH = new Intent(MainActivity.this, ChonChuDe.class);
                startActivity(iMH);
            }
        });

        db = new DatabaseHelper(this);

        Score user = db.getScore();

        if(user != null)
        {
            tv_name.setText(user.getUsername());
            tv_score.setText(String.valueOf(user.getScore()));
        }

    }
    void TimDK()
    {
        btn_choi = findViewById(R.id.btn_choi);
        btn_chonchude = findViewById(R.id.btn_chonchude);
        tv_name = findViewById(R.id.tv_nameMHC);
        tv_score = findViewById(R.id.tv_best_score_value);
    }
}