package com.mmfsin.noexcuses.presentation.dfroutines.dfdays.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemDayBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.dfroutines.dfdays.interfaces.IDefaultDaysListener

class DefaultDaysAdapter(
    private val days: List<Day>,
    private val listener: IDefaultDaysListener
) : RecyclerView.Adapter<DefaultDaysAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDayBinding.bind(view)
        private val c = binding.root.context
        fun bind(day: Day) {
            binding.apply {
                val title = day.title
                val dayNumber = day.id.last().toString()
                image.firstLetter.text = dayNumber
                tvDay.text = c.getString(R.string.days_day, dayNumber)
                tvTitle.text = title
                val exercises = day.exercises
                val numOfExercises = if (exercises == 1) c.getString(R.string.days_one_exercise)
                else c.getString(R.string.days_exercises, exercises.toString())
                tvExercises.text = numOfExercises
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = days[position]
        holder.bind(day)
        holder.itemView.setOnClickListener { listener.onDayClick(day.id) }
    }

    override fun getItemCount(): Int = days.size
}