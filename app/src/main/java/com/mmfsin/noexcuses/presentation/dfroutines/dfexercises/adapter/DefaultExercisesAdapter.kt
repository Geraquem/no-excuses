package com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemDfExerciseBinding
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.domain.models.getCategoryColor
import com.mmfsin.noexcuses.presentation.dfroutines.dfexercises.interfaces.IDefaultExerciseListener

class DefaultExercisesAdapter(
    private val exercises: List<DefaultExercise>,
    private val listener: IDefaultExerciseListener
) : RecyclerView.Adapter<DefaultExercisesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDfExerciseBinding.bind(view)
        private val c = binding.root.context
        fun bind(
            exercise: DefaultExercise,
            prevSuperSerie: Boolean,
            position: Int,
            totalSize: Int
        ) {
            binding.apply {
                tvPosition.text = position.toString()
                setCategoryColor(exercise.exercise.category)
                Glide.with(c).load(exercise.exercise.gifURL).into(image)
                tvCategory.text = exercise.exercise.category
                tvName.text = exercise.exercise.name

                tvSeries.text = exercise.series
                tvReps.text = exercise.reps
                tvWait.text = exercise.desc

                setBackground(position, totalSize, clMain)

                llTime.isVisible = !exercise.superSerie
                llSuperSerie.isVisible = exercise.superSerie

                if (exercise.superSerie) lineBottom.visibility = View.VISIBLE
                if (prevSuperSerie) lineTop.visibility = View.VISIBLE
            }
        }

        private fun setCategoryColor(category: String) {
            val categoryColor = ContextCompat.getColor(c, getCategoryColor(category))
            binding.tvCategory.background.setTint(categoryColor)
        }

        private fun setBackground(position: Int, totalSize: Int, container: ConstraintLayout) {
            if (totalSize == 1) container.setBackgroundResource(R.drawable.bg_white_box)
            else {
                val background = when (position) {
                    1 -> R.drawable.bg_white_box_top
                    totalSize -> R.drawable.bg_white_box_bottom
                    else -> R.drawable.bg_white_box_medium
                }
                container.setBackgroundResource(background)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_df_exercise, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]
        val prevSuperSerie = if (position == 0) false else (exercises[position - 1].superSerie)
        holder.bind(
            exercise = exercises[position],
            prevSuperSerie = prevSuperSerie,
            position = position + 1,
            totalSize = exercises.size,
        )
        holder.itemView.setOnClickListener { listener.seeExerciseButtonClick(exercise.exercise.id) }
    }

    override fun getItemCount(): Int = exercises.size
}