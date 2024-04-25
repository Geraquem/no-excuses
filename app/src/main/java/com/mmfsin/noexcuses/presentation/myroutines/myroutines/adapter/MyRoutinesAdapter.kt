package com.mmfsin.noexcuses.presentation.myroutines.myroutines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemRoutineBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.myroutines.myroutines.interfaces.IMyRoutineListener

class MyRoutinesAdapter(
    private val routines: List<Routine>,
    private val listener: IMyRoutineListener
) : RecyclerView.Adapter<MyRoutinesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRoutineBinding.bind(view)
        private val c = binding.root.context
        fun bind(routine: Routine, listener: IMyRoutineListener) {
            binding.apply {
                image.tvNumOfDays.text = routine.days.toString()
                val days =
                    if (routine.days == 1) R.string.my_routines_day else R.string.my_routines_days
                image.tvDays.text = c.getString(days)
                tvTitle.text = routine.title
                val description = routine.description?.let { routine.description }
                    ?: run { c.getString(R.string.my_routines_no_description) }
                tvDescription.text = description

                val pushPinIcon = if (routine.doingIt) R.drawable.ic_pushpin
                else R.drawable.ic_pushpin_off
                ivPushpin.setImageResource(pushPinIcon)

                ivPushpin.setOnClickListener { listener.onRoutinePushPinClick(routine.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_routine, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(routines[position], listener)
        holder.itemView.setOnClickListener { listener.onRoutineClick(routines[position].id) }
        holder.itemView.setOnLongClickListener {
            listener.onRoutineLongClick(routines[position].id)
            true
        }
    }

    override fun getItemCount(): Int = routines.size
}