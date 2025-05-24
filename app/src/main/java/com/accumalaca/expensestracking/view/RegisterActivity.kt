package com.accumalaca.expensestracking.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.accumalaca.expensestracking.databinding.ActivityRegisterBinding
import com.accumalaca.expensestracking.model.User
import com.accumalaca.expensestracking.viewmodel.AuthViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val uname = binding.txtUsername.text.toString()
            val fname = binding.txtFirstName.text.toString()
            val lname = binding.txtLastName.text.toString()
            val pw = binding.txtPassword.text.toString()
            val repw = binding.txtRepeatPassword.text.toString()

            if (uname.isBlank() || fname.isBlank() || lname.isBlank() || pw.isBlank() || repw.isBlank()) {
                Toast.makeText(this, "Ada field kosong, jangan males isi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pw != repw) {
                Toast.makeText(this, "Password gak cocok, kayak mantan kamu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(uname, fname, lname, pw)

            viewModel.registerUser(user) { success, msg ->
                runOnUiThread {
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                    if (success) finish() // balikin ke Login
                }
            }
        }
    }
}
