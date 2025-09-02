package com.example.mykotlinapp
import retrofit2.Response
import retrofit2.http.GET
interface ApiService {
    @GET("Users")
    suspend fun getUsers():Response<List<User>>
}