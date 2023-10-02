package com.mmfsin.noexcuses.presentation.routines.days.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemChExerciseBinding
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.presentation.routines.days.interfaces.IDayExerciseListener

class DayExercisesAdapter(
    private val exercises: List<CompactExercise>, private val listener: IDayExerciseListener
) : RecyclerView.Adapter<DayExercisesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemChExerciseBinding.bind(view)
        private val c = binding.root.context
        fun bind(exercise: CompactExercise) {
            binding.apply {
                Glide.with(binding.root.context).load(exercise.imageURL).into(image)
                tvCategory.text = exercise.category
                tvName.text = exercise.name
                val question = c.getText(R.string.days_exercises_empty_data)

                val series = exercise.series?.toString() ?: run { question }
                tvSeries.text = series

                if (exercise.series == 1) tvSeriesText.text =
                    c.getString(R.string.days_exercises_serie)

                val time = exercise.time ?: run { question }
                tvWait.text = time
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ch_exercise, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(exercises[position])
        holder.itemView.setOnClickListener {
            exercises[position].chExerciseId?.let { id -> listener.onExerciseClick(id) }
        }
        holder.itemView.setOnLongClickListener {
            exercises[position].chExerciseId?.let { id -> listener.onExerciseLongClick(id) }
            true
        }
    }

    override fun getItemCount(): Int = exercises.size
}