package com.example.votaya.core.network

import retrofit2.http.GET
import retrofit2.http.Query

interface DummyJsonApi {
    @GET("users")
    suspend fun getUsers(@Query("limit") limit: Int = 10): UserResponse

    @GET("posts")
    suspend fun getPosts(@Query("limit") limit: Int = 10): PostResponse
}

data class UserResponse(
    val users: List<DummyUser>
)

data class DummyUser(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val image: String
)

data class PostResponse(
    val posts: List<DummyPost>
)

data class DummyPost(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int,
    val tags: List<String>,
    val reactions: Reactions
)

data class Reactions(
    val likes: Int,
    val dislikes: Int
)
