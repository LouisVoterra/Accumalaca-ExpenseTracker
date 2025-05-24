package com.accumalaca.expensestracking.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.accumalaca.expensestracking.databinding.ActivityMainBinding
import com.accumalaca.expensestracking.util.SessionManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)
        val username = session.getLoggedInUser()

        if (username == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        binding.txtWelcome.text = "Halo, $username!"
        binding.btnLogout.setOnClickListener {
            session.clearSession()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
