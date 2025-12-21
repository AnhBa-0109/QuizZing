package ntu.edu.nguyenquockhanh.btl_quizzing;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import ntu.edu.nguyenquockhanh.btl_quizzing.model.GameMode;

public class ManHinhKetQua extends AppCompatActivity {
    MaterialButton btn_chudekhac, btn_manhinhchinh, btn_choilai;
    ImageView iv_stateCheck;
    TextView tv_caudung, tv_score;
    int score, mode, categoryId, caudung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_ket_qua);

        TimDK();
        mode = getIntent().getIntExtra("Game_Mode", GameMode.RANDOM);
        score = getIntent().getIntExtra("Score", 0);
        caudung = getIntent().getIntExtra("Correct", 0);


        btn_choilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IChuyenMH = new Intent(ManHinhKetQua.this, ManHinhChoi.class);
                IChuyenMH.putExtra("Game_Mode", mode);
                //kiểm tra nếu mode là chơi theo chủ đề thì khi bấm chơi lại sẽ load câu hỏi theo chủ đề đó
                if(mode == GameMode.BY_CATEGORY){
                    categoryId = getIntent().getIntExtra("category_id", -1);
                    IChuyenMH.putExtra("category_id", categoryId);
                }
                startActivity(IChuyenMH);
                overridePendingTransition(R.anim.slide_in_from_left, android.R.anim.slide_out_right);
            }
        });
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

        tv_score.setText(String.valueOf(score));
        setStringCorrectAnswer(caudung);

//        if(mode == GameMode.BY_CATEGORY)
//        {
//            categoryId = getIntent().getIntExtra("category_id", -1);
//            Log.i("Chế độ chơi", String.valueOf(mode));
//            Log.i("Chủ đề", String.valueOf(categoryId));
//            Log.i("Đang ở màn hình:", "Màn hình kết quả");
//        }
//        else {
//            Log.i("Chế độ chơi", String.valueOf(mode));
//        }
    }
    void TimDK()
    {
        btn_chudekhac = findViewById(R.id.btn_chudekhac);
        btn_manhinhchinh = findViewById(R.id.btn_manhinhchinh);
        btn_choilai = findViewById(R.id.btn_choilai);
        tv_caudung = findViewById(R.id.tv_caudung);
        iv_stateCheck = findViewById(R.id.iv_stateCheck);
        tv_score = findViewById(R.id.tv_score);
    }

    void setStringCorrectAnswer(int caudung)
    {
        String text = "Bạn đã trả lời dúng " + caudung + "/10 câu";

        SpannableString spannable = new SpannableString(text);

        int start = text.indexOf(String.valueOf(caudung));
        int end = start + String.valueOf(caudung).length();

        int color;
        if(caudung > 0){
            color = ContextCompat.getColor(this, R.color.green);
            iv_stateCheck.setImageResource(R.drawable.ic_check);
        }
        else{
            color = ContextCompat.getColor(this, R.color.red);
            iv_stateCheck.setImageResource(R.drawable.ic_wrong);
        }

        spannable.setSpan(
                new ForegroundColorSpan(color),
                        start,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannable.setSpan(
                new StyleSpan(Typeface.BOLD),
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        tv_caudung.setText(spannable);
    }

}