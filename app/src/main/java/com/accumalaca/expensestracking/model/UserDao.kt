package com.accumalaca.expensestracking.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE username = :uname")
    suspend fun getUser(uname: String): User?

    @Query("UPDATE user SET password=:password WHERE username = :username")
    suspend fun updateUser(username: String,password: String): Int


}
