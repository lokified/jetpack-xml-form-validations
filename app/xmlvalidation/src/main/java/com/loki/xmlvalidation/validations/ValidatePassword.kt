package com.loki.xmlvalidation.validations

class ValidatePassword {

    fun executePassword(password: String) : ValidationResult {
        if(password.isEmpty()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password cannot be blank"
            )
        }

        return ValidationResult(
            isSuccessful = true
        )
    }
}