package com.ferhat.myaicoach.feature.auth.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.ferhat.myaicoach.data.auth.AuthRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

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

    fun login() {

        val state = uiState.value

        if (state.email.isBlank()) {
            _uiState.update {
                it.copy(emailError = "E-posta boş olamaz")
            }
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            _uiState.update {
                it.copy(emailError = "Geçerli bir e-posta giriniz")
            }
            return
        }

        if (state.password.isBlank()) {
            _uiState.update {
                it.copy(passwordError = "Şifre boş olamaz")
            }
            return
        }

        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {

            try {

                authRepository.login(
                    email = state.email,
                    password = state.password
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoginSuccess = true
                    )
                }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        generalError = e.localizedMessage
                            ?: "Giriş yapılamadı."
                    )
                }

            }

        }
    }
}