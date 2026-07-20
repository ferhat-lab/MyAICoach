package com.ferhat.myaicoach.feature.auth.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                emailError = null,
                generalError = null
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                passwordError = null,
                generalError = null
            )
        }
    }

    fun onPasswordVisibilityChange() {
        _uiState.update {
            it.copy(
                isPasswordVisible = !it.isPasswordVisible
            )
        }
    }

    fun login(): Boolean {
        val currentState = _uiState.value

        val emailError = when {
            currentState.email.isBlank() -> "E-posta adresi boş bırakılamaz."
            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(currentState.email)
                .matches() -> "Geçerli bir e-posta adresi girin."
            else -> null
        }

        val passwordError = when {
            currentState.password.isBlank() -> "Şifre boş bırakılamaz."
            currentState.password.length < 6 -> "Şifre en az 6 karakter olmalıdır."
            else -> null
        }

        _uiState.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        return emailError == null && passwordError == null
    }
}