package com.example.votaya.core.repositories

import com.example.votaya.core.ResponseService
import com.example.votaya.onboarding.personal.model.UserProfile

interface UserService {
    suspend fun saveUserInfo(userProfile: UserProfile): ResponseService<Unit>
}