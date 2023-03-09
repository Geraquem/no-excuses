package com.mmfsin.noexcuses.presentation.exercises.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemSimpleExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.exercises.interfaces.IExercisesListener

class ExercisesAdapter(
    private val exercises: List<Exercise>, private val listener: IExercisesListener
) : RecyclerView.Adapter<ExercisesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemSimpleExerciseBinding.bind(view)
        fun bind(exercise: Exercise) {
            binding.apply { tvName.text = exercise.nombre }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_simple_exercise, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(exercises[position])
        holder.itemView.setOnClickListener { listener.onClick(exercises[position].id) }
    }

    override fun getItemCount(): Int = exercises.size
}