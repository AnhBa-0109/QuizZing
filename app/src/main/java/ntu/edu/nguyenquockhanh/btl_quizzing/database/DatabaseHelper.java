package ntu.edu.nguyenquockhanh.btl_quizzing.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import ntu.edu.nguyenquockhanh.btl_quizzing.R;
import ntu.edu.nguyenquockhanh.btl_quizzing.model.Category;
import ntu.edu.nguyenquockhanh.btl_quizzing.model.Question;
import ntu.edu.nguyenquockhanh.btl_quizzing.model.Score;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "sql_android.db";
    private static final int DB_VERSION = 4;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Tạo bảng
    @Override
    public void onCreate(SQLiteDatabase db) {

        //tạo bảng Category
        db.execSQL("CREATE TABLE Category("
                + "category_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "category_name TEXT NOT NULL)");

        //tạo bảng Question
        db.execSQL("CREATE TABLE Question (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "question_text TEXT," +
                "answer1 TEXT," +
                "answer2 TEXT," +
                "answer3 TEXT," +
                "answer4 TEXT," +
                "correct_answer TEXT," +
                "audio_file TEXT)");

        //tạo bảng Score
        db.execSQL("CREATE TABLE Score (" +
                "id INTEGER PRIMARY KEY," +
                "username TEXT," +
                "score INTEGER)");

        //tạo bảng Question_Category
        db.execSQL("CREATE TABLE Question_Category (" +
                "question_id, INTEGER" +
                "category_id INTEGER," +
                "PRIMARY KEY (question_id, category_id)," +
                "FOREIGN KEY (question_id) REFERENCES Question(id)," +
                "FOREIGN KEY (category_id) REFERENCES Category(category_id))");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Category");
        db.execSQL("DROP TABLE IF EXISTS Question");
        db.execSQL("DROP TABLE IF EXISTS Score");
        onCreate(db);
    }


    //Bảng Category
    //Thêm chủ đề
    public void addCategory(String name) {
        //Mở cửa Taxi
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Cho Taxi biết địa chỉ mà dữ liệu muốn đến
        values.put("category_name", name);
        //Taxi đưa dữ liệu đi đến
        db.insert("Category", null, values);
    }

    //Link icon và background đến từng chủ đề
    private void mapUIForCategory(Category c, Context context) {
        switch (c.getName()) {
            case "V-Pop":
                c.setIconResId(R.drawable.ic_vpop);
                c.setStartColor(ContextCompat.getColor(context, R.color.vpop_start));
                c.setEndColor(ContextCompat.getColor(context, R.color.vpop_end));
                break;
            case "K-Pop":
                c.setIconResId(R.drawable.ic_kpop);
                c.setStartColor(ContextCompat.getColor(context, R.color.kpop_start));
                c.setEndColor(ContextCompat.getColor(context, R.color.kpop_end));
                break;
            case "US-UK":
                c.setIconResId(R.drawable.ic_usuk);
                c.setStartColor(ContextCompat.getColor(context, R.color.usuk_start));
                c.setEndColor(ContextCompat.getColor(context, R.color.usuk_end));
                break;
            case "Rap/HipHop":
                c.setIconResId(R.drawable.ic_rap);
                c.setStartColor(ContextCompat.getColor(context, R.color.rap_start));
                c.setEndColor(ContextCompat.getColor(context, R.color.rap_end));
                break;
            case "Indie":
                c.setIconResId(R.drawable.ic_indie);
                c.setStartColor(ContextCompat.getColor(context, R.color.indie_start));
                c.setEndColor(ContextCompat.getColor(context, R.color.indie_end));
                break;

            case "Ballad":
                c.setIconResId(R.drawable.ic_ballad);
                c.setStartColor(ContextCompat.getColor(context, R.color.ballad_start));
                c.setEndColor(ContextCompat.getColor(context, R.color.ballad_end));
                break;
        }
    }
    public List<Category> getAllCategories(Context context) {
        List<Category> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Category", null);

        if (cursor.moveToFirst()) {
            do {
                Category c = new Category();
                c.setId(cursor.getInt(0));
                c.setName(cursor.getString(1));

                // Mapping icon + color
                mapUIForCategory(c, context);

                list.add(c);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }


    //Bảng Score
    //Thêm dữ liệu mẫu
    public Score getScore() {
        SQLiteDatabase db = getReadableDatabase();
        Score score = null;

        Cursor cursor = db.rawQuery(
                "SELECT * FROM Score WHERE id = 1",
                null
        );

        if (cursor.moveToFirst()) {
            score = new Score(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            );
        }

        cursor.close();
        return score;
    }
    public void updateHighScoreIfNeeded(int newScore) {
        SQLiteDatabase db = getWritableDatabase();

        Score currentScore = getScore();
        //Kiểm tra nếu người chơi chưa có điểm thì sẽ cập nhật luôn
        if(currentScore == null) {
            ContentValues cv = new ContentValues();
            cv.put("id",1);
            cv.put("username", "Anh Ba");
            cv.put("score", newScore);
            db.insert("Score",null,cv);
            return;
        }

        if (newScore > currentScore.getScore()) {
            ContentValues cv = new ContentValues();
            cv.put("score", newScore);

            db.update("Score", cv, "id = ?", new String[]{"1"});
        }
    }



    //Bảng Question

    //Lấy ngẫu nhiên câu hỏi
    public List<Question> getRandomQuestions(int limit) {
        List<Question> questions = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Question ORDER BY RANDOM() LIMIT 10", null);

        while(cursor.moveToNext()) {
            Question question = new Question();
            question.id = cursor.getInt(0);
            question.questionText = cursor.getString(1);
            question.answer1 = cursor.getString(2);
            question.answer2 = cursor.getString(3);
            question.answer3 = cursor.getString(4);
            question.answer4 = cursor.getString(5);
            question.correctAnswer = cursor.getString(6);
            question.audioFile = cursor.getString(7);
            questions.add(question);
        }
        cursor.close();
        return questions;
    }

    //Lấy ngẫu nhiên câu hỏi theo chủ đề
    public List<Question> getRandomQuestionsByCategory(int categoryId, int limit) {
        List<Question> questions = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT q.* FROM Question q " +
                        "JOIN Question_Category qc ON q.id = qc.question_id " +
                        "WHERE qc.category_id = ? " +
                        "ORDER BY RANDOM() LIMIT 10",
                new String[]{String.valueOf(categoryId), String.valueOf(limit)}
        );

        while(cursor.moveToNext()) {
            Question question = new Question();
            question.id = cursor.getInt(0);
            question.questionText = cursor.getString(1);
            question.answer1 = cursor.getString(2);
            question.answer2 = cursor.getString(3);
            question.answer3 = cursor.getString(4);
            question.answer4 = cursor.getString(5);
            question.correctAnswer = cursor.getString(6);
            question.audioFile = cursor.getString(7);
            questions.add(question);
        }

        cursor.close();
        return questions;
    }



}
