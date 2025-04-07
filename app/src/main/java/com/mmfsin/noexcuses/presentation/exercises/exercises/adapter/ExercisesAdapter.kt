package com.mmfsin.noexcuses.presentation.exercises.exercises.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemExerciseBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.presentation.exercises.exercises.interfaces.IExercisesListener
import com.mmfsin.noexcuses.utils.ADD_EXERCISE

class ExercisesAdapter(
    private val exercises: MutableList<Exercise>,
    private val listener: IExercisesListener
) : RecyclerView.Adapter<ExercisesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemExerciseBinding.bind(view)
        fun bind(exercise: Exercise) {
            binding.apply {
                if (exercise.id == ADD_EXERCISE) {
                    ivAddExercise.isVisible = true
                    ivAddExercise.alpha = 1f
                    ivAddExercise.setImageResource(R.drawable.ic_add)
                    image.visibility = View.INVISIBLE
                } else {
                    Glide.with(binding.root.context).load(exercise.gifURL).into(image)
                    ivAddExercise.isVisible = false
                    image.visibility = View.VISIBLE
                }

                if (exercise.createdByUser && exercise.gifURL == null) {
                    image.visibility = View.INVISIBLE
                    ivAddExercise.alpha = 0.5f
                    ivAddExercise.setImageResource(R.drawable.ic_no_image)
                    ivAddExercise.isVisible = true
                }

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
        val exercise = exercises[position]
        holder.bind(exercise)
        holder.itemView.setOnClickListener { listener.onExerciseClick(exercise.id) }
        holder.itemView.setOnLongClickListener {
            if (exercise.createdByUser) listener.onExerciseLongClick(exercise.id)
            true
        }
    }

    override fun getItemCount(): Int = exercises.size
}