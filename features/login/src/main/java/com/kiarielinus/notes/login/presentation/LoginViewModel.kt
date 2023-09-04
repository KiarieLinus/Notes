package com.kiarielinus.notes.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiarielinus.notes.auth_impl.api.AuthRepository
import com.kiarielinus.notes.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {
    val uiState = LoginState().apply {
        login = {onLogin ->
            if (isValidInput()) {
                viewModelScope.launch {
                    val result = if (isLogin) {
                        repository.login(email, password)
                    } else repository.register(email, username, password)

                    when (result) {
                        is Resource.Error -> loginError = result.message
                        is Resource.Success -> onLogin()
                    }
                }
            }
            isLoading = false
        }
        googleLogin = {credential, onLogin ->
            viewModelScope.launch {
                when (val result = repository.signInWithCredential(credential)) {
                    is Resource.Error -> loginError = result.message
                    is Resource.Success -> onLogin()
                }
                isGoogleLoading = false
            }
        }
    }


    private fun isValidInput(): Boolean {
        val errorText = "This field is required"

        uiState.apply {
            emailError = if (email.isBlank()) {
                errorText
            } else null
            usernameError = if (isLogin.not() && username.isBlank()) {
                errorText
            } else null
            passwordError = if (password.isBlank()) {
                errorText
            } else null
        }

        return uiState.emailError == null && uiState.usernameError == null && uiState.passwordError == null
    }
}