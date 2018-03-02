package com.example.androidlearnning.java.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.androidlearnning.java.models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FRAMGIA\pham.dinh.tuan on 02/03/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "demo";

    // Contacts table name
    private static final String TABLE_STUDENT = "Student";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    private static final String KEY_GENDER = "gender";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_AGE + " INTEGER,"
                + KEY_GENDER + " INTEGER" + ")";
        db.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);

        // Create tables again
        onCreate(db);
    }


    // Addew Student
    void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_AGE, student.getAge());
        values.put(KEY_GENDER, student.getGender());

        // Inserting Row
        db.insert(TABLE_STUDENT, null, values);
        db.close(); // Closing database connection
    }

    // Getting student by id
    Student getStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STUDENT, new String[]{KEY_ID,
                        KEY_NAME, KEY_AGE, KEY_GENDER}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Student student = new Student();
        student.setId(Integer.parseInt(cursor.getString(0)));
        student.setName(cursor.getString(1));
        student.setAge(Integer.parseInt(cursor.getString(2)));
        student.setGender(Integer.parseInt(cursor.getString(3)));
        return student;
    }

    // get all student
    public List<Student> getAllStudent() {
        List<Student> students = new ArrayList<Student>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(Integer.parseInt(cursor.getString(0)));
                student.setName(cursor.getString(1));
                student.setAge(Integer.parseInt(cursor.getString(2)));
                student.setGender(Integer.parseInt(cursor.getString(3)));
                students.add(student);
            } while (cursor.moveToNext());
        }
        return students;
    }

    // update student
    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_AGE, student.getAge());
        values.put(KEY_GENDER, student.getGender());

        // updating row
        return db.update(TABLE_STUDENT, values, KEY_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
    }

    // Deleting single contact
    public void deleteStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENT, KEY_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
        db.close();
    }

}
