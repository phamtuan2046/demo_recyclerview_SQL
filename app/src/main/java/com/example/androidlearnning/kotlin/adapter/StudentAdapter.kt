package com.example.androidlearnning.kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidlearnning.demo.R
import com.example.androidlearnning.kotlin.models.Student
import kotlinx.android.synthetic.main.item_student.view.*

/**
 * Created by FRAMGIA\pham.dinh.tuan on 02/03/2018.
 */
class StudentAdapter(context: Context, var students: ArrayList<Student>, var onClickItem: (index: Int) -> Unit, var ondelete: (index: Int) -> Unit) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    var infalter: LayoutInflater? = null

    init {
        infalter = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: StudentViewHolder?, position: Int) {
        holder?.bindata(students[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StudentViewHolder {
        return StudentViewHolder(infalter!!.inflate(R.layout.item_student, parent, false), onClickItem,ondelete)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    fun onItemClick(index: Int) {

    }

    class StudentViewHolder(var itemview: View, onClickItem: (index: Int) -> Unit, onDelete: (index: Int) -> Unit) : RecyclerView.ViewHolder(itemview) {
        init {
            itemview.setOnClickListener {
                onClickItem.invoke(adapterPosition)
            }
            itemview.btnDelete.setOnClickListener {
                onDelete.invoke(adapterPosition)
            }
        }

        fun bindata(student: Student) {
            if (student.gender == 0) {
                itemview.imgGender.setImageResource(R.drawable.boy)
            } else {
                itemview.imgGender.setImageResource(R.drawable.girl)
            }
            if (student.age != null)
                itemview.tvName.text = student.name
            if (student.age != null)
                itemview.tvAge.text = student.age!!
        }
    }
}