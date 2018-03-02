package com.example.androidlearnning.kotlin.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.androidlearnning.demo.R
import com.example.androidlearnning.kotlin.database.DatabaseHandler
import com.example.androidlearnning.kotlin.models.Student
import kotlinx.android.synthetic.main.activity_student.*

/**
 * Created by FRAMGIA\pham.dinh.tuan on 02/03/2018.
 */
class StudentActivity : AppCompatActivity() {
    var mStudent: Student? = null
    var DatabaseHandler: DatabaseHandler? = null

    companion object {
        val CREATE = "create"
        val EDIT = "edit"
        val KEY = "key_data"
        val DATA = "data_student"
        fun getCreateIntent(context: Context): Intent {
            var intent = Intent(context, StudentActivity::class.java)
            intent.putExtra(KEY, CREATE)
            return intent
        }

        fun getEditIntent(context: Context, student: Student): Intent {
            var intent = Intent(context, StudentActivity::class.java)
            intent.putExtra(KEY, EDIT)
            intent.putExtra(DATA, student)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        DatabaseHandler = DatabaseHandler(this)
        initData()
        btnCancel.setOnClickListener {
            finish()
        }
        btnDone.setOnClickListener {
            processButtonDone()
        }
    }

    fun initData() {
        var intent = this.intent
        if (this.intent.getStringExtra(KEY) == EDIT) {
            btnDone.text = "UPDATE"
            mStudent = this.intent.getSerializableExtra(DATA) as Student
            edName.setText(mStudent!!.name)
            edAge.setText(mStudent!!.age.toString())
            if (mStudent!!.gender == 0) {
                rdBoy.isChecked = true
            } else {
                rdBoy.isChecked = false
            }
        } else {
            btnDone.text = "CREATE"
        }
    }

    fun processButtonDone() {
        if (mStudent != null) {
            mStudent?.name = edName.text.toString()
            mStudent?.age = edName.text.toString()
            if (rdBoy.isChecked) mStudent?.gender = 0
            else mStudent?.gender = 1
            DatabaseHandler?.updateStudent(mStudent!!)
        } else {
            var student = Student()
            student?.name = edName.text.toString()
            student?.age = edName.text.toString()
            if (rdBoy.isChecked) student?.gender = 0
            else student?.gender = 1
            DatabaseHandler?.addStudent(student!!)
        }
    }
}