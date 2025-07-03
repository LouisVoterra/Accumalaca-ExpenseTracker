package com.accumalaca.expensestracking.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class Expense(
    @ColumnInfo(name = "expenseName")
    var expenseName: String,

    @ColumnInfo(name = "amount")
    var amount: Int,

    @ColumnInfo(name = "category")
    var category: String, // ini adalah nama budget yang dipilih dari spinner

    @ColumnInfo(name = "date")
    var date: Long, // ngubah INI DARI String ke Long

    @ColumnInfo(name = "username")
    var username: String // agar data terikat ke akun
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
