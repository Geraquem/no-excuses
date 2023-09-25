package com.mmfsin.noexcuses.presentation.menu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemButtonMenuBinding
import com.mmfsin.noexcuses.domain.models.MenuItem
import com.mmfsin.noexcuses.presentation.menu.interfaces.IMenuListener

class MenuAdapter(
    private val items: List<MenuItem>,
    private val listener: IMenuListener
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemButtonMenuBinding.bind(view)
        fun bind(item: MenuItem) {
            binding.apply {
                tvName.text = item.name
                image.setImageResource(item.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_button_menu, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { listener.onItemClick(items[position].action) }
    }

    override fun getItemCount(): Int = items.size
}