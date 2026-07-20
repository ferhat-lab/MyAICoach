package com.ferhat.myaicoach.feature.auth.login

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        state = state,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onPasswordVisibilityChange = viewModel::onPasswordVisibilityChange,
        onLoginClick = {
            val isValid = viewModel.login()

            if (isValid) {
                onLoginSuccess()
            }
        },
        onRegisterClick = onRegisterClick,
        onForgotPasswordClick = onForgotPasswordClick
    )
}

@Composable
fun LoginScreen(
    state: LoginState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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
        Text(
            text = "My AI Coach",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Yapay zekâ destekli İngilizce koçun",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Tekrar hoş geldin",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Derslerine devam etmek için giriş yap.",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "E-posta")
            },
            singleLine = true,
            enabled = !state.isLoading,
            isError = state.emailError != null,
            supportingText = {
                state.emailError?.let { error ->
                    Text(text = error)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Şifre")
            },
            singleLine = true,
            enabled = !state.isLoading,
            isError = state.passwordError != null,
            visualTransformation = if (state.isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                TextButton(
                    onClick = onPasswordVisibilityChange,
                    enabled = !state.isLoading
                ) {
                    Text(
                        text = if (state.isPasswordVisible) {
                            "Gizle"
                        } else {
                            "Göster"
                        }
                    )
                }
            },
            supportingText = {
                state.passwordError?.let { error ->
                    Text(text = error)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onLoginClick()
                }
            )
        )

        TextButton(
            onClick = onForgotPasswordClick,
            modifier = Modifier.align(Alignment.End),
            enabled = !state.isLoading
        ) {
            Text(text = "Şifremi unuttum")
        }

        state.generalError?.let { error ->
            Text(
                text = error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp
                )
            } else {
                Text(text = "Giriş Yap")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Henüz hesabın yok mu?",
            style = MaterialTheme.typography.bodyMedium
        )

        TextButton(
            onClick = onRegisterClick,
            enabled = !state.isLoading
        ) {
            Text(text = "Yeni hesap oluştur")
        }
    }
}

