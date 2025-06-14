package com.mmfsin.noexcuses.presentation.menu.dialogs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemDayBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.menu.dialogs.listener.IMenuDaysListener
import com.mmfsin.noexcuses.utils.getInitial

class MenuDaysAdapter(
    private val days: List<Day>,
    private val createdByUser: Boolean,
    private val listener: IMenuDaysListener
) : RecyclerView.Adapter<MenuDaysAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDayBinding.bind(view)
        private val c = binding.root.context
        fun bind(day: Day, position: Int, createdByUser: Boolean) {
            binding.apply {
                tvDay.text = c.getString(R.string.days_day, position.toString())
                val title = day.title
                val initial = if (createdByUser) title.getInitial()
                else day.id.last().toString()
                image.firstLetter.text = initial
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
        holder.bind(day, position + 1, createdByUser)
        holder.itemView.setOnClickListener { listener.onDayClick(day.id) }
    }

    override fun getItemCount(): Int = days.size
}