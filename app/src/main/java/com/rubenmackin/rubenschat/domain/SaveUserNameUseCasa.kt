package com.rubenmackin.rubenschat.domain

import javax.inject.Inject

class SaveUserNameUseCasa @Inject constructor(private val databaseService: DatabaseService) {
    suspend operator fun invoke(userName: String) {
        databaseService.saveUserName(userName)
    }
}