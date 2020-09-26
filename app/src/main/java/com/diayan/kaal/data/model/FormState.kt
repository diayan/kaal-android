package com.diayan.kaal.data.model

data class FormState (
    val nameError: Int? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val addressError: Int? = null,
    val phoneNumberError: Int? = null,
    val isDataValid: Boolean = false
)