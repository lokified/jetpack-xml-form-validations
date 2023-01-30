package com.loki.composevalidation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.loki.composevalidation.NavGraph
import com.loki.composevalidation.components.Input
import com.loki.composevalidation.navigateSafely
import com.loki.composevalidation.ui.theme.Teal
import com.loki.composevalidation.ui.theme.Teal200
import com.loki.composevalidation.ui.theme.Yellow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController
) {

    val viewModel = LoginViewModel()

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {

        viewModel.loginEvent.collectLatest {  event ->

            when(event) {

                is LoginViewModel.LoginEvent.Loading -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "loading ..."
                    )
                }

                is LoginViewModel.LoginEvent.Success -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )

                    navController.navigate(NavGraph.HomeScreen.route)
                }

                is LoginViewModel.LoginEvent.Error -> {

                }
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = Color.White)
        ) {

            Column {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Login",
                    color = Teal200,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 24.sp
                )

                FormSection(viewModel = viewModel)
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FormSection(
    viewModel: LoginViewModel
) {

    val loginState = viewModel.loginState.value
    val keyboardController = LocalSoftwareKeyboardController.current


    Box(modifier = Modifier.fillMaxWidth()) {

        Column(modifier = Modifier.padding(8.dp)) {

            Input(
                placeholder = "Username",
                label = "Username",
                value = loginState.username,
                onValueChange =  { viewModel.onEvent(LoginViewModel.LoginFormEvent.UsernameChanged(it.trim())) },
                errorMessage = loginState.usernameError,
                isError = !loginState.usernameError.isEmpty()
            )

            Input(
                placeholder = "Email",
                label = "Email",
                value = loginState.email,
                onValueChange = { viewModel.onEvent(LoginViewModel.LoginFormEvent.EmailChanged(it.trim())) },
                errorMessage = loginState.emailError,
                isError = !loginState.emailError.isEmpty()
            )

            Input(
                placeholder = "Password",
                label = "Password",
                value = loginState.password,
                onValueChange = { viewModel.onEvent(LoginViewModel.LoginFormEvent.PasswordChanged(it.trim())) },
                errorMessage = loginState.passwordError,
                isError = !loginState.passwordError.isEmpty(),
                keyboardType = KeyboardType.Password
            )

            Button(
                onClick = {
                    viewModel.onEvent(LoginViewModel.LoginFormEvent.Submit)
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Yellow
                )
            ) {

                Text(text = "Login", color = Teal)
            }
        }
    }
}