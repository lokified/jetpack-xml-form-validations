package com.loki.composevalidation.login

data class LoginState(
    var username: String = "",
    var usernameError: String = "",
    var email: String = "",
    var emailError: String = "",
    var password: String = "",
    var passwordError: String = ""
)
