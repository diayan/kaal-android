package com.diayan.kaal.util

import androidx.core.util.PatternsCompat

object ValidationUtil {
    fun isEmailValid(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return hasAtLeastFiveCharacters(password)
    }

    private fun containsAndStartsWithAnAlphabet(password: String): Boolean {
        return password.toLowerCase().matches("[a-z]+.*".toRegex())
    }

    private fun containsAtLeastOneUpperCaseLetter(password: String): Boolean {
        return password.matches(".*[A-Z]+.*".toRegex())
    }

    private fun isBetweenEightToTwentyChars(password: String): Boolean {
        return password.length in 8..20
    }

    private fun hasAtLeastFiveCharacters(password: String): Boolean {
        return password.length > 5
    }

    private fun containsAtLeastOneSpecialChar(password: String): Boolean {
        return password.matches(".*[.!#@&*()?]+.*".toRegex())
    }

    private fun containsAtLeastOneDigit(password: String): Boolean {
        return password.matches(".*\\d+.*".toRegex())
    }

}