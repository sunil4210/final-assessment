package com.example.final_assessment.repositories

import com.example.final_assessment.models.dashboard.DashboardResponse
import com.example.final_assessment.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class DashboardRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getDashboard(keypass: String): Response<DashboardResponse> {
        return apiService.getDashboard(keypass)
    }
}
