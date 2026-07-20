package com.ferhat.myaicoach.feature.auth.register

data class RegisterState(

    val name: String = "",

    val email: String = "",

    val password: String = "",

    val confirmPassword: String = "",

    val isPasswordVisible: Boolean = false,

    val isConfirmPasswordVisible: Boolean = false,

    val isLoading: Boolean = false,

    val nameError: String? = null,

    val emailError: String? = null,

    val passwordError: String? = null,

    val confirmPasswordError: String? = null,

    val generalError: String? = null,

    val isRegisterSuccess: Boolean = false

)