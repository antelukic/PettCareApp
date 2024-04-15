package com.pettcare.app.uicomponents

import androidx.compose.animation.Crossfade
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle

@Composable
fun PettCareProceedButton(
    text: String,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ),
    shape: Shape = MaterialTheme.shapes.medium,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    isLoading: Boolean = false,
) {
    Button(
        onClick = onClick,
        colors = colors,
        enabled = isEnabled && !isLoading,
        shape = shape,
        modifier = modifier,
    ) {
        Crossfade(targetState = isLoading, label = "button_crossfade") {
            if (it) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = text,
                    style = textStyle,
                )
            }
        }
    }
}
