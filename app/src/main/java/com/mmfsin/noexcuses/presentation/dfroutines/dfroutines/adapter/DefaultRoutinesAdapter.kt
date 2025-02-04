package com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemDefaultRoutineBinding
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.presentation.dfroutines.dfroutines.interfaces.IDefaultRoutineListener

class DefaultRoutinesAdapter(
    private val myDefaultRoutines: List<Routine>,
    private val listener: IDefaultRoutineListener
) : RecyclerView.Adapter<DefaultRoutinesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDefaultRoutineBinding.bind(view)
        private val c = binding.root.context
        fun bind(defaultRoutine: Routine, listener: IDefaultRoutineListener) {
            binding.apply {
                image.tvNumOfDays.text = defaultRoutine.days.toString()
                tvTitle.text = defaultRoutine.name
                tvDescription.text = defaultRoutine.description

                val pushPinIcon = if (defaultRoutine.doingIt) R.drawable.ic_pushpin
                else R.drawable.ic_pushpin_off
                ivPushpin.setImageResource(pushPinIcon)

                ivPushpin.setOnClickListener { listener.onRoutinePushPinClick(defaultRoutine.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_default_routine, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routine = myDefaultRoutines[position]
        holder.bind(routine, listener)
        holder.itemView.setOnClickListener { listener.onRoutineClick(routine.id) }
    }

    override fun getItemCount(): Int = myDefaultRoutines.size
}