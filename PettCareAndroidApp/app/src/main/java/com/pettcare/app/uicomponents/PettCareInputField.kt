package com.pettcare.app.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import com.pettcare.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PettCareInputField(
    value: String,
    placeHolderText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String? = placeHolderText,
    trailingIcon: @Composable (() -> Unit)? = null,
    labelStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    placeholderStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        cursorColor = MaterialTheme.colorScheme.primary,
        errorLabelColor = MaterialTheme.colorScheme.error,
        errorLeadingIconColor = MaterialTheme.colorScheme.error,
        errorCursorColor = MaterialTheme.colorScheme.error,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
    ),
    maxLines: Int = 1,
    minLines: Int = 1,
    shape: Shape = MaterialTheme.shapes.medium,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    isError: Boolean = false,
    errorText: String? = null,
    onFocusChanged: (FocusState) -> Unit = {},
    readOnly: Boolean = false,
) {
    Column(
        modifier = modifier,
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            maxLines = maxLines,
            minLines = minLines,
            visualTransformation = visualTransformation,
            colors = colors,
            trailingIcon = trailingIcon,
            shape = shape,
            keyboardOptions = keyboardOptions,
            readOnly = readOnly,
            label = {
                labelText?.let {
                    Text(
                        text = labelText,
                        style = labelStyle,
                    )
                }
            },
            placeholder = {
                Text(
                    text = placeHolderText,
                    style = placeholderStyle,
                )
            },
            isError = isError,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged(onFocusChanged),
        )
        if (errorText.isNullOrBlank().not()) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_1)))
            Text(
                text = errorText.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}
