package com.mmfsin.noexcuses.presentation.dayexercises.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.dayexercises.interfaces.IDayExercisesListener

class DayExercisesAdapter(
    private val exercises: List<Exercise>, private val listener: IDayExercisesListener
) : RecyclerView.Adapter<DayExercisesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemExerciseBinding.bind(view)
        fun bind(exercise: Exercise) {
            binding.apply {
                Glide.with(binding.root.context).load(exercise.imageURL).into(image)
                tvCategory.text = exercise.category
                tvName.text = exercise.name
                delete.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(exercises[position])
        holder.itemView.setOnClickListener { listener.onClick(exercises[position]) }
        holder.binding.delete.setOnClickListener { listener.deleteDayExercise(exercises[position]) }
    }

    override fun getItemCount(): Int = exercises.size
}