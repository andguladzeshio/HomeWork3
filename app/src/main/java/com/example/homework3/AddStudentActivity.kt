package com.example.homework3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.homework3.databinding.ActivityAddStudentBinding
import com.example.homework3.model.FirebaseConstants
import com.example.homework3.model.Student
import com.google.firebase.database.FirebaseDatabase

class AddStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStudentBinding
    private val rootNode = FirebaseDatabase.getInstance().reference.child(FirebaseConstants.ROOT_ELEMENT).child(FirebaseConstants.STUDENTS_PATH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.addButton.setOnClickListener {
            val name = binding.nameEditText.text
            val surname = binding.surnameEditText.text
            val personalNumber = binding.privateNumberEditText.text
            val profilePictureLink = binding.profilePictureEditText.text
            val email = binding.emailEditText.text

            if (!name.isNullOrBlank() && !surname.isNullOrBlank() && !personalNumber.isNullOrBlank() && !profilePictureLink.isNullOrBlank() && !email.isNullOrBlank()) {
                if (!email.contains("@")) {
                    Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (personalNumber.length != 13) {
                    Toast.makeText(this, "Invalid personal number", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val student = Student(name.toString(), surname.toString(), personalNumber.toString(), profilePictureLink.toString(), email.toString())
                rootNode.child(student.personalNumber).setValue(student)
                finish()
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}