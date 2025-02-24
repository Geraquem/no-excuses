package com.mmfsin.noexcuses.presentation.calendar.adapter

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
import com.mmfsin.noexcuses.presentation.calendar.interfaces.ICalendarDayExercisesListener

class CalendarMExercisesAdapter(
    private val exercises: MutableList<CompactExercise>,
    private val listener: ICalendarDayExercisesListener
) : RecyclerView.Adapter<CalendarMExercisesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemChExerciseBinding.bind(view)
        private val c = binding.root.context
        fun bind(
            exercise: CompactExercise,
            prevSuperSerie: Boolean,
            position: Int,
        ) {
            binding.apply {
                ivEdit.isVisible = false
                tvPosition.text = position.toString()
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

                llNotes.isVisible = exercise.hasNotes

                val addDataVisible = series == 0 && time == null && !exercise.hasNotes
                llData.isVisible = !addDataVisible

                if (exercise.superSerie) lineBottom.visibility = View.VISIBLE
                if (prevSuperSerie) lineTop.visibility = View.VISIBLE
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
        val prevSuperSerie = if (position == 0) false else (exercises[position - 1].superSerie)
        holder.bind(
            exercise = exercises[position],
            prevSuperSerie = prevSuperSerie,
            position = position + 1,
        )
        holder.itemView.setOnClickListener {
            exercises[position].chExerciseId?.let { id -> listener.showExercise(id, true) }
        }
    }

    override fun getItemCount(): Int = exercises.size
}