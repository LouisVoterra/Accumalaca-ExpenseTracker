package com.accumalaca.expensestracking.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.accumalaca.expensestracking.model.User
import com.accumalaca.expensestracking.model.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// yang ngatur login dan daftar, bukan doi
class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = UserDatabase.getInstance(application).userDao()

    // daftar akun
    fun registerUser(user: User, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val existing = withContext(Dispatchers.IO) {
                userDao.getUser(user.username)
            }

            if (existing != null) {
                onResult(false, "Username sudah ada. Jangan ngikut-ngikut!")
            } else {
                withContext(Dispatchers.IO) {
                    userDao.insertUser(user)
                }
                onResult(true, "Registrasi sukses, selamat bergabung wahai pejuang.")
            }
        }
    }

    // buat login
    fun loginUser(username: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                userDao.getUser(username)
            }

            if (user == null) {
                onResult(false, "Username nggak ketemu di dunia nyata maupun maya.")
            } else if (user.password != password) {
                onResult(false, "Password salah. Coba lagi, jangan menyerah.")
            } else {
                onResult(true, "Login berhasil. Selamat datang kembali di dunia ini.")
            }
        }
    }
}
