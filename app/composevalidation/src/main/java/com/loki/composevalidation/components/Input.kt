package com.loki.composevalidation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loki.composevalidation.ui.theme.Teal
import com.loki.composevalidation.ui.theme.Teal200

@Composable
fun Input(
    placeholder: String,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String,
    isError: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    Box(modifier = Modifier.padding(8.dp)) {

        Column {
            var passwordVisible by remember {
                mutableStateOf(false)
            }

            OutlinedTextField(
                modifier =Modifier.fillMaxWidth(),
                value = value,
                onValueChange = { onValueChange(it) },
                placeholder = {
                    Text(text = placeholder)
                },
                isError = isError,
                label = {
                    Text(
                        text = label,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedLabelColor = Color.DarkGray,
                    focusedIndicatorColor = Teal,
                    unfocusedIndicatorColor = Color.Black,
                    placeholderColor = Color.Gray,
                    unfocusedLabelColor = Color.Black,
                    cursorColor = Teal,
                    textColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType
                ),
                visualTransformation = if (!passwordVisible && keyboardType == KeyboardType.Password) PasswordVisualTransformation()
                else VisualTransformation.None,
                trailingIcon = {

                    if(keyboardType == KeyboardType.Password) {

                        val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null)
                        }
                    }
                }
            )
            if (isError) {
                Text(text = errorMessage, color = Color.Red, fontSize = 12.sp)
            }
        }

    }



}