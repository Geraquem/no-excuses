package com.mmfsin.noexcuses.presentation.dayexercises.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ItemDayExerciseBinding
import com.mmfsin.noexcuses.domain.models.RealmExercise
import com.mmfsin.noexcuses.presentation.dayexercises.interfaces.IDayExercisesListener

class DayExercisesAdapter(
    private val realmExercises: List<RealmExercise>, private val listener: IDayExercisesListener
) : RecyclerView.Adapter<DayExercisesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemDayExerciseBinding.bind(view)
        fun bind(realmExercise: RealmExercise) {
            binding.apply { tvName.text = realmExercise.nombre }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_day_exercise, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(realmExercises[position])
        holder.itemView.setOnClickListener { listener.onClick(realmExercises[position]) }
        holder.binding.delete.setOnClickListener { listener.deleteDayExercise(realmExercises[position]) }
    }

    override fun getItemCount(): Int = realmExercises.size
}