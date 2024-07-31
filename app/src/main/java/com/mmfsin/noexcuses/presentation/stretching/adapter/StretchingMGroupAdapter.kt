package com.mmfsin.noexcuses.presentation.stretching.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemStretchingMgroupBinding
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.stretching.interfaces.IStretchingListener
import com.mmfsin.noexcuses.utils.CATEGORY

class StretchingMGroupAdapter(
    private val mgroups: List<MuscularGroup>,
    private val listener: IStretchingListener
) : RecyclerView.Adapter<StretchingMGroupAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemStretchingMgroupBinding.bind(view)
        private val c = binding.root.context
        fun bind(mgroup: MuscularGroup) {
            binding.apply {
                tvName.text = mgroup.name
                if (mgroup.name == c.getString(R.string.mgroups_cardio)) itemView.isVisible = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_stretching_mgroup, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mgroups[position])
        holder.itemView.setOnClickListener { listener.onStretchingMGroupClick(mgroups[position].id) }
    }

    override fun getItemCount(): Int = mgroups.size
}