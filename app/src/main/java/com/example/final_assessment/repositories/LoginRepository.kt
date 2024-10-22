package com.example.final_assessment.repositories

import com.example.final_assessment.models.login.LoginRequest
import com.example.final_assessment.models.login.LoginResponse
import com.example.final_assessment.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun login(username: String, password: String): Response<LoginResponse> {
        return apiService.login(LoginRequest(username, password))
    }
}
