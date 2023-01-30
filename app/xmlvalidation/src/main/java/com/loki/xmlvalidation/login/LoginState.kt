package com.loki.xmlvalidation.login

data class LoginState(
    var username: String = "",
    var usernameError: String? = null,
    var email: String = "",
    var emailError: String? = null,
    var password: String = "",
    var passwordError: String? = null
)
