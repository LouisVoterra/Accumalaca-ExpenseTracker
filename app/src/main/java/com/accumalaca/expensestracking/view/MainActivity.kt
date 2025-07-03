package com.accumalaca.expensestracking.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.accumalaca.expensestracking.databinding.ActivityMainBinding
import com.accumalaca.expensestracking.util.SessionManager
import com.accumalaca.expensestracking.R
import kotlinx.coroutines.launch

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

        //Tampilkan nama pengguna saat login
        username?.let {
            // ambil user dari database
            kotlinx.coroutines.GlobalScope.launch(kotlinx.coroutines.Dispatchers.IO) {
                val user = com.accumalaca.expensestracking.model.UserDatabase
                    .getInstance(this@MainActivity)
                    .userDao()
                    .getUser(it)

                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                    android.widget.Toast.makeText(
                        this@MainActivity,
                        "Selamat datang, ${user?.firstName ?: it}",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        if (username == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }


        //avController = (supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment).navController

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        navController = navHostFragment.navController


        val appBarConfiguration = androidx.navigation.ui.AppBarConfiguration(
            setOf(
                R.id.itemExpense,
                R.id.itemBudget,
                R.id.itemReport,
                R.id.itemProfile
            )
        )

        binding.bottomNav.setupWithNavController(navController)





        //binding.bottomNav.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }
}
