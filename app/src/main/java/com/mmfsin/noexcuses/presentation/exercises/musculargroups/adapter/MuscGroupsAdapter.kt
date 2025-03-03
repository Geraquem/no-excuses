package com.mmfsin.noexcuses.presentation.exercises.musculargroups.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemMuscularGroupBinding
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.exercises.musculargroups.interfaces.IMuscGroupListener

class MuscGroupsAdapter(
    private val groups: List<MuscularGroup>,
    private val isWomanImageSelected: Boolean,
    private val listener: IMuscGroupListener
) : RecyclerView.Adapter<MuscGroupsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMuscularGroupBinding.bind(view)
        fun bind(group: MuscularGroup, isWomanImageSelected: Boolean) {
            binding.apply {
                val mgImage = if (isWomanImageSelected) group.womanImageURL else group.manImageURL
                Glide.with(binding.root.context).load(mgImage).into(image)
                tvName.text = group.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_muscular_group, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(groups[position], isWomanImageSelected)
        holder.itemView.setOnClickListener { listener.onMGroupClick(groups[position].id) }
    }

    override fun getItemCount(): Int = groups.size
}