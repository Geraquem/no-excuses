package com.mmfsin.noexcuses.presentation.chooseexercises.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemExerciseBinding
import com.mmfsin.noexcuses.domain.models.RealmExercise
import com.mmfsin.noexcuses.presentation.chooseexercises.interfaces.IChExercisesListener

class ChExercisesAdapter(
    private val realmExercises: List<RealmExercise>, private val listener: IChExercisesListener
) : RecyclerView.Adapter<ChExercisesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemExerciseBinding.bind(view)
        fun bind(exercise: RealmExercise) {
            binding.apply {
                Glide.with(binding.root.context).load(exercise.imageURL).into(image);
                tvCategory.visibility = View.GONE
                tvName.text = exercise.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_exercise, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(realmExercises[position])
        holder.itemView.setOnClickListener { listener.onClick(realmExercises[position]) }
    }

    override fun getItemCount(): Int = realmExercises.size
}