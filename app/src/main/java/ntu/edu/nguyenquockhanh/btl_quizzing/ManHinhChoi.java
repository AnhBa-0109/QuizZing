package ntu.edu.nguyenquockhanh.btl_quizzing;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class ManHinhChoi extends AppCompatActivity {
    MaterialButton btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_choi);
        TimDK();

        btn_back.setOnClickListener(v -> showExitDialog());
        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        showExitDialog();
                    }
                }
        );
    }
    void TimDK()
    {
        btn_back = findViewById(R.id.btn_backChoi);
    }
    private void showExitDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_exit_game);
        dialog.setCancelable(false);

        MaterialButton btnExit = dialog.findViewById(R.id.btn_exit);
        MaterialButton btnStay = dialog.findViewById(R.id.btn_stay);

        btnStay.setOnClickListener(v ->{
            dialog.dismiss();
        });


        btnExit.setOnClickListener(v -> {
            dialog.setOnDismissListener(d -> {
                Intent intent = new Intent(ManHinhChoi.this, MainActivity.class);
                intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_SINGLE_TOP
                );
                startActivity(intent);

                overridePendingTransition(
                        R.anim.slide_in_from_left,
                        R.anim.slide_out_right
                );
            });

            dialog.dismiss();
        });

        btnStay.setOnClickListener(v -> dialog.dismiss());

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationFast;
        }

        dialog.show();
    }

}