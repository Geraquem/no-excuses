package com.mmfsin.noexcuses.presentation.calendar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemCalendarDataBinding
import com.mmfsin.noexcuses.domain.models.CalendarDayData
import com.mmfsin.noexcuses.presentation.calendar.interfaces.ICalendarDayListener

class CalendarDayAdapter(
    private val data: List<CalendarDayData>,
    private val listener: ICalendarDayListener,
) : RecyclerView.Adapter<CalendarDayAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCalendarDataBinding.bind(view)
        private val c = binding.root.context
        private var clickable: Boolean = false

        fun bind(data: CalendarDayData, listener: ICalendarDayListener) {
            binding.apply {
                data.routineName?.let { name ->
                    clickable = true
                    tvRoutineName.text = name
                    data.routineDescription?.let { desc ->
                        tvRoutineDescription.text = desc
                    } ?: run { tvRoutineDescription.isVisible = false }
                    tvDayName.text = data.dayName
                } ?: run {
                    clickable = false
                    tvRoutineName.isVisible = false
                    tvRoutineDescription.text = c.getString(R.string.calendar_no_data)
                    tvDayName.isVisible = false
                    separator.isVisible = false
                }

                root.setOnClickListener { }
                ivDelete.setOnClickListener { listener.deleteDayData(data.databaseId) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_data, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }

    override fun getItemCount(): Int = data.size
}