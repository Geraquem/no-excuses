package com.mmfsin.noexcuses.presentation.stretching.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemStretchingDetailBinding
import com.mmfsin.noexcuses.domain.models.Stretching

class StretchingDetailAdapter(
    private val stretching: List<Stretching>,
) : RecyclerView.Adapter<StretchingDetailAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemStretchingDetailBinding.bind(view)
        private val c = binding.root.context
        fun bind(stretching: Stretching, position: Int) {
            binding.apply {
                tvPosition.text = c.getString(R.string.stretching_position, position.toString())
                Glide.with(binding.root.context).load(stretching.imageURL).into(image1)
                tvDescription.text = stretching.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_stretching_detail, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stretching[position], position + 1)
    }

    override fun getItemCount(): Int = stretching.size
}