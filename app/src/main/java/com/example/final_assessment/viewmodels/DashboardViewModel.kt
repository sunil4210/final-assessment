package com.example.final_assessment.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_assessment.models.dashboard.DashboardEntity
import com.example.final_assessment.repositories.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: DashboardRepository) :
    ViewModel() {

    private val _dashboardState = MutableLiveData<DashboardState>()
    val dashboardState: LiveData<DashboardState> = _dashboardState

    fun fetchDashboard(keypass: String) {
        viewModelScope.launch {
            _dashboardState.value = DashboardState.Loading
            try {
                val response = repository.getDashboard(keypass)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _dashboardState.value = DashboardState.Success(it.entities)
                        Log.d("DashboardViewModel", "fetchDashboard: ${it.entities} ")
                    } ?: run {
                        _dashboardState.value = DashboardState.Error("Empty response")
                    }
                } else {
                    _dashboardState.value = DashboardState.Error("Failed to load dashboard")
                }
            } catch (e: Exception) {
                _dashboardState.value = DashboardState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

// Sealed class to represent the different states of the UI
sealed class DashboardState {
    data object Loading : DashboardState()
    data class Success(val entities: List<DashboardEntity>) : DashboardState()
    data class Error(val message: String) : DashboardState()
}

