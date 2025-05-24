package com.accumalaca.expensestracking.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE username = :uname")
    suspend fun getUser(uname: String): User?
}
