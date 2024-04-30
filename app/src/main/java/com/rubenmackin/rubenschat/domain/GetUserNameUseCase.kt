package com.rubenmackin.rubenschat.domain

import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetUserNameUseCase @Inject constructor(private val databaseService: DatabaseService) {
    suspend operator fun invoke(): String = databaseService.getUserName().first()
}