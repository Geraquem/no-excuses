package com.mmfsin.noexcuses.presentation.myroutines.mroutines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemRoutineBinding
import com.mmfsin.noexcuses.domain.models.MyRoutine
import com.mmfsin.noexcuses.presentation.myroutines.mroutines.interfaces.IMyRoutineListener

class MyRoutinesAdapter(
    private val myRoutines: List<MyRoutine>,
    private val listener: IMyRoutineListener
) : RecyclerView.Adapter<MyRoutinesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRoutineBinding.bind(view)
        private val c = binding.root.context
        fun bind(myRoutine: MyRoutine, listener: IMyRoutineListener) {
            binding.apply {
                image.tvNumOfDays.text = myRoutine.days.toString()
                val days =
                    if (myRoutine.days == 1) R.string.my_routines_day else R.string.my_routines_days
                image.tvDays.text = c.getString(days)
                tvTitle.text = myRoutine.title
                val description = myRoutine.description?.let { myRoutine.description }
                    ?: run { c.getString(R.string.my_routines_no_description) }
                tvDescription.text = description

                val pushPinIcon = if (myRoutine.doingIt) R.drawable.ic_pushpin
                else R.drawable.ic_pushpin_off
                ivPushpin.setImageResource(pushPinIcon)

                ivPushpin.setOnClickListener { listener.onRoutinePushPinClick(myRoutine.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_routine, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myRoutines[position], listener)
        holder.itemView.setOnClickListener { listener.onRoutineClick(myRoutines[position].id) }
        holder.itemView.setOnLongClickListener {
            listener.onRoutineLongClick(myRoutines[position].id)
            true
        }
    }

    override fun getItemCount(): Int = myRoutines.size
}