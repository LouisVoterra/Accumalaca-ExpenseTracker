package com.accumalaca.expensestracking.view

import android.content.Intent
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

            //update validasi 6 huruf
            if (pw.length < 6) {
                Toast.makeText(this, "Password minimal 6 karakter, jangan pelit huruf", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(uname, fname, lname, pw)

            viewModel.registerUser(user) { success, msg ->
                runOnUiThread {
                    android.util.Log.d("RegisterActivity", "Hasil registrasi: success=$success, pesan=$msg")
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                    if (success) finish() //balik login
                }
            }
        }

        binding.txtLogin.setOnClickListener {
            finish()
        }
    }
}
