package com.accumalaca.expensestracking.model

import androidx.databinding.ObservableField

data class UserBinding(
    val username: ObservableField<String> = ObservableField(""),
    val oldPassword: ObservableField<String> = ObservableField(""),
    val newPassword: ObservableField<String> = ObservableField(""),
    val repeatPassword: ObservableField<String> = ObservableField("")
)