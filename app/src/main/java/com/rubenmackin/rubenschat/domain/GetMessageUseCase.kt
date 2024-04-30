package com.rubenmackin.rubenschat.domain

import com.rubenmackin.rubenschat.data.network.FirebaseChatService
import javax.inject.Inject

class GetMessageUseCase @Inject constructor(private val firebaseChatService: FirebaseChatService) {
    operator fun invoke() = firebaseChatService.getMessage()
}