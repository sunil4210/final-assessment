package com.example.final_assessment.models.dashboard

data class DashboardResponse(
    val entities: List<DashboardEntity>,
    val entityTotal: Int
)

