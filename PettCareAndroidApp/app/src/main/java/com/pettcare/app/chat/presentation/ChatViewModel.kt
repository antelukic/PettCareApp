package com.pettcare.app.chat.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.pettcare.app.chat.domain.model.Message
import com.pettcare.app.chat.domain.usecase.CloseSession
import com.pettcare.app.chat.domain.usecase.GetAllChatMessages
import com.pettcare.app.chat.domain.usecase.GetAllMessages
import com.pettcare.app.chat.domain.usecase.GetUserInfo
import com.pettcare.app.chat.domain.usecase.InitSession
import com.pettcare.app.chat.domain.usecase.SendMessage
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.navigation.Router
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import okhttp3.internal.toImmutableList

class ChatViewModel(
    router: Router,
    private val userId: String,
    private val getAllMessages: GetAllMessages,
    private val sendMessage: SendMessage,
    private val closeSession: CloseSession,
    private val initSession: InitSession,
    private val getAllChatMessages: GetAllChatMessages,
    private val getUserInfo: GetUserInfo,
) : BaseViewModel<ChatUiState>(router, ChatUiState()) {

    private val _userName = mutableStateOf("")
    val userName: State<String> = _userName

    private val _messageText = mutableStateOf("")
    val messageText: State<String> = _messageText

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    fun connectToChat() {
        launchInIO {
            getUserInfo.invoke()
        }
        launchInIO {
            getUserInfo.results.collectLatest { profileData ->
                if (profileData is BaseResponse.Success) {
                    _userName.value = profileData.data.name
                }
            }
        }
        launchInIO {
            getAllChatMessages(userId)
        }
        launchInIO {
            getAllChatMessages.results.collectLatest(::handleAllMessages)
        }
        getInitialChatMessages()
        launchInIO {
            val result = initSession(userId)
            when (result) {
                is BaseResponse.Success -> {
                    launchInMain {
                        sendMessage.messages()
                            .collectLatest { message ->
                                if (message is BaseResponse.Success) {
                                    val newList = uiState.value.messages.toMutableList().apply {
                                        add(0, message.data)
                                    }
                                    updateUiState { state ->
                                        state.copy(
                                            messages = newList,
                                        )
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

    private fun getInitialChatMessages() {
        launchInIO {
            updateUiState { state ->
                state.copy(isLoading = true)
            }
            getAllMessages()
                .collectLatest { response ->
                    if (response is BaseResponse.Success) {
                        updateUiState { state ->
                            val messages = state.messages.toMutableList()
                            messages.add(0, response.data)
                            state.copy(
                                messages = messages.toImmutableList(),
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
                _messageText.value = ""
            }
        }
    }

    private suspend fun handleAllMessages(messages: BaseResponse<List<Message>>) {
        if (messages is BaseResponse.Success) {
            val newList = uiState.value.messages.toMutableList().apply {
                addAll(0, messages.data)
            }
            updateUiState { state ->
                state.copy(messages = newList)
            }
        }
        if (messages is BaseResponse.Error) {
            _toastEvent.emit("An error occured with our service. We apologize! Please try again later!")
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}
