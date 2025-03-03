package com.mmfsin.noexcuses.presentation.menu.adapter

import android.content.Context
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
    private val isWomanImageSelected: Boolean,
    private val listener: IMenuListener
) : RecyclerView.Adapter<MenuMuscGroupsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMenuMuscularGroupBinding.bind(view)
        fun bind(group: MuscularGroup, isWomanImageSelected: Boolean, isLast: Boolean) {
            binding.apply {
                val mgImage = if (isWomanImageSelected) group.womanImageURL else group.manImageURL
                Glide.with(binding.root.context).load(mgImage).into(image)
                tvName.text = group.name

                val layoutParams = itemView.layoutParams as ViewGroup.MarginLayoutParams
                if (isLast) layoutParams.marginEnd = 0
                else layoutParams.marginEnd = tuDpToPx(itemView.context, 8)
                itemView.layoutParams = layoutParams
            }
        }

        private fun tuDpToPx(context: Context, dp: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
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
        holder.bind(muscularGroup, isWomanImageSelected, position == itemCount - 1)
        holder.itemView.setOnClickListener { listener.onMenuMuscGroupClick(muscularGroup.id) }
    }

    override fun getItemCount(): Int = groups.size
}