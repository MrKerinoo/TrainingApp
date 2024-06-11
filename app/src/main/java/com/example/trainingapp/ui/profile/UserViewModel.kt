package com.example.trainingapp.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.data.TrainingsRepository
import com.example.trainingapp.data.entities.User
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * UserViewModel is the ViewModel that provides data for the User screen.
 * It uses TrainingsRepository to get the user details.
 * Validates the input data before saving the user details.
 * Updates UiState and saves the user details.
 * Transforms UserDetails to User.
 */
class UserViewModel(
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    var userUiState by mutableStateOf(UserUiState())
        private set


    init {
        viewModelScope.launch {
            val user = trainingsRepository.getUserStream()
                .filterNotNull()
                .first()
                .toUserUiState(true, true ,true)
            userUiState = user
        }
    }

    suspend fun saveUser() {
        if (validateAge() && validateWeight() && validateHeight()
            ) {
            trainingsRepository.insertUser(userUiState.userDetails.toUser())
        }
    }

    fun updateUiState(userDetails: UserDetails) {
        userUiState = UserUiState(
            userDetails = userDetails,
            isAgeValid = validateAge(userDetails),
            isWeightValid = validateWeight(userDetails),
            isHeightValid = validateHeight(userDetails)
        )
    }

    suspend fun updateUser() {
        if (validateAge()) {
            trainingsRepository.updateUser(userUiState.userDetails.toUser())
        }
    }

    private fun validateAge(uiState: UserDetails = userUiState.userDetails): Boolean {
        return with(uiState) {
            age.isDigitsOnly()
        }
    }

    private fun validateWeight(uiState: UserDetails = userUiState.userDetails): Boolean {
        return with(uiState) {
            weight.isDigitsOnly()
        }
    }

    private fun validateHeight(uiState: UserDetails = userUiState.userDetails): Boolean {
        return with(uiState) {
            height.isDigitsOnly()
        }
    }


}

data class UserUiState(
    val userDetails: UserDetails = UserDetails(),
    val isAgeValid: Boolean = true,
    val isWeightValid: Boolean = true,
    val isHeightValid: Boolean = true
)

data class UserDetails(
    val id: Int = 0,
    val name: String = "",
    val age: String = "0",
    val weight: String = "0",
    val height: String = "0",
    val lang: Int = 1,
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

fun User.toUserUiState(isAgeValid: Boolean = true, isWeightValid: Boolean = true , isHeightValid: Boolean = true): UserUiState = UserUiState(
    userDetails = this.toUserDetails(),
    isAgeValid = isAgeValid,
    isWeightValid = isWeightValid,
    isHeightValid = isHeightValid
)

