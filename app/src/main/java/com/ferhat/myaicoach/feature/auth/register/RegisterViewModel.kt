package com.ferhat.myaicoach.feature.auth.register

import com.ferhat.myaicoach.data.auth.AuthRepository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.update {
            it.copy(
                name = name,
                nameError = null,
                generalError = null
            )
        }
    }

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

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update {
            it.copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = null,
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

    fun onConfirmPasswordVisibilityChange() {
        _uiState.update {
            it.copy(
                isConfirmPasswordVisible = !it.isConfirmPasswordVisible
            )
        }
    }

    fun register() {

        val state = uiState.value

        if (state.name.isBlank()) {
            _uiState.update {
                it.copy(nameError = "Ad Soyad boş olamaz")
            }
            return
        }

        if (!state.email.contains("@")) {
            _uiState.update {
                it.copy(emailError = "Geçerli e-posta giriniz")
            }
            return
        }

        if (state.password.length < 8) {
            _uiState.update {
                it.copy(passwordError = "Şifre en az 8 karakter olmalı")
            }
            return
        }

        if (state.password != state.confirmPassword) {
            _uiState.update {
                it.copy(confirmPasswordError = "Şifreler eşleşmiyor")
            }
            return
        }

        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            try {

                authRepository.register(
                    email = state.email,
                    password = state.password
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRegisterSuccess = true
                    )
                }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        generalError = e.localizedMessage ?: "Kayıt işlemi başarısız."
                    )
                }

            }
        }
    }
}