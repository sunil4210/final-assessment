package com.example.final_assessment.views.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_assessment.constants.AppConstants
import com.example.final_assessment.databinding.ActivityDashboardBinding
import com.example.final_assessment.viewmodels.DashboardState
import com.example.final_assessment.viewmodels.DashboardViewModel
import com.example.final_assessment.views.dashboard_detail.DashboardDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private lateinit var dashboardAdapter: DashboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View binding setup
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get keypass from intent
        val keypass = intent.getStringExtra(AppConstants.KEYPASS) ?: "fitness"

        // Set up RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        // Observe dashboard state
        dashboardViewModel.dashboardState.observe(this) { state ->
            when (state) {
                is DashboardState.Loading -> {
                    // Show loading indicator and hide RecyclerView
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }

                is DashboardState.Success -> {
                    // Hide loading indicator and show RecyclerView
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    dashboardAdapter = DashboardAdapter(state.entities) { selectedEntity ->
                        // Navigate to DetailsActivity
                        val intent = Intent(this, DashboardDetailActivity::class.java)
                        intent.putExtra(
                            AppConstants.DASHBOARD_ENTITY, selectedEntity
                        )
                        startActivity(intent)
                    }
                    binding.recyclerView.adapter = dashboardAdapter

                }

                is DashboardState.Error -> {
                    // Hide loading indicator, show error message, and hide RecyclerView
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    Toast.makeText(this, "Error: ${state.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Fetch dashboard data
        dashboardViewModel.fetchDashboard(keypass)
    }
}

