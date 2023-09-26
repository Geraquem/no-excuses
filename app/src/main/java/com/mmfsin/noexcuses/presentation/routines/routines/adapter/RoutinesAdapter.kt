package com.mmfsin.noexcuses.presentation.routines.routines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemRoutineBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.routines.routines.interfaces.IRoutineListener

class RoutinesAdapter(
    private val routines: List<Routine>, private val listener: IRoutineListener
) : RecyclerView.Adapter<RoutinesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRoutineBinding.bind(view)
        private val c = binding.root.context
        fun bind(routine: Routine) {
            binding.apply {
                image.tvNumOfDays.text = routine.days.toString()
                tvTitle.text = routine.title
                val description = routine.description?.let { routine.description }
                    ?: run { c.getString(R.string.routines_no_description) }
                tvDescription.text = description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_routine, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(routines[position])
        holder.itemView.setOnClickListener { listener.onRoutineClick(routines[position].id) }
        holder.itemView.setOnLongClickListener {
            listener.onRoutineLongClick(routines[position].id)
            true
        }
    }

    override fun getItemCount(): Int = routines.size
}