package com.example.homework3

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework3.databinding.StudentItemBinding
import com.example.homework3.model.Student

@SuppressLint("NotifyDataSetChanged")
class StudentsAdapter: RecyclerView.Adapter<StudentsAdapter.ViewHolder>() {
    var students = arrayOf<Student>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            StudentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = students.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(students[position])
    }

    fun submitStudentsList(students: Array<Student>) {
        this.students = students
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: StudentItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun onBind(student: Student) {
            binding.studentFullNameTextView.text = "%s %s".format(student.name, student.surname)
            binding.studentEmailTextView.text = student.email
            binding.studentPrivateNumberTextView.text = student.personalNumber
            Glide.with(itemView.context).load(student.profilePictureLink).into(binding.studentImageView)
        }

    }
}