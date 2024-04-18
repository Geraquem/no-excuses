package com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemChExerciseBinding
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.interfaces.IDefaultExerciseListener

class DefaultExercisesAdapter(
    private val exercises: List<DefaultExercise>,
    private val listener: IDefaultExerciseListener
) : RecyclerView.Adapter<DefaultExercisesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemChExerciseBinding.bind(view)
        private val c = binding.root.context
        fun bind(exercise: DefaultExercise) {
            binding.apply {
                Glide.with(c).load(exercise.exercise.imageURL).into(image)
                tvCategory.text = exercise.exercise.category
                tvName.text = exercise.exercise.name

                tvSeries.text = exercise.series
                tvWait.text = exercise.desc
                ivHasNotes.isVisible = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ch_exercise, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
        holder.itemView.setOnClickListener { listener.onDefaultExerciseClick(exercise.id) }
    }

    override fun getItemCount(): Int = exercises.size
}