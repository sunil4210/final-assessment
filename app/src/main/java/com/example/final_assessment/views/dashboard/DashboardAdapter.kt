package com.example.final_assessment.views.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_assessment.R
import com.example.final_assessment.models.dashboard.DashboardEntity

class DashboardAdapter(
    private val entities: List<DashboardEntity>,
    private val onItemClick: (DashboardEntity) -> Unit
) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    inner class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val exerciseNameTextView: TextView =
            itemView.findViewById(R.id.exerciseNameTextView)
        private val muscleGroupTextView: TextView = itemView.findViewById(R.id.muscleGroupTextView)
        private val equipmentTextView: TextView = itemView.findViewById(R.id.equipmentTextView)
        private val difficultyTextView: TextView = itemView.findViewById(R.id.difficultyTextView)
        private val caloriesBurnedTextView: TextView =
            itemView.findViewById(R.id.caloriesBurnedTextView)

        @SuppressLint("SetTextI18n")
        fun bind(entity: DashboardEntity) {
            exerciseNameTextView.text = ("Exercise: " + entity.exerciseName) ?: "N/A"
            muscleGroupTextView.text = ("Target Muscle: " + entity.muscleGroup) ?: "N/A"
            equipmentTextView.text = ("Equipment: " + entity.equipment) ?: "N/A"
            difficultyTextView.text = ("Difficulty: " + entity.difficulty) ?: "N/A"
            "Calories Burned: ${entity.caloriesBurnedPerHour ?: 0}".also { caloriesBurnedTextView.text = it }

            itemView.setOnClickListener {
                onItemClick(entity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dashboard_entity, parent, false)
        return DashboardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val entity = entities[position]
        holder.bind(entity)
    }

    override fun getItemCount(): Int {
        return entities.size
    }
}

