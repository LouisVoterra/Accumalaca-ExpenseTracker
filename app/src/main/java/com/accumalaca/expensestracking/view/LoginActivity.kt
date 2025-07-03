package com.accumalaca.expensestracking.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.accumalaca.expensestracking.databinding.ActivityLoginBinding
import com.accumalaca.expensestracking.util.SessionManager
import com.accumalaca.expensestracking.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)

        //session.getLoggedInUser()?.let {
            //goToMain()
        //}

        // kenapa ganti? cuma masttin SessionManager.getLoggedInUser() berfungsi
        val loggedIn = session.getLoggedInUser()
        if (loggedIn != null) {
            Toast.makeText(this, "Sudah login sebagai: $loggedIn", Toast.LENGTH_SHORT).show()
            goToMain()
        }

        binding.btnLogin.setOnClickListener {
            val uname = binding.txtUsername.text.toString()
            val pw = binding.txtPassword.text.toString()

            if (uname.isBlank() || pw.isBlank()) {
                Toast.makeText(this, "Isi dulu, jangan kosongan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.loginUser(uname, pw) { success, msg ->
                runOnUiThread {
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                    if (success) {
                        session.saveLoginSession(uname)
                        goToMain()
                    }
                }
            }
        }

        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
