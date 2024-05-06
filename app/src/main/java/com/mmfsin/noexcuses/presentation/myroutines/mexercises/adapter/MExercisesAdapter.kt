package com.mmfsin.noexcuses.presentation.myroutines.mexercises.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemChExerciseBinding
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.domain.models.getCategoryColor
import com.mmfsin.noexcuses.presentation.myroutines.mexercises.interfaces.IMExerciseListener

class MExercisesAdapter(
    private val exercises: List<CompactExercise>,
    private val listener: IMExerciseListener
) : RecyclerView.Adapter<MExercisesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemChExerciseBinding.bind(view)
        private val c = binding.root.context
        fun bind(exercise: CompactExercise, listener: IMExerciseListener) {
            binding.apply {
                setCategoryColor(exercise.category)
                Glide.with(binding.root.context).load(exercise.imageURL).into(image)
                tvCategory.text = exercise.category
                tvName.text = exercise.name

                val series = exercise.series
                series?.let {
                    llReps.visibility = if (it == 0) View.GONE else View.VISIBLE
                    if (it == 1) tvSeriesText.text = c.getString(R.string.mexercises_serie)
                    tvSeries.text = it.toString()
                } ?: run { llReps.visibility = View.GONE }

                val time = exercise.time
                tvWait.text = time.toString()
                time?.let {
                    tvWait.text = it
                    llTime.visibility = View.VISIBLE
                } ?: run { llTime.visibility = View.GONE }

                ivHasNotes.isVisible = exercise.hasNotes

                val addDataVisible = series == 0 && time == null && !exercise.hasNotes
                llData.isVisible = !addDataVisible
                llAddData.isVisible = addDataVisible

                exercise.chExerciseId?.let { id ->
                    llAddData.setOnClickListener { listener.onExerciseLongClick(id) }
                }
            }
        }

        private fun setCategoryColor(category: String) {
            val categoryColor = ContextCompat.getColor(c, getCategoryColor(category))
            binding.tvCategory.background.setTint(categoryColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ch_exercise, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(exercises[position], listener)
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