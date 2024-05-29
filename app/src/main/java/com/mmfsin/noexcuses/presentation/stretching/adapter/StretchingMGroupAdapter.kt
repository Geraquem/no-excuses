package com.mmfsin.noexcuses.presentation.stretching.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemStretchingMgroupBinding
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.stretching.interfaces.IStretchingListener

class StretchingMGroupAdapter(
    private val mgroups: List<MuscularGroup>,
    private val listener: IStretchingListener
) : RecyclerView.Adapter<StretchingMGroupAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemStretchingMgroupBinding.bind(view)
        fun bind(mgroup: MuscularGroup) {
            binding.apply {
                Glide.with(binding.root.context).load(mgroup.imageURL).into(image)
                tvName.text = "estiramientos de ${mgroup.name}"
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
        holder.itemView.setOnClickListener { listener.onStretchingMGroupClick(mgroups[position].name) }
    }

    override fun getItemCount(): Int = mgroups.size
}