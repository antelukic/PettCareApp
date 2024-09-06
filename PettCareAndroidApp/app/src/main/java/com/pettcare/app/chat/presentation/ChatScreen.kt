package com.pettcare.app.chat.presentation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pettcare.app.R
import com.pettcare.app.chat.domain.model.Message
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ChatScreen(
    userId: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<ChatViewModel>(parameters = { parametersOf(userId) })
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = true) {
        viewModel.toastEvent.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.connectToChat()
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.disconnect()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    ChatScreen(
        username = viewModel.userName.value,
        messageText = viewModel.messageText.value,
        onMessageChange = viewModel::onMessageChange,
        onSendMessage = viewModel::sendMessage,
        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        modifier = modifier.padding(dimensionResource(id = R.dimen.spacing_4)),
    )
}

@Composable
private fun ChatScreen(
    uiState: ChatUiState,
    username: String,
    messageText: String,
    onMessageChange: (String) -> Unit,
    onSendMessage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true,
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            items(uiState.messages) { message ->
                val isOwnMessage = message.username == username
                Box(
                    contentAlignment = if (isOwnMessage) {
                        Alignment.CenterEnd
                    } else {
                        Alignment.CenterStart
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Message(
                        message = message,
                        modifier = messageModifier(isOwnMessage),
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextField(
                value = messageText,
                onValueChange = onMessageChange,
                placeholder = {
                    Text(text = "Enter a message")
                },
                modifier = Modifier.weight(1f),
            )
            IconButton(onClick = onSendMessage) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                )
            }
        }
    }
}

@Composable
private fun Message(
    message: Message,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = message.username,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = message.text,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = message.formattedTime,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.End),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
fun messageModifier(isOwnMessage: Boolean) = Modifier.composed {
    width(dimensionResource(id = R.dimen.message_width))
    drawBehind {
        val cornerRadius = 10.dp.toPx()
        val triangleHeight = 20.dp.toPx()
        val triangleWidth = 25.dp.toPx()
        val trianglePath = Path().apply {
            if (isOwnMessage) {
                moveTo(size.width, size.height - cornerRadius)
                lineTo(size.width, size.height + triangleHeight)
                lineTo(size.width - triangleWidth, size.height - cornerRadius)
                close()
            } else {
                moveTo(0f, size.height - cornerRadius)
                lineTo(0f, size.height + triangleHeight)
                lineTo(triangleWidth, size.height - cornerRadius)
                close()
            }
        }
        drawPath(
            path = trianglePath,
            color = if (isOwnMessage) Color.Green else Color.DarkGray,
        )
    }
    background(
        color = if (isOwnMessage) {
            MaterialTheme.colorScheme.onPrimaryContainer
        } else {
            MaterialTheme.colorScheme.onSecondaryContainer
        },
        shape = RoundedCornerShape(10.dp),
    )
    padding(dimensionResource(id = R.dimen.spacing_2))
}
