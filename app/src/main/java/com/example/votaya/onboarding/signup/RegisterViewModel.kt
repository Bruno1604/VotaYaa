package com.example.votaya.onboarding.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.votaya.core.AuthRepository
import com.example.votaya.core.ResponseService
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {
    private val authRepository = AuthRepository()

    private val _registerState = MutableStateFlow<ResponseService<FirebaseUser>?>(null)
    val registerState: StateFlow<ResponseService<FirebaseUser>?> = _registerState.asStateFlow()


    // --- Validación ---
    fun validateEmail(email: String): String? {
        if (email.isBlank()) return "El correo es obligatorio"
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return "Correo inválido"
        return null
    }

    fun validatePassword(password: String): String? {
        if (password.isBlank()) return "La contraseña es obligatoria"
        // Actualizado a 8 para coincidir con el login y mejorar seguridad
        if (password.length < 8) return "Mínimo 8 caracteres"
        return null
    }

    fun validateConfirmPassword(password: String, confirm: String): String? {
        if (confirm.isBlank()) return "Confirma tu contraseña"
        if (password != confirm) return "Las contraseñas no coinciden"
        return null
    }

    fun isRegisterFormValid(
        email: String, password: String, confirm: String
    ): Boolean {
        return validateEmail(email) == null &&
                validatePassword(password) == null &&
                validateConfirmPassword(password, confirm) == null
    }

    // --- Operación de registro ---
    fun requestSignUp(email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = ResponseService.Loading
            _registerState.value = authRepository.requestSignUp(email, password)
        }
    }
}
