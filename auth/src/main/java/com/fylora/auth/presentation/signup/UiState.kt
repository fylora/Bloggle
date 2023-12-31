package com.fylora.auth.presentation.signup

sealed interface UiState {
    data class UsernameTextFieldError(val message: String): UiState
    data class PasswordTextFieldError(val message: String): UiState
    data class ConfirmPasswordTextFieldError(val message: String): UiState
}