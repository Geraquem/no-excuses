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
    private val routines: List<Routine>,
    private val listener: IRoutineListener
) : RecyclerView.Adapter<RoutinesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRoutineBinding.bind(view)
        fun bind(routine: Routine) {
            binding.apply {
                tvTitle.text = routine.title
                routine.description?.let { tvDescription.text = it }
                    ?: run { tvDescription.visibility = View.GONE }
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
    }

    override fun getItemCount(): Int = routines.size
}