package com.mmfsin.noexcuses.presentation.stretching.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemStretchingBinding
import com.mmfsin.noexcuses.domain.models.Stretch
import com.mmfsin.noexcuses.presentation.stretching.interfaces.IStretchingListener

class StretchingAdapter(
    private val mgroups: List<Stretch>,
    private val listener: IStretchingListener
) : RecyclerView.Adapter<StretchingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemStretchingBinding.bind(view)
        private val c = binding.root.context
        fun bind(stretch: Stretch) {
            binding.apply {
                tvName.text = stretch.mGroup
                rvStretching.apply {
                    layoutManager = LinearLayoutManager(c)
                    adapter = StretchingDetailAdapter(stretch.stretching)
                }
                rvStretching.isVisible = false

                clMgroup.setOnClickListener {
                    val angle = if (rvStretching.isVisible) 0f else 180f
                    icArrowDown.animate().rotation(angle).setDuration(200).start()
                    rvStretching.isVisible = !rvStretching.isVisible
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_stretching, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mgroups[position])
//        holder.itemView.setOnClickListener { listener.onStretchingClick(mgroups[position].mGroup) }
    }

    override fun getItemCount(): Int = mgroups.size
}