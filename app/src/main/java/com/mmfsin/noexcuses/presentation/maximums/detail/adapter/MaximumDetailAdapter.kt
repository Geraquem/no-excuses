package com.mmfsin.noexcuses.presentation.maximums.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemMaximumDetailBinding
import com.mmfsin.noexcuses.domain.models.MData
import com.mmfsin.noexcuses.presentation.maximums.listeners.IMaximumDetailListener
import com.mmfsin.noexcuses.utils.deletePointZero
import com.mmfsin.noexcuses.utils.toCompleteDate

class MaximumDetailAdapter(
    private val mDataList: List<MData>,
    private val listener: IMaximumDetailListener
) : RecyclerView.Adapter<MaximumDetailAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMaximumDetailBinding.bind(view)
        private val context = binding.root.context
        fun bind(data: MData) {
            binding.apply {
                tvDate.text = data.date.toCompleteDate(context)
                tvWeight.text = data.weight.deletePointZero()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_maximum_detail, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mDataList[position]
        holder.bind(data)
        holder.itemView.setOnClickListener { listener.onMDataClick(data.id) }
    }

    override fun getItemCount(): Int = mDataList.size
}