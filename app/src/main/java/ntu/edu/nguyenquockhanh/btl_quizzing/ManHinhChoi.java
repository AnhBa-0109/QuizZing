    package ntu.edu.nguyenquockhanh.btl_quizzing;

    import android.app.Dialog;
    import android.content.Intent;
    import android.graphics.Color;
    import android.graphics.drawable.ColorDrawable;
    import android.media.MediaPlayer;
    import android.os.Bundle;
    import android.os.CountDownTimer;
    import android.os.Handler;
    import android.os.Looper;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.ProgressBar;
    import android.widget.TextView;

    import androidx.activity.EdgeToEdge;
    import androidx.activity.OnBackPressedCallback;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import com.google.android.material.button.MaterialButton;

    import java.util.Collection;
    import java.util.Collections;
    import java.util.List;

    import ntu.edu.nguyenquockhanh.btl_quizzing.database.DatabaseHelper;
    import ntu.edu.nguyenquockhanh.btl_quizzing.model.GameMode;
    import ntu.edu.nguyenquockhanh.btl_quizzing.model.Question;

    public class ManHinhChoi extends AppCompatActivity {
        MaterialButton btn_back, btn_da1, btn_da2, btn_da3, btn_da4;
        TextView tv_cau, tv_diem, tv_time, tv_CauHoi, tv_statePhatNhac;
        ImageButton ib_playpause;
        List<Question> dsCauHoi;
        ProgressBar progressBar;
        int currentQuestion = 0;
        int score = 0;

        CountDownTimer timer;
        MediaPlayer mediaPlayer;

        DatabaseHelper db;

        int mode = getIntent().getIntExtra("Game_Mode", GameMode.RANDOM);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
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

            db = new DatabaseHelper(this);
            dsCauHoi = db.getALlQuestion();
            Collections.shuffle(dsCauHoi);

            loadQuestion();
            startTimer();
        }
        void TimDK() {
            btn_back = findViewById(R.id.btn_backChoi);
            btn_da1 = findViewById(R.id.btn_DA1);
            btn_da2 = findViewById(R.id.btn_DA2);
            btn_da3 = findViewById(R.id.btn_DA3);
            btn_da4 = findViewById(R.id.btn_DA4);

            tv_cau = findViewById(R.id.Cau);
            tv_diem = findViewById(R.id.tv_diem);
            tv_time = findViewById(R.id.tv_time);
            tv_CauHoi = findViewById(R.id.tv_CauHoi);
            tv_statePhatNhac = findViewById(R.id.state_phatnhac);

            ib_playpause = findViewById(R.id.ib_playpause);

            progressBar = findViewById(R.id.progress);
        }

        private void loadQuestion() {
            Question q = dsCauHoi.get(currentQuestion);
            tv_cau.setText((currentQuestion + 1) + "/10");
            tv_CauHoi.setText(q.questionText);

            btn_da1.setText(q.answer1);
            btn_da2.setText(q.answer2);
            btn_da3.setText(q.answer3);
            btn_da4.setText(q.answer4);

            tv_statePhatNhac.setText("Click vào nút Play để phát");
        }
        private void startTimer() {
            progressBar.setMax(20);
            timer = new CountDownTimer(20000, 1000) {
                public void onTick(long ms) {
                    int s = (int) (ms / 1000);
                    tv_time.setText(s + "s");
                    progressBar.setProgress(s);
                }

                public void onFinish() {
                    nextQuestion();
                }
            }.start();
        }
        View.OnClickListener answerClick = v -> {
            timer.cancel();
            Button b = (Button) v;

            if (b.getText().toString().equals(
                    dsCauHoi.get(currentQuestion).correctAnswer)) {
                b.setBackgroundColor(Color.GREEN);
                score += 100;
                tv_diem.setText(String.valueOf(score));
            } else {
                b.setBackgroundColor(Color.RED);
            }

            new Handler().postDelayed(this::nextQuestion, 800);
        };
        private void playAudio() {
            if (mediaPlayer != null) mediaPlayer.release();
            tv_statePhatNhac.setText("Đang phát đoạn nhạc...");
            mediaPlayer = MediaPlayer.create(this,
                    getResources().getIdentifier(
                            dsCauHoi.get(currentQuestion).audioFile,
                            "raw",
                            getPackageName()));
            mediaPlayer.start();
        }
        private void nextQuestion() {
            currentQuestion++;
            if (currentQuestion == 10) {
                db.updateHighScoreIfNeeded(score);
                finish();
                return;
            }
            loadQuestion();
            startTimer();
        }

        //Hàm hiện hộp thoại xác nhận thoát
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