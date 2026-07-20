package com.ferhat.myaicoach.feature.auth.register

import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.LaunchedEffect

@Composable
fun RegisterScreen(
    onBackClick: () -> Unit,
    onLoginClick : () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isRegisterSuccess) {
        if (state.isRegisterSuccess) {
            onLoginClick()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .imePadding()
            .padding(
                horizontal = 24.dp,
                vertical = 32.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Geri dön"
                )
            }
        }
        Text(
            text = "Hesap Oluştur",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "İngilizce öğrenme yolculuğuna başla.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Ad Soyad")
            },
            singleLine = true,
            isError = state.nameError != null,
            supportingText = {
                state.nameError?.let {
                    Text(it)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("E-posta")
            },
            singleLine = true,
            isError = state.emailError != null,
            supportingText = {
                state.emailError?.let {
                    Text(it)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Şifre")
            },
            singleLine = true,
            isError = state.passwordError != null,
            supportingText = {
                state.passwordError?.let {
                    Text(it)
                }
            },
            visualTransformation = if (state.isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(
                    onClick = viewModel::onPasswordVisibilityChange
                ) {
                    Icon(
                        imageVector = if (state.isPasswordVisible) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        },
                        contentDescription = if (state.isPasswordVisible) {
                            "Şifreyi gizle"
                        } else {
                            "Şifreyi göster"
                        }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.confirmPassword,
            onValueChange = viewModel::onConfirmPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Şifre Tekrar")
            },
            singleLine = true,
            isError = state.confirmPasswordError != null,
            supportingText = {
                state.confirmPasswordError?.let {
                    Text(it)
                }
            },
            visualTransformation = if (state.isConfirmPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(
                    onClick = viewModel::onConfirmPasswordVisibilityChange
                ) {
                    Icon(
                        imageVector = if (state.isConfirmPasswordVisible) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        },
                        contentDescription = if (state.isConfirmPasswordVisible) {
                            "Şifre tekrarını gizle"
                        } else {
                            "Şifre tekrarını göster"
                        }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        state.generalError?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(
            onClick = viewModel::register,
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            Text(
                text = if (state.isLoading) {
                    "Kaydediliyor..."
                } else {
                    "Kayıt Ol"
                }
            )
        }
    }
}