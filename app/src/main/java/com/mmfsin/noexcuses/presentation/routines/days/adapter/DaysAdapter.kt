package com.mmfsin.noexcuses.presentation.routines.days.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemDayBinding
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.presentation.routines.days.interfaces.IDayListener

class DaysAdapter(
    private val days: List<Day>,
    private val listener: IDayListener
) : RecyclerView.Adapter<DaysAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDayBinding.bind(view)
        fun bind(day: Day) {
            binding.apply {
                tvTitle.text = day.title
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
        holder.itemView.setOnClickListener { listener.onDayClick(days[position].id) }
    }

    override fun getItemCount(): Int = days.size
}