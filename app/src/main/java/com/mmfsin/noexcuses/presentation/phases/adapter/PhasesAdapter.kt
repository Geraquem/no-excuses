package com.mmfsin.noexcuses.presentation.phases.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemPhaseBinding
import com.mmfsin.noexcuses.domain.models.Phase
import com.mmfsin.noexcuses.presentation.phases.interfaces.IPhaseListener

class PhasesAdapter(
    private val phases: List<Phase>,
    private val listener: IPhaseListener
) : RecyclerView.Adapter<PhasesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPhaseBinding.bind(view)
        fun bind(phase: Phase) {
            binding.apply {
                tvName.text = phase.name
                phase.description?.let { desc -> tvDescription.text = desc }
                    ?: run { tvDescription.visibility = View.GONE }

                ivMenu.setOnClickListener {
                   //TODO
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_phase, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(phases[position])
        holder.itemView.setOnClickListener { listener.onClick(phases[position].id) }
    }

    override fun getItemCount(): Int = phases.size
}