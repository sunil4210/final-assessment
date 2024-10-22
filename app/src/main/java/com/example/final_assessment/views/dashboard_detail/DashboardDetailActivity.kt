package com.example.final_assessment.views.dashboard_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.final_assessment.constants.AppConstants
import com.example.final_assessment.databinding.ActivityDashbaordDetailBinding
import com.example.final_assessment.models.dashboard.DashboardEntity

class DashboardDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashbaordDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View binding setup
        binding = ActivityDashbaordDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar as the action bar
        setSupportActionBar(binding.toolbar)

        // Retrieve the DashboardEntity passed from DashboardActivity
        val entity = intent.getSerializableExtra(AppConstants.DASHBOARD_ENTITY) as? DashboardEntity

        // Set the toolbar title to the exercise name
        supportActionBar?.title = entity?.exerciseName ?: "Exercise Details"

        // Enable back button in the app bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Display the entity details
        entity?.let {
            binding.exerciseNameTextView.text = it.exerciseName
            binding.muscleGroupTextView.text = it.muscleGroup
            binding.equipmentTextView.text = it.equipment
            binding.difficultyTextView.text = it.difficulty
            binding.caloriesBurnedTextView.text = "Calories Burned: ${it.caloriesBurnedPerHour}"
            binding.descriptionTextView.text = it.description
        }
    }

    // Handle back button press in the AppBar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
