package com.loki.composevalidation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.composevalidation.validations.ValidateEmail
import com.loki.composevalidation.validations.ValidatePassword
import com.loki.composevalidation.validations.ValidateUsername
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    //login event after submitting the form
    private val _loginEvent = MutableSharedFlow<LoginEvent>()
    val loginEvent = _loginEvent.asSharedFlow()

    //state of the forms
    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    //validate functions
    private val validateUsername: ValidateUsername = ValidateUsername()
    private val validateEmail: ValidateEmail = ValidateEmail()
    private val validatePassword: ValidatePassword = ValidatePassword()


    //event to be used inside the login fragment
    fun onEvent(event: LoginFormEvent) {

        when(event) {

            is LoginFormEvent.UsernameChanged -> {

                _loginState.value = _loginState.value.copy(
                    username = event.username
                )
            }

            is LoginFormEvent.EmailChanged -> {

                _loginState.value = _loginState.value.copy(
                    email = event.email
                )
            }

            is LoginFormEvent.PasswordChanged -> {

                _loginState.value = _loginState.value.copy(
                    password = event.password
                )
            }

            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {

        val usernameResult = validateUsername.execute(_loginState.value.username)
        val emailResult = validateEmail.execute(_loginState.value.email)
        val passwordResult = validatePassword.executePassword(_loginState.value.password)

        val hasError = listOf(
            usernameResult,
            emailResult,
            passwordResult
        ).any { !it.isSuccessful }

        if (hasError) {
            _loginState.value = _loginState.value.copy(
                usernameError = usernameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )

            return
        }

        _loginState.value = _loginState.value.copy(
            usernameError = "",
            emailError = "",
            passwordError = ""
        )


        viewModelScope.launch {

            _loginEvent.emit(LoginEvent.Loading)

            delay(3000L)

            _loginEvent.emit(LoginEvent.Success("Login Successful"))
        }
    }


    sealed class LoginFormEvent {
        data class UsernameChanged(val username: String): LoginFormEvent()
        data class EmailChanged(val email: String): LoginFormEvent()
        data class PasswordChanged(val password: String): LoginFormEvent()
        object Submit: LoginFormEvent()
    }

    sealed class LoginEvent {
        object Loading: LoginEvent()
        data class Error(val errorMessage: String): LoginEvent()
        data class Success(val message: String): LoginEvent()
    }
}