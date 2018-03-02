package com.example.androidlearnning.kotlin.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.androidlearnning.demo.R
import com.example.androidlearnning.kotlin.adapter.StudentAdapter
import com.example.androidlearnning.kotlin.database.DatabaseHandler
import com.example.androidlearnning.kotlin.models.Student
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var databaseHandler: DatabaseHandler? = null
    var studentAdapter: StudentAdapter? = null
    var students = ArrayList<Student>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseHandler = DatabaseHandler(this)
        initView()
        getAllStudent()
        fabAdd.setOnClickListener {
            startActivityForResult(StudentActivity.getCreateIntent(this),100)
        }
    }

    fun initView() {
        studentAdapter = StudentAdapter(this, students)
        rcvStudent.adapter = studentAdapter
    }

    fun getAllStudent() {
        var data = databaseHandler?.getAllStudent()
        if (data != null && data.isNotEmpty()) {
            students.clear()
            students.addAll(data)
            studentAdapter?.notifyDataSetChanged()
        }
    }
    fun edit(student:Student){

    }
}
