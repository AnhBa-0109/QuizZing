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

public class MainActivity extends AppCompatActivity {
    MaterialButton btn_choi, btn_chonchude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimDK();
        btn_chonchude.setOnClickListener(ChuyenMH);
    }
    void TimDK()
    {
        btn_choi = findViewById(R.id.btn_choi);
        btn_chonchude = findViewById(R.id.btn_chonchude);
    }
    View.OnClickListener ChuyenMH = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent iMH = new Intent(MainActivity.this, ChonChuDe.class);
            startActivity(iMH);
        }
    };
}