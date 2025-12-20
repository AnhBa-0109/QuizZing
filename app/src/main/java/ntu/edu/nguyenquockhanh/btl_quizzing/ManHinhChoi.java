    package ntu.edu.nguyenquockhanh.btl_quizzing;

    import android.app.Dialog;
    import android.content.Intent;
    import android.content.res.ColorStateList;
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
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.activity.OnBackPressedCallback;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.content.ContextCompat;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import com.google.android.material.button.MaterialButton;
    import com.google.android.material.card.MaterialCardView;

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
        MaterialCardView cardAudio;
        int currentQuestion = 0;
        int score = 0;
        int correctAnswer = 0;
        CountDownTimer timer;
        MediaPlayer mediaPlayer;

        DatabaseHelper db;
        boolean isPlaying = false;
        int mode;
        int categoryId = -1;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_man_hinh_choi);
            TimDK();
            score = 0;
            tv_diem.setText("0");
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
            ib_playpause.setOnClickListener(v -> {
                if (isPlaying) {
                    stopAudio();
                } else {
                    playAudio();
                }
            });

            //Lấy dữ liệu để biết người chơi chọn mode nào
            mode = getIntent().getIntExtra("Game_Mode", GameMode.RANDOM);
            if(mode == GameMode.BY_CATEGORY) categoryId = getIntent().getIntExtra("category_id", -1);
            db = new DatabaseHelper(this);


            db.insertDefaultCategories();
            db.insertDefaultQuestions();

            loadQuestionsByMode();

            if (dsCauHoi == null || dsCauHoi.size() == 0) {
                Toast.makeText(this, "Không có câu hỏi!", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            btn_da1.setOnClickListener(answerClick);
            btn_da2.setOnClickListener(answerClick);
            btn_da3.setOnClickListener(answerClick);
            btn_da4.setOnClickListener(answerClick);

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

            cardAudio = findViewById(R.id.card_audio);
        }

        private void loadQuestion() {
            Question q = dsCauHoi.get(currentQuestion);
            tv_cau.setText((currentQuestion + 1) + "/10");
            tv_CauHoi.setText(q.questionText);

            btn_da1.setText(q.answer1);
            btn_da2.setText(q.answer2);
            btn_da3.setText(q.answer3);
            btn_da4.setText(q.answer4);

            //Xử lý câu hỏi không có audio file thì sẽ ẩn giao diện audio
            if(q.audioFile == null || q.audioFile.trim().isEmpty()){
                cardAudio.setVisibility(View.GONE);
                stopAudio();
            }
            else {
                cardAudio.setVisibility(View.VISIBLE);
                tv_statePhatNhac.setText("Click nút Play để phát");
            }

        }
        private void loadQuestionsByMode() {
            if (mode == GameMode.BY_CATEGORY) {
                dsCauHoi = db.getRandomQuestionsByCategory(categoryId);
            } else {
                dsCauHoi = db.getRandomQuestions();
            }
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
                    highlightCorrectAnswer();
                    disableAnswerButtons();

                    new Handler(Looper.getMainLooper())
                            .postDelayed(() -> nextQuestion(), 800);
                }
            }.start();
        }
        private void disableAnswerButtons() {
            btn_da1.setEnabled(false);
            btn_da2.setEnabled(false);
            btn_da3.setEnabled(false);
            btn_da4.setEnabled(false);
        }
        View.OnClickListener answerClick = v -> {
            timer.cancel();
            Button b = (Button) v;

            if (b.getText().toString().equals(
                    dsCauHoi.get(currentQuestion).correctAnswer)) {
                b.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                score += 100;
                correctAnswer++;
                tv_diem.setText(String.valueOf(score));
            } else {
                b.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            }
            disableAnswerButtons();

            new Handler().postDelayed(this::nextQuestion, 800);
        };


        private void stopAudio() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            isPlaying = false;
            ib_playpause.setImageResource(R.drawable.ic_play);
            tv_statePhatNhac.setText("Click vào nút Play để phát nhạc...");
        }
        private void playAudio() {
            stopAudio();

            String audioName = dsCauHoi.get(currentQuestion).audioFile;
            if (audioName == null) return;

            mediaPlayer = MediaPlayer.create(this,
                    getResources().getIdentifier(audioName, "raw", getPackageName()));
            mediaPlayer.start();

            isPlaying = true;
            ib_playpause.setImageResource(R.drawable.ic_pause);
            tv_statePhatNhac.setText("Đang phát đoạn nhạc...");
        }

        private void resetAnswerUI() {
            ColorStateList defaultTint =
                    ContextCompat.getColorStateList(this, R.color.grey_black);

            btn_da1.setBackgroundTintList(defaultTint);
            btn_da2.setBackgroundTintList(defaultTint);
            btn_da3.setBackgroundTintList(defaultTint);
            btn_da4.setBackgroundTintList(defaultTint);

            btn_da1.setEnabled(true);
            btn_da2.setEnabled(true);
            btn_da3.setEnabled(true);
            btn_da4.setEnabled(true);
        }
        private void nextQuestion() {
            stopAudio();

            currentQuestion++;
            if (currentQuestion >= dsCauHoi.size()) {
                db.updateHighScoreIfNeeded(score);
                Intent iChuyen = new Intent(ManHinhChoi.this, ManHinhKetQua.class);
                iChuyen.putExtra("Game_Mode", mode);
                iChuyen.putExtra("Score", score);
                iChuyen.putExtra("Correct", correctAnswer);
                iChuyen.putExtra("category_id", categoryId);
                startActivity(iChuyen);
                return;
            }

            resetAnswerUI();
            loadQuestion();
            startTimer();
        }
        private void highlightCorrectAnswer() {
            String correct = dsCauHoi.get(currentQuestion).correctAnswer;

            for (MaterialButton b :
                    new MaterialButton[]{btn_da1, btn_da2, btn_da3, btn_da4}) {
                if (b.getText().toString().equals(correct)) {
                    b.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                }
            }
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