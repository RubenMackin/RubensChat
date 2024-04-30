package com.rubenmackin.rubenschat.domain

import com.rubenmackin.rubenschat.data.network.FirebaseChatService
import com.rubenmackin.rubenschat.data.network.dto.MessageDto
import com.rubenmackin.rubenschat.data.network.dto.UserDto
import java.util.Calendar
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val firebaseChatService: FirebaseChatService) {

    //msg
    //hour
    //date
    //user --> UserName, admin:Boolean

    operator fun invoke(msg: String, nickname: String) {

        val calendar = Calendar.getInstance()
        val currentMsg = msg

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val userDto = UserDto(nickname, false)

        val messageDto = MessageDto(
            msg = msg,
            hour = "$hour:$min",
            date = "$day/$month/$year",
            user = userDto
        )

        firebaseChatService.sendMessageToFirebase(messageDto)
    }
}