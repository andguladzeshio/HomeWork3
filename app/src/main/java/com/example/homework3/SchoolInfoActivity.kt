package com.example.homework3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.homework3.databinding.ActivitySchoolInfoBinding
import com.example.homework3.model.FirebaseConstants
import com.example.homework3.model.School
import com.example.homework3.model.Student
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SchoolInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySchoolInfoBinding
    private val rootNode = FirebaseDatabase.getInstance().reference.child(FirebaseConstants.ROOT_ELEMENT)
    private val adapter = StudentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.studentsRecyclerView.adapter = adapter
        observeSchool()
        setClickListeners()
    }

    private fun observeSchool() {
        rootNode.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child(FirebaseConstants.SCHOOL_NAME_KEY).getValue(String::class.java) ?: ""
                val students = snapshot.child(FirebaseConstants.STUDENTS_PATH).children
                    .map { it.getValue(Student::class.java)!! }.toTypedArray()

                setUpSchool(School(name, students))
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SchoolInfoActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setClickListeners() {
        binding.addStudentButton.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpSchool(school: School) {
        binding.schoolNameTextView.text = school.name
        adapter.submitStudentsList(school.students)
    }

}