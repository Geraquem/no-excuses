package com.mmfsin.noexcuses.presentation.myroutines.exercises.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.myroutines.exercises.interfaces.IChExercisesListener

class ChExercisesAdapter(
    private val exercises: List<Exercise>,
    private val listener: IChExercisesListener
) : RecyclerView.Adapter<ChExercisesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemExerciseBinding.bind(view)
        fun bind(exercise: Exercise) {
            binding.apply {
                Glide.with(binding.root.context).load(exercise.imageURL).into(image)
                tvName.text = exercise.name
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
        holder.itemView.setOnClickListener { listener.onExerciseClick(exercises[position].id) }
    }

    override fun getItemCount(): Int = exercises.size
}