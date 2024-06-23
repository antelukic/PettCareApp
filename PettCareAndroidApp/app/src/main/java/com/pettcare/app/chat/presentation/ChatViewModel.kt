package com.pettcare.app.chat.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.pettcare.app.chat.domain.usecase.CloseSession
import com.pettcare.app.chat.domain.usecase.GetAllMessages
import com.pettcare.app.chat.domain.usecase.InitSession
import com.pettcare.app.chat.domain.usecase.SendMessage
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.navigation.Router
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach

class ChatViewModel(
    router: Router,
    private val getAllMessages: GetAllMessages,
    private val sendMessage: SendMessage,
    private val closeSession: CloseSession,
    private val initSession: InitSession,
) : BaseViewModel<ChatUiState>(router, ChatUiState()) {

    val username = "test"

    private val _messageText = mutableStateOf("")
    val messageText: State<String> = _messageText

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    fun connectToChat() {
        getAllChatMessages()
        launchInIO {
            val result = initSession(username)
            when (result) {
                is BaseResponse.Success -> {
                    launchInMain {
                        sendMessage.messages()
                            .onEach { message ->
                                if (message is BaseResponse.Success) {
                                    val newList = uiState.value.messages.toMutableList().apply {
                                        add(0, message.data)
                                    }
                                    updateUiState { state ->
                                        state.copy(messages = newList)
                                    }
                                }
                            }
                    }
                }

                is BaseResponse.Error -> {
                    _toastEvent.emit("An error occured with our service. We apologize! Please try again later!")
                }

                else -> {}
            }
        }
    }

    fun onMessageChange(message: String) {
        _messageText.value = message
    }

    fun disconnect() {
        launchInIO {
            closeSession()
        }
    }

    fun getAllChatMessages() {
        launchInIO {
            updateUiState { state ->
                state.copy(isLoading = true)
            }
            getAllMessages()
                .collectLatest { response ->
                    if (response is BaseResponse.Success) {
                        updateUiState { state ->
                            state.copy(
                                messages = response.data,
                                isLoading = false,
                            )
                        }
                    }
                }
        }
    }

    fun sendMessage() {
        launchInIO {
            if (messageText.value.isNotBlank()) {
                sendMessage(messageText.value)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}
