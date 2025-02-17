package com.mmfsin.noexcuses.presentation.myroutines.mdays.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemDayBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.myroutines.mdays.interfaces.IDaysListener
import com.mmfsin.noexcuses.utils.getInitial

class DaysAdapter(
    private val days: List<Day>,
    private val listener: IDaysListener
) : RecyclerView.Adapter<DaysAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDayBinding.bind(view)
        private val c = binding.root.context
        fun bind(day: Day, position: Int) {
            binding.apply {
                val title = day.title
                val initial = title.getInitial()
                image.firstLetter.text = initial
                tvDay.text = c.getString(R.string.days_day, position.toString())
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
        holder.bind(days[position], position + 1)
        holder.itemView.setOnClickListener { listener.onDayClick(days[position].id) }
        holder.itemView.setOnLongClickListener {
            listener.onDayLongClick(days[position].id)
            true
        }
    }

    override fun getItemCount(): Int = days.size
}