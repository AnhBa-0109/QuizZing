package ntu.edu.nguyenquockhanh.btl_quizzing.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ntu.edu.nguyenquockhanh.btl_quizzing.model.Category;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "sql_android.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Tạo bảng
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Category("
                + "category_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "category_name TEXT NOT NULL)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Category");
        onCreate(db);
    }

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

    //Thêm chủ đề mẫu
    public void insertDefaultCategories() {
        //Mở database
        SQLiteDatabase db = this.getWritableDatabase();

        //Dùng con trỏ trỏ đến kết quả query
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Category", null);
        //Kiểm tra điều kiện nếu bảng Category rỗng thì mới add dữ liệu mẫu.
        if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
            addCategory("V-Pop");
            addCategory("K-Pop");
            addCategory("Rap/HipHop");
            addCategory("US-UK");
            addCategory("Indie");
            addCategory("Ballad");
        }
        cursor.close();
    }
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Category", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);

                Log.d("DB_TEST", "ID=" + id + ", NAME=" + name);

                list.add(new Category(id, name));
            } while (cursor.moveToNext());
        } else {
            Log.d("DB_TEST", "Category table is EMPTY");
        }

        cursor.close();
        return list;
    }
}
