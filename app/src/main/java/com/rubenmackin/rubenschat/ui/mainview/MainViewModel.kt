package com.rubenmackin.rubenschat.ui.mainview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubenmackin.rubenschat.domain.GetUserNameUseCase
import com.rubenmackin.rubenschat.domain.SaveUserNameUseCasa
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val saveUserName: SaveUserNameUseCasa,
    private val getUserNameUseCase: GetUserNameUseCase
) : ViewModel() {

    init {
        verifyUserLogged()
    }

    private var _uiState = MutableStateFlow<MainViewState>(MainViewState.LOADING)
    val uiState: StateFlow<MainViewState> = _uiState

    private fun verifyUserLogged() {
        viewModelScope.launch {
            val name = async { getUserNameUseCase() }.await()
            if (name.isNotEmpty()) {
                _uiState.value = MainViewState.REGISTERED
            } else {
                _uiState.value = MainViewState.UNREGISTERED
            }
        }
    }

    fun saveNickName(nickName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            saveUserName(nickName)
        }

    }
}

sealed class MainViewState {
    object UNREGISTERED : MainViewState()
    object REGISTERED : MainViewState()
    object LOADING : MainViewState()
}