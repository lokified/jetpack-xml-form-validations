package com.loki.composevalidation.validations

class ValidateUsername {

    fun execute(username: String): ValidationResult {
        if (username.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Username cannot be blank"
            )
        }

        return ValidationResult(
            isSuccessful = true
        )
    }
}