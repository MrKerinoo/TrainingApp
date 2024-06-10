package com.example.trainingapp.ui.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.data.TrainingsRepository
import com.example.trainingapp.data.entities.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserViewModel(
    savedStateHandle: SavedStateHandle,
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    var userUiState by mutableStateOf(UserUiState())
        private set

    private val _darkMode = MutableStateFlow(userUiState.userDetails.darkMode)
    val darkMode = _darkMode.asStateFlow()

    init {
        viewModelScope.launch {
            val user = trainingsRepository.getUserStream()
                .filterNotNull()
                .first()
                .toUserUiState()
            userUiState = user
            _darkMode.value = user.userDetails.darkMode

            Log.d("UserViewModel", "Initial Dark Mode: ${_darkMode.value}")
        }
    }

    fun userExists(): Boolean {
        return userUiState.userDetails.id != 0
    }

    suspend fun saveUser() {
        if (validateInput()) {
            trainingsRepository.insertUser(userUiState.userDetails.toUser())
        }
    }

    fun updateUiState(userDetails: UserDetails) {
        userUiState = UserUiState(userDetails = userDetails)
        _darkMode.value = userDetails.darkMode
        Log.d("UserViewModel", "Updated Dark Mode: ${_darkMode.value}")
    }

    suspend fun updateUser() {
        if (validateInput()) {
            trainingsRepository.updateUser(userUiState.userDetails.toUser())
            _darkMode.value = userUiState.userDetails.darkMode
            Log.d("UserViewModel", "Updated User Dark Mode: ${_darkMode.value}")
        }
    }

    private fun validateInput(uiState: UserDetails = userUiState.userDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && age.isNotBlank() && age.toInt() > 0 && weight.isNotBlank() && weight.toFloat() > 0f && height.isNotBlank() && height.toFloat() > 0f
        }
    }
}

data class UserUiState(
    val userDetails: UserDetails = UserDetails()
)

data class UserDetails(
    val id: Int = 0,
    val name: String = "",
    val age: String = "",
    val weight: String = "",
    val height: String = "",
    val lang: String = "",
    val darkMode: Boolean = true,
)

fun UserDetails.toUser(): User = User(
    id = id,
    name = name,
    age = age.toInt(),
    weight = weight.toFloat(),
    height = height.toFloat(),
    lang = lang,
    darkMode = darkMode
)

fun User.toUserDetails(): UserDetails = UserDetails(
    id = id,
    name = name,
    age = age.toString(),
    weight = weight.toString(),
    height = height.toString(),
    lang = lang,
    darkMode = darkMode
)

fun User.toUserUiState(): UserUiState = UserUiState(
    userDetails = toUserDetails()
)

