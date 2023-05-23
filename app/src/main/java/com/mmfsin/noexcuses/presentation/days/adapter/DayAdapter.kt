package com.mmfsin.noexcuses.presentation.days.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemDayBinding
import com.mmfsin.noexcuses.domain.mappers.toDay
import com.mmfsin.noexcuses.domain.models.DayWithExercises
import com.mmfsin.noexcuses.presentation.days.interfaces.IDayListener

class DayAdapter(
    private val days: List<DayWithExercises>, private val listener: IDayListener
) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemDayBinding.bind(view)
        fun bind(dayWithExercises: DayWithExercises) {
            binding.apply {
                val name = dayWithExercises.day.name
                tvName.text = name
                roundImage.firstLetter.text = name.substring(0, 1)
                tvDescription.text = when (dayWithExercises.numExercises) {
                    1 -> binding.root.context.getString(R.string.day_exercise)
                    else -> binding.root.context.getString(
                        R.string.day_exercises, dayWithExercises.numExercises.toString()
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(days[position])
        holder.itemView.setOnClickListener { listener.onClick(days[position].toDay()) }
        holder.binding.config.setOnClickListener { listener.config(days[position].toDay()) }
    }

    override fun getItemCount(): Int = days.size
}