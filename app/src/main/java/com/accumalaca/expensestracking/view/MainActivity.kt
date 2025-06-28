package com.accumalaca.expensestracking.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.accumalaca.expensestracking.databinding.ActivityMainBinding
import com.accumalaca.expensestracking.util.SessionManager
import com.accumalaca.expensestracking.R

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var session: SessionManager
    private lateinit var navController: NavController

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



        navController = (supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment).navController

        binding.bottomNav.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }
}
