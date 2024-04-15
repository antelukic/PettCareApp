package com.pettcare.app.auth.sharedcomponents

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.pettcare.app.uicomponents.PettCareInputField

@Composable
fun EmailInput(
    emailPlaceholder: String,
    email: String,
    onUpdateEmail: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorText: String? = null,
    emailLabel: String? = null,
) {
    PettCareInputField(
        value = email,
        placeHolderText = emailPlaceholder,
        labelText = emailLabel,
        onValueChange = onUpdateEmail,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = modifier,
        isError = isError,
        errorText = errorText,
    )
}
