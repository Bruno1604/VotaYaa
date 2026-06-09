package com.example.votaya.core.repositories

import android.util.Log
import com.example.votaya.core.ResponseService
import com.example.votaya.onboarding.personal.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository : UserService {
    private val firestore by lazy { FirebaseFirestore.getInstance() }
    private val userCollection by lazy { firestore.collection("users") }

    override suspend fun saveUserInfo(userProfile: UserProfile): ResponseService<Unit> = withContext(
        Dispatchers.IO
    ) {
        try {
            val userMap = hashMapOf(
                "id" to userProfile.id,
                "firstName" to userProfile.firstName,
                "lastName" to userProfile.lastName,
                "userName" to userProfile.userName,
                "phone" to userProfile.phone,
                "birthDate" to userProfile.birthDate,
                "email" to userProfile.email
            )

            Log.d("UserRepository", "Iniciando guardado en Firestore para UID: ${userProfile.id}")
            
            userCollection.document(userProfile.id)
                .set(userMap)
                .await()
                
            Log.d("UserRepository", "Perfil guardado exitosamente")
            ResponseService.Success(Unit)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error al guardar en Firestore: ${e.message}", e)
            ResponseService.Error("Error al guardar perfil: ${e.localizedMessage}")
        }
    }
}
