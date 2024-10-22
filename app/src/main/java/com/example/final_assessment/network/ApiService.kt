package com.example.final_assessment.network

import com.example.final_assessment.constants.ApiConstants
import com.example.final_assessment.constants.AppConstants
import com.example.final_assessment.models.dashboard.DashboardResponse
import com.example.final_assessment.models.login.LoginRequest
import com.example.final_assessment.models.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST(ApiConstants.LOGIN_URL)
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET(ApiConstants.DASHBOARD_URL)
    suspend fun getDashboard(@Path(AppConstants.KEYPASS) keypass: String): Response<DashboardResponse>
}