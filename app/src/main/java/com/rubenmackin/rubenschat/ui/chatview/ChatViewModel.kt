package com.rubenmackin.rubenschat.ui.chatview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubenmackin.rubenschat.domain.GetMessageUseCase
import com.rubenmackin.rubenschat.domain.GetUserNameUseCase
import com.rubenmackin.rubenschat.domain.LogoutUseCase
import com.rubenmackin.rubenschat.domain.SendMessageUseCase
import com.rubenmackin.rubenschat.domain.model.MessageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessageUseCase: GetMessageUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    var name: String = ""

    init {
        getUserName()
        getMessages()
    }

    private fun getUserName() {
        viewModelScope.launch(Dispatchers.IO) {
            name = getUserNameUseCase()
        }
    }

    private var _messageList = MutableStateFlow<List<MessageModel>>(emptyList())
    val messageList: StateFlow<List<MessageModel>> = _messageList

    fun getMessages() {
        viewModelScope.launch {
            val result = getMessageUseCase()
            result.collect { listaMensajesFirebase ->
                _messageList.value = listaMensajesFirebase
            }
        }
    }

    fun sendMessage(msg: String) {
        sendMessageUseCase(msg, name)
    }

    fun logout(onViewFinish: () -> Unit) {
        viewModelScope.launch {
            async { logoutUseCase() }.await()
            onViewFinish()
        }
    }
}