package com.example.final_assessment.models.dashboard

import java.io.Serializable

data class DashboardEntity(
    val exerciseName: String?,
    val muscleGroup: String?,
    val equipment: String?,
    val difficulty: String?,
    val caloriesBurnedPerHour: Int?,
    val description: String?,
) : Serializable

