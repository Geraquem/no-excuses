package com.mmfsin.noexcuses.presentation.phases.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemPhaseBinding
import com.mmfsin.noexcuses.domain.models.Phase
import com.mmfsin.noexcuses.presentation.phases.interfaces.IPhasesListener

class PhasesAdapter(
    private val phases: List<Phase>, private val listener: IPhasesListener
) : RecyclerView.Adapter<PhasesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPhaseBinding.bind(view)
        fun bind(phase: Phase, listener: IPhasesListener) {
            binding.apply {
                tvName.text = phase.name
                phase.description?.let { desc -> tvDescription.text = desc }
                    ?: run { tvDescription.visibility = View.GONE }

                ivConfig.setOnClickListener { listener.config(phase) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_phase, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(phases[position], listener)
        holder.itemView.setOnClickListener { listener.onClick(phases[position]) }
    }

    override fun getItemCount(): Int = phases.size
}