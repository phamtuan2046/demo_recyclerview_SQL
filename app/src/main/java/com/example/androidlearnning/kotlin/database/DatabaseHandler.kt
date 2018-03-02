package com.example.androidlearnning.kotlin.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.androidlearnning.kotlin.models.Student

/**
 * Created by FRAMGIA\pham.dinh.tuan on 02/03/2018.
 */
class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {

        // All Static variables
        // Database Version
        private val DATABASE_VERSION = 1

        // Database Name
        private val DATABASE_NAME = "demo"

        // Contacts table name
        private val TABLE_STUDENT = "Student"

        // Contacts Table Columns names
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_AGE = "age"
        private val KEY_GENDER = "gender"
    }


    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_STUDENT_TABLE = ("CREATE TABLE " + TABLE_STUDENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_AGE + " TEXT,"
                + KEY_GENDER + " INTEGER" + ")")
        db.execSQL(CREATE_STUDENT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT)

        // Create tables again
        onCreate(db)
    }

    // Addew Student
    internal fun addStudent(student: Student) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_NAME, student.name)
        values.put(KEY_AGE, student.age)
        values.put(KEY_GENDER, student.gender)

        // Inserting Row
        db.insert(TABLE_STUDENT, null, values)
        db.close() // Closing database connection
    }

    // Getting student by id
    internal fun getStudent(id: Int): Student {
        val db = this.readableDatabase

        val cursor = db.query(TABLE_STUDENT, arrayOf(KEY_ID, KEY_NAME, KEY_AGE, KEY_GENDER), KEY_ID + "=?",
                arrayOf(id.toString()), null, null, null, null)
        cursor?.moveToFirst()

        val student = Student()
        student.id = Integer.parseInt(cursor!!.getString(0))
        student.name = cursor.getString(1)
        student.age = cursor.getString(2)
        student.gender = Integer.parseInt(cursor!!.getString(3))
        return student
    }


    // get all student
    fun getAllStudent(): List<Student> {
        var students = ArrayList<Student>()
        // Select All Query
        val selectQuery = "SELECT  * FROM " + TABLE_STUDENT

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val student = Student()
                student.id = Integer.parseInt(cursor.getString(0))
                student.name = cursor.getString(1)
                student.age = cursor.getString(2)
                student.gender = Integer.parseInt(cursor.getString(3))
                students.add(student)
            } while (cursor.moveToNext())
        }
        return students
    }

    // update student
    fun updateStudent(student: Student): Int {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(KEY_NAME, student.name)
        values.put(KEY_AGE, student.age)
        values.put(KEY_GENDER, student.gender)

        // updating row
        return db.update(TABLE_STUDENT, values, KEY_ID + " = ?",
                arrayOf(student.id.toString()))
    }

    // Deleting single contact
    fun deleteStudent(student: Student) {
        val db = this.writableDatabase
        db.delete(TABLE_STUDENT, KEY_ID + " = ?",
                arrayOf(student.id.toString()))
        db.close()
    }
}