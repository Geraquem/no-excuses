package com.mmfsin.noexcuses.presentation.menu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemMenuMuscularGroupBinding
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.menu.interfaces.IMenuListener

class MenuMuscGroupsAdapter(
    private val groups: List<MuscularGroup>,
    private val listener: IMenuListener
) : RecyclerView.Adapter<MenuMuscGroupsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMenuMuscularGroupBinding.bind(view)
        fun bind(group: MuscularGroup) {
            binding.apply {
                Glide.with(binding.root.context).load(group.imageURL).into(image)
                tvName.text = group.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_menu_muscular_group, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val muscularGroup = groups[position]
        holder.bind(muscularGroup)
        holder.itemView.setOnClickListener { listener.onMenuMuscGroupClick(muscularGroup.name) }
    }

    override fun getItemCount(): Int = groups.size
}