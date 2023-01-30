package com.loki.xmlvalidation.validations

data class ValidationResult(
    val errorMessage: String = "",
    val isSuccessful: Boolean = false
)
