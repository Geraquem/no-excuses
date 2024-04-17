package com.mmfsin.noexcuses.presentation.defaultroutines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemRoutineBinding
import com.mmfsin.noexcuses.domain.models.DefaultRoutine
import com.mmfsin.noexcuses.presentation.defaultroutines.interfaces.IRoutineListener

class RoutinesAdapter(
    private val myDefaultRoutines: List<DefaultRoutine>,
    private val listener: IRoutineListener
) : RecyclerView.Adapter<RoutinesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRoutineBinding.bind(view)
        private val c = binding.root.context
        fun bind(defaultRoutine: DefaultRoutine) {
            binding.apply {
                image.tvNumOfDays.text = defaultRoutine.days.toString()
                tvTitle.text = defaultRoutine.title
                tvDescription.text = defaultRoutine.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_routine, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routine = myDefaultRoutines[position]
        holder.bind(routine)
        holder.itemView.setOnClickListener { listener.onRoutineClick(routine.id, "routine.days") }
    }

    override fun getItemCount(): Int = myDefaultRoutines.size
}