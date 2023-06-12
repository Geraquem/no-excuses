package com.mmfsin.noexcuses.presentation.musculargroups.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemMuscularGroupBinding
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.exercises.adapter.ExercisesAdapter
import com.mmfsin.noexcuses.presentation.exercises.interfaces.IExercisesListener

class MGroupsAdapter(
    private val mGroups: List<MuscularGroup>,
    private val onClick: (mGroup: MuscularGroup) -> Unit
) : RecyclerView.Adapter<MGroupsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMuscularGroupBinding.bind(view)
        fun bind(muscularGroup: MuscularGroup) {
            binding.apply {
                Glide.with(binding.root.context).load(muscularGroup.imageURL).into(image)
                tvName.text = muscularGroup.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_muscular_group, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mGroups[position])
        holder.itemView.setOnClickListener { onClick(mGroups[position]) }
    }

    override fun getItemCount(): Int = mGroups.size
}