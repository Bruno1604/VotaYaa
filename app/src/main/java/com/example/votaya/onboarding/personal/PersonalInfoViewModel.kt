package com.example.votaya.onboarding.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.votaya.core.ResponseService
import com.example.votaya.core.repositories.UserRepository
import com.example.votaya.onboarding.personal.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PersonalInfoViewModel: ViewModel() {
    private val repository = UserRepository()

    private val _saveState = MutableStateFlow<ResponseService<Unit>?>(null)
    val saveState: StateFlow<ResponseService<Unit>?> = _saveState.asStateFlow()

    // --- Validaciones mejoradas ---
    fun validateFirstName(value: String): String? {
        if (value.isBlank()) return "El nombre es requerido"
        if (value.length < 2) return "Mínimo 2 caracteres"
        return null
    }

    fun validateLastName(value: String): String? {
        if (value.isBlank()) return "Los apellidos son requeridos"
        if (value.length < 2) return "Mínimo 2 caracteres"
        return null
    }

    fun validateUsername(value: String): String? {
        if (value.isBlank()) return "El usuario es requerido"
        if (value.length < 4) return "Mínimo 4 caracteres"
        if (!value.matches(Regex("^[a-zA-Z0-9_.]+$")))
            return "Solo letras, números, _ y ."
        return null
    }

    fun validatePhone(value: String): String? {
        if (value.isBlank()) return "El teléfono es requerido"
        // Permitimos +, espacios y guiones para evitar frustración
        val cleanPhone = value.replace(Regex("[\\s\\-+]"), "")
        if (!cleanPhone.all { it.isDigit() }) return "Formato de teléfono inválido"
        if (cleanPhone.length < 10) return "Mínimo 10 dígitos"
        return null
    }

    fun validateBirthDate(value: String): String? {
        if (value.isBlank()) return "Selecciona tu fecha de nacimiento"
        return null
    }

    fun isFormValid(
        firstName: String, lastName: String, username: String,
        phone: String, birthDate: String
    ): Boolean {
        return validateFirstName(firstName) == null &&
                validateLastName(lastName) == null &&
                validateUsername(username) == null &&
                validatePhone(phone) == null &&
                validateBirthDate(birthDate) == null
    }

    fun saveProfile(uid: String, firstName: String, lastName: String,
                    username: String, phone: String, birthDate: String, email: String) {
        viewModelScope.launch {
            _saveState.value = ResponseService.Loading
            val user = UserProfile(id = uid,
                firstName = firstName,
                lastName = lastName,
                userName = username,
                phone = phone,
                birthDate = birthDate,
                email = email)
            _saveState.value = repository.saveUserInfo(user)
        }
    }
}
