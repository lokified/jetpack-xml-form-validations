package com.loki.composevalidation.validations

data class ValidationResult(
    val errorMessage: String = "",
    val isSuccessful: Boolean = false
)
