package com.mmfsin.noexcuses.presentation.maximums.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemMaximumBinding
import com.mmfsin.noexcuses.domain.models.MaximumData
import com.mmfsin.noexcuses.domain.models.getCategoryColor
import com.mmfsin.noexcuses.presentation.maximums.listeners.IMaximumsListener
import com.mmfsin.noexcuses.utils.deletePointZero

class MaximumsAdapter(
    private val maximums: List<MaximumData>,
    private val listener: IMaximumsListener
) : RecyclerView.Adapter<MaximumsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMaximumBinding.bind(view)
        private val c = binding.root.context
        fun bind(data: MaximumData, listener: IMaximumsListener) {
            binding.apply {
                tvCategory.text = data.exercise.category
                setCategoryColor(data.exercise.category)

                tvExerciseName.text = data.exercise.name
                Glide.with(c).load(data.exercise.gifURL).into(image)

                val lastWeight = data.data.last().weight.deletePointZero()
                tvLastWeight.text = c.getString(R.string.maximums_my_kg, lastWeight)

                val maxWeight = data.data.maxOfOrNull { it.weight }.deletePointZero()
                tvMaxWeight.text = c.getString(R.string.maximums_my_kg, maxWeight)

                tvDetails.setOnClickListener {
                    listener.onSeeDetailsClick(data.exercise.id)
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_maximum, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = maximums[position]
        holder.bind(data, listener)
    }

    override fun getItemCount(): Int = maximums.size
}