package com.pettcare.app.auth.sharedcomponents

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.pettcare.app.uicomponents.PettCareInputField

@Composable
fun PasswordInput(
    passwordAccessibility: String,
    passwordPlaceholder: String,
    password: String,
    onUpdatePassword: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorText: String? = null,
    passwordLabel: String? = null,
) {
    var passwordShowing by remember {
        mutableStateOf(false)
    }

    PettCareInputField(
        value = password,
        placeHolderText = passwordPlaceholder,
        labelText = passwordLabel,
        onValueChange = onUpdatePassword,
        visualTransformation = if (passwordShowing) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordShowing = !passwordShowing }) {
                Icon(
                    imageVector = if (passwordShowing) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                    contentDescription = passwordAccessibility,
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = modifier,
        isError = isError,
        errorText = errorText,
    )
}
