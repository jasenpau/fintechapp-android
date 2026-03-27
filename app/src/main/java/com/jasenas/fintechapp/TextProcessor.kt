package com.jasenas.fintechapp

import android.content.Context
import androidx.annotation.StringRes

class TextProcessor {

    data class ValidationResult(
        val isValid: Boolean,
        @StringRes val errorMessageRes: Int? = null
    )

    fun validateTextInput(text: String): ValidationResult {
        return if (text.isBlank()) {
            ValidationResult(false, R.string.error_empty_input)
        } else {
            ValidationResult(true)
        }
    }

    fun countWords(text: String): Int {
        if (text.isBlank()) return 0
        return text.trim().split(Regex("\\s+")).count { word ->
            word.any { it.isLetter() }
        }
    }

    fun countSeparators(text: String): Int {
        val separators = ",.?:;-!"
        return text.count { it in separators }
    }
}
