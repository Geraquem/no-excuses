package com.mmfsin.noexcuses.presentation.routines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemRoutineBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.routines.interfaces.IRoutineListener

class RoutinesAdapter(
    private val myRoutines: List<Routine>,
    private val listener: IRoutineListener
) : RecyclerView.Adapter<RoutinesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRoutineBinding.bind(view)
        private val c = binding.root.context
        fun bind(routine: Routine) {
            binding.apply {
                image.tvNumOfDays.text = routine.daysCount.toString()
                tvTitle.text = routine.title
                tvDescription.text = routine.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_routine, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routine = myRoutines[position]
        holder.bind(routine)
        holder.itemView.setOnClickListener { listener.onRoutineClick(routine.id, routine.days) }
    }

    override fun getItemCount(): Int = myRoutines.size
}